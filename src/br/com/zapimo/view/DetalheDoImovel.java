package br.com.zapimo.view;

import java.util.ArrayList;

import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.util.LruCache;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.LinearLayout.LayoutParams;
import br.com.zapimo.R;
import br.com.zapimo.dao.Dao;
import br.com.zapimo.model.Contato;
import br.com.zapimo.model.Imoveis;
import br.com.zapimo.util.IpURL;
import br.com.zapimo.util.JsonUtil;
import br.com.zapimo.util.MeuAlerta;
import br.com.zapimo.util.MeuProgressDialog;
import br.com.zapimo.util.MeusWidgetsBuilder;
import br.com.zapimo.util.MontaJSONObjectGenerico;
import br.com.zapimo.util.VolleySingleton;
import br.com.zapimo.util.VolleyTimeout;

public class DetalheDoImovel extends Activity {

	private static final String RESOURCE_DETALHE_ITEM = "/imoveis/";	
	private static final String RESOURCE_ENVIA_MENSAGEM = "/imoveis/contato";	
	private RequestQueue requestQueue;
	private Context context;	
	private ImageLoader imageLoader;
	private ArrayList<String> listaComImagens;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	    Bundle bundle = getIntent().getExtras();
	    final Imoveis imovelDoSerializable = (Imoveis) bundle.getSerializable("Imoveis");

		context = DetalheDoImovel.this;
						
		requestQueue = Volley.newRequestQueue(context);

		imageLoader = new ImageLoader(requestQueue, new ImageLoader.ImageCache() {
			private final LruCache<String, Bitmap> cache = new LruCache<String, Bitmap>(10);
			@Override
			public void putBitmap(String url, Bitmap bitmap) {
				cache.put(url, bitmap);
			}
			@Override
			public Bitmap getBitmap(String url) {
				return cache.get(url);
			}
		});
		
		listaComImagens = new ArrayList<String>();

		buscaImovel(imovelDoSerializable.getCodImovel());

		
		
		
		setContentView(devolveTelaDetalheDoImovel(imovelDoSerializable));
	}
	
	private void buscaImovel(int codImovel) {
		
		requestQueue = VolleySingleton.getInstance(context).getRequestQueue();

		String url = IpURL.URL_SERVER_REST.getValor() + RESOURCE_DETALHE_ITEM + codImovel;
		
	    final ProgressDialog progressDialog = MeuProgressDialog.criaProgressDialog(context, "Buscando Imóveis...");

		JsonObjectRequest jsonObjRequest = new JsonObjectRequest(

				Request.Method.GET, url, null,

				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject jSONObjectResposta) {

						respostaBuscaImovelWS(jSONObjectResposta, progressDialog);						
					}
				}, 
				new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError volleyError) {
						
						MeuProgressDialog.encerraProgressDialog(progressDialog);
						
						new MeuAlerta("volleyError", volleyError.toString(), context).meuAlertaOk();
					}
				});

		jsonObjRequest.setRetryPolicy(VolleyTimeout.recuperarTimeout());

		requestQueue.add(jsonObjRequest);
	}

	private void respostaBuscaImovelWS(JSONObject jSONObjectResposta, ProgressDialog progressDialog) {
			
		JsonUtil jsonUtil = new JsonUtil();
			
		//talvez remover
		Dao dao = new Dao(context);
		//talvez remover
		
		boolean deuErro = jsonUtil.insereInformacoesDoJsonNoBancoDeDados(context, jSONObjectResposta, dao, Imoveis.class);
				
		MeuProgressDialog.encerraProgressDialog(progressDialog);

		if(deuErro){

			new MeuAlerta("Erro", "Erro ao tentar inserir dados", context).meuAlertaOk();
		}else{	
			
			//listaComImagens.add(imovelDoSerializable.getUrlImagem());
			listaComImagens.add("imovel.getUrlImagem()");
			listaComImagens.add("imosdfsdf");

			
		}				
	}

	
	private ScrollView devolveTelaDetalheDoImovel(final Imoveis imovel) {
		
		MeusWidgetsBuilder meusWidgetsBuilder = new MeusWidgetsBuilder(context);

		ScrollView scrollViewTela = meusWidgetsBuilder.criaScrollView();
		
		final LinearLayout llTela = meusWidgetsBuilder.criaLinearLayoutTELA();
		
		LinearLayout llFotosHolder = new LinearLayout(context);
		llFotosHolder.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 700));
			
		HorizontalScrollView horizontalScrollView = new HorizontalScrollView(context); 
		horizontalScrollView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

		LinearLayout llFotosInternas = new LinearLayout(context);
		llFotosInternas.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		
		//adiciona fotos no llFotosInternas
		for(String urlImagem : listaComImagens) {
		
		ImageView imageViewFotos = new ImageView(context);
		imageViewFotos.setAdjustViewBounds(true);
		imageViewFotos.setImageDrawable(context.getResources().getDrawable(R.drawable.carregamento));
		imageViewFotos.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT));
		
		llFotosInternas.addView(imageViewFotos);

		imageLoader.get(urlImagem, imageLoader.getImageListener(imageViewFotos, R.drawable.carregamento, R.drawable.erro));
		}
		//adiciona fotos no llFotosInternas
			
				
		horizontalScrollView.addView(llFotosInternas);
		
		llFotosHolder.addView(horizontalScrollView);
		
		final EditText etNome = meusWidgetsBuilder.criaEditText("nome");
		final EditText etEmail = meusWidgetsBuilder.criaEditText("email");
		final EditText etTelefone = meusWidgetsBuilder.criaEditText("telefone");
		final EditText etMensagem = meusWidgetsBuilder.criaEditText("Mensagem");
		
		LayoutParams lp_MATCH_WRAP = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);			
		lp_MATCH_WRAP.setMargins(0, 20, 0, 0);		

		Button botaoEmviarMensagem = meusWidgetsBuilder.criaBotao("Enviar Mensagem", lp_MATCH_WRAP);	
		botaoEmviarMensagem.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				acaoAposClique(llTela, imovel.getCodImovel(), etNome, etEmail, etTelefone, etMensagem);
			}
		});

		llTela.addView(llFotosHolder);
		llTela.addView(meusWidgetsBuilder.criaLinearLayoutLINHA("Tipo de imóvel: ", meusWidgetsBuilder.criaTextViewCONTEUDO(imovel.getSubtipoImovel())));
		llTela.addView(meusWidgetsBuilder.criaLinearLayoutLINHA("Tipo de oferta: ", meusWidgetsBuilder.criaTextViewCONTEUDO(imovel.getSubTipoOferta())));
		llTela.addView(meusWidgetsBuilder.criaLinearLayoutLINHA("Valor: ", meusWidgetsBuilder.criaTextViewCONTEUDO(""+imovel.getPrecoVenda())));
		llTela.addView(meusWidgetsBuilder.criaLinearLayoutLINHA("Dormitório(s): ", meusWidgetsBuilder.criaTextViewCONTEUDO(""+imovel.getDormitorios())));
		llTela.addView(meusWidgetsBuilder.criaLinearLayoutLINHA("Suíte(s): ", meusWidgetsBuilder.criaTextViewCONTEUDO(""+imovel.getSuites())));
		llTela.addView(meusWidgetsBuilder.criaLinearLayoutLINHA("Vaga(s): ", meusWidgetsBuilder.criaTextViewCONTEUDO(""+imovel.getVagas())));
		llTela.addView(meusWidgetsBuilder.criaLinearLayoutLINHA("Área util: ", meusWidgetsBuilder.criaTextViewCONTEUDO(""+imovel.getAreaUtil()+"m²")));
		llTela.addView(meusWidgetsBuilder.criaLinearLayoutLINHA("Área total: ", meusWidgetsBuilder.criaTextViewCONTEUDO(""+imovel.getAreaTotal()+"m²")));
		llTela.addView(meusWidgetsBuilder.criaLinearLayoutLINHA("Data de atualização: ", meusWidgetsBuilder.criaTextViewCONTEUDO(imovel.getDataAtualizacao())));	

		//llTela.addView(meusWidgetsBuilder.criaLinearLayoutLINHAet("Nome: ", etNome));
		//llTela.addView(meusWidgetsBuilder.criaLinearLayoutLINHAet("E-Mail: ", etEmail));
		//llTela.addView(meusWidgetsBuilder.criaLinearLayoutLINHAet("Telefone: ", etTelefone));
		//llTela.addView(meusWidgetsBuilder.criaLinearLayoutLINHAet("Mensagem: ", etMensagem));
		//llTela.addView(botaoEmviarMensagem);

		scrollViewTela.addView(llTela);

		return scrollViewTela;
	}
	
	private void acaoAposClique(LinearLayout llTela, int codImovel, EditText etNome, EditText etEmail, EditText etTelefone, EditText etMensagem){
	
		if(faltaPreencherEditText(llTela)){
		
			new MeuAlerta("Atenção", "Favor preencher todos os campos", context).meuAlertaOk();
		}
		else{
		    Contato contato = new Contato();
			contato.setCodImovel(codImovel);
			contato.setNome(etNome.getText().toString());
			contato.setEmail(etEmail.getText().toString());
			contato.setTelefone(etTelefone.getText().toString());
			contato.setMensagem(etMensagem.getText().toString());
	
			enviaMensagemWS(contato);
		}
	}
	
	public boolean faltaPreencherEditText(LinearLayout llTela) {

		boolean temVazio = false;

		for (int i = 0; i < llTela.getChildCount(); i++) {

			Object child = (Object) llTela.getChildAt(i);

			if (child instanceof LinearLayout) {

				LinearLayout linearLayoutChild = (LinearLayout) child;

				for (int x = 0; x < linearLayoutChild.getChildCount(); x++) {

					View view = (View) linearLayoutChild.getChildAt(x);

					if (view instanceof EditText) {

						EditText editTextChild = (EditText) view;

						if(editTextChild.getText().toString().isEmpty() ){
						
							temVazio = true;
							break;
						}	
					}
				}
			}
		}
		return temVazio;
	}

	private void enviaMensagemWS(Contato contato) {
	    
	    final ProgressDialog progressDialog = MeuProgressDialog.criaProgressDialog(context, "Enviando mensagem...");

		String url = IpURL.URL_SERVER_REST.getValor() + RESOURCE_ENVIA_MENSAGEM;
		
		MontaJSONObjectGenerico montaJSONObject = new MontaJSONObjectGenerico();
		
		JsonObjectRequest jsonObjRequest = new JsonObjectRequest(

				Request.Method.POST, 
				url, 
				montaJSONObject.montaJSONObject(contato),

				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject jSONObjectResposta) {

						respostaBuscaUsuarioWS(jSONObjectResposta, progressDialog);				
					}
				}, 
				new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError volleyError) {
						
						MeuProgressDialog.encerraProgressDialog(progressDialog);
	
						new MeuAlerta("volleyError", volleyError.toString(), context).meuAlertaOk();							
					}
				});

		jsonObjRequest.setRetryPolicy(VolleyTimeout.recuperarTimeout());

		requestQueue.add(jsonObjRequest);		
	}
	
	private void respostaBuscaUsuarioWS(JSONObject jSONObjectResposta, ProgressDialog progressDialog) {
		
		MeuProgressDialog.encerraProgressDialog(progressDialog);

		new MeuAlerta("jSONObjectResposta", ""+jSONObjectResposta, context).meuAlertaOk();									
	}


}
