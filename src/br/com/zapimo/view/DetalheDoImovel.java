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
import android.os.Bundle;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.LinearLayout.LayoutParams;
import br.com.zapimo.R;
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
	private LinearLayout llFotosInternas;
	private Imoveis imovelDoSerializable;
	private Imoveis imovelDoWS;
	private MeusWidgetsBuilder meusWidgetsBuilder;
	private LinearLayout llTela;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	    Bundle bundle = getIntent().getExtras();
	    imovelDoSerializable = (Imoveis) bundle.getSerializable("Imoveis");

		context = DetalheDoImovel.this;
						
		meusWidgetsBuilder = new MeusWidgetsBuilder(context);

		listaComImagens = new ArrayList<String>();
		
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
		
		buscaImovel(imovelDoSerializable.getCodImovel());

		setContentView(devolveTelaDetalheDoImovel());
	}
	
	private void buscaImovel(int codImovel) {
		
		requestQueue = VolleySingleton.getInstance(context).getRequestQueue();

		String url = IpURL.URL_SERVER_REST.getValor() + RESOURCE_DETALHE_ITEM + codImovel;
		
	    final ProgressDialog progressDialog = MeuProgressDialog.criaProgressDialog(context, "Buscando Fotos...");

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
						
						listaComImagens.add(imovelDoSerializable.getUrlImagem());

						adicionaFotosNoLlFotosInternas();
					}
				});

		jsonObjRequest.setRetryPolicy(VolleyTimeout.recuperarTimeout());

		requestQueue.add(jsonObjRequest);
	}

	private void respostaBuscaImovelWS(JSONObject jSONObjectImovel, ProgressDialog progressDialog) {
			
		JsonUtil jsonUtil = new JsonUtil();
				
		try {
			imovelDoWS = (Imoveis)jsonUtil.devolveObjetoDaClasseInformada("Imovel", Imoveis.class, jSONObjectImovel);
		
			MeuProgressDialog.encerraProgressDialog(progressDialog);

			if(imovelDoWS != null){
										
				for(String urlDaImagem : imovelDoWS.getFotos()) {
					
					listaComImagens.add(urlDaImagem);					
				}			
				
				adicionaFotosNoLlFotosInternas();
				
				adicionaOutrosWidgetsNaTela();
			}	
		} 
		catch (Exception erro) {
	
			new MeuAlerta("Erro", "Erro imprevisto"+erro, context).meuAlertaOk();	
		}					
	}
	
	private void adicionaOutrosWidgetsNaTela(){
				
		llTela.addView(meusWidgetsBuilder.criaLinearLayoutLINHA("Logradouro: ", meusWidgetsBuilder.criaTextViewCONTEUDO(imovelDoWS.getEndereco().getLogradouro())));	
		llTela.addView(meusWidgetsBuilder.criaLinearLayoutLINHA("Numero: ", meusWidgetsBuilder.criaTextViewCONTEUDO(imovelDoWS.getEndereco().getNumero())));	
		llTela.addView(meusWidgetsBuilder.criaLinearLayoutLINHA("Complemento: ", meusWidgetsBuilder.criaTextViewCONTEUDO(imovelDoWS.getEndereco().getComplemento())));	
		llTela.addView(meusWidgetsBuilder.criaLinearLayoutLINHA("CEP: ", meusWidgetsBuilder.criaTextViewCONTEUDO(""+imovelDoWS.getEndereco().getCEP())));	
		llTela.addView(meusWidgetsBuilder.criaLinearLayoutLINHA("Bairro: ", meusWidgetsBuilder.criaTextViewCONTEUDO(imovelDoWS.getEndereco().getBairro())));	
		llTela.addView(meusWidgetsBuilder.criaLinearLayoutLINHA("Cidade: ", meusWidgetsBuilder.criaTextViewCONTEUDO(imovelDoWS.getEndereco().getCidade())));	
		llTela.addView(meusWidgetsBuilder.criaLinearLayoutLINHA("Estado: ", meusWidgetsBuilder.criaTextViewCONTEUDO(imovelDoWS.getEndereco().getEstado())));	
		llTela.addView(meusWidgetsBuilder.criaLinearLayoutLINHA("Zona: ", meusWidgetsBuilder.criaTextViewCONTEUDO(imovelDoWS.getEndereco().getZona())));	

		llTela.addView(meusWidgetsBuilder.criaLinearLayoutLINHA("Código do cliente: ", meusWidgetsBuilder.criaTextViewCONTEUDO(""+imovelDoWS.getCliente().getCodCliente())));	
		llTela.addView(meusWidgetsBuilder.criaLinearLayoutLINHA("Nome do Cliente: ", meusWidgetsBuilder.criaTextViewCONTEUDO(imovelDoWS.getCliente().getNomeFantasia())));	

		llTela.addView(meusWidgetsBuilder.criaLinearLayoutLINHA("Observação: ", meusWidgetsBuilder.criaTextViewCONTEUDO(imovelDoWS.getObservacao())));	
		
		
		for(String carac : imovelDoWS.getCaracteristicas()) {
			
			llTela.addView(meusWidgetsBuilder.criaLinearLayoutLINHA("Caracteristicas: ", meusWidgetsBuilder.criaTextViewCONTEUDO(carac)));	
		}
	
		for(String caracComum : imovelDoWS.getCaracteristicasComum()) {
			
			llTela.addView(meusWidgetsBuilder.criaLinearLayoutLINHA("Caracteristicas comum: ", meusWidgetsBuilder.criaTextViewCONTEUDO(caracComum)));	
		}
	
		
		
		
		
		
	
	}

	private ScrollView devolveTelaDetalheDoImovel() {
		
		ScrollView scrollViewTela = meusWidgetsBuilder.criaScrollView();
		
		llTela = meusWidgetsBuilder.criaLinearLayoutTELA();
		
		LinearLayout llFotosHolder = new LinearLayout(context);
		llFotosHolder.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 700));
			
		HorizontalScrollView horizontalScrollView = new HorizontalScrollView(context); 
		horizontalScrollView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

		llFotosInternas = new LinearLayout(context);
		llFotosInternas.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		
		adicionaFotosNoLlFotosInternas();
		
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
				
				acaoAposClique(imovelDoSerializable.getCodImovel(), etNome, etEmail, etTelefone, etMensagem);
			}
		});

		llTela.addView(llFotosHolder);
		llTela.addView(meusWidgetsBuilder.criaLinearLayoutLINHA("Tipo de imóvel: ", meusWidgetsBuilder.criaTextViewCONTEUDO(imovelDoSerializable.getSubtipoImovel())));
		llTela.addView(meusWidgetsBuilder.criaLinearLayoutLINHA("Tipo de oferta: ", meusWidgetsBuilder.criaTextViewCONTEUDO(imovelDoSerializable.getSubTipoOferta())));
		llTela.addView(meusWidgetsBuilder.criaLinearLayoutLINHA("Valor: ", meusWidgetsBuilder.criaTextViewCONTEUDO(""+imovelDoSerializable.getPrecoVenda())));
		llTela.addView(meusWidgetsBuilder.criaLinearLayoutLINHA("Dormitório(s): ", meusWidgetsBuilder.criaTextViewCONTEUDO(""+imovelDoSerializable.getDormitorios())));
		llTela.addView(meusWidgetsBuilder.criaLinearLayoutLINHA("Suíte(s): ", meusWidgetsBuilder.criaTextViewCONTEUDO(""+imovelDoSerializable.getSuites())));
		llTela.addView(meusWidgetsBuilder.criaLinearLayoutLINHA("Vaga(s): ", meusWidgetsBuilder.criaTextViewCONTEUDO(""+imovelDoSerializable.getVagas())));
		llTela.addView(meusWidgetsBuilder.criaLinearLayoutLINHA("Área util: ", meusWidgetsBuilder.criaTextViewCONTEUDO(""+imovelDoSerializable.getAreaUtil()+"m²")));
		llTela.addView(meusWidgetsBuilder.criaLinearLayoutLINHA("Área total: ", meusWidgetsBuilder.criaTextViewCONTEUDO(""+imovelDoSerializable.getAreaTotal()+"m²")));
		llTela.addView(meusWidgetsBuilder.criaLinearLayoutLINHA("Data de atualização: ", meusWidgetsBuilder.criaTextViewCONTEUDO(imovelDoSerializable.getDataAtualizacao())));	

		
		
		
		
		//llTela.addView(meusWidgetsBuilder.criaLinearLayoutLINHAet("Nome: ", etNome));
		//llTela.addView(meusWidgetsBuilder.criaLinearLayoutLINHAet("E-Mail: ", etEmail));
		//llTela.addView(meusWidgetsBuilder.criaLinearLayoutLINHAet("Telefone: ", etTelefone));
		//llTela.addView(meusWidgetsBuilder.criaLinearLayoutLINHAet("Mensagem: ", etMensagem));
		//llTela.addView(botaoEmviarMensagem);

		scrollViewTela.addView(llTela);

		return scrollViewTela;
	}
	
	private void adicionaFotosNoLlFotosInternas() {
		
		for(String urlImagem : listaComImagens) {
			
			ImageView imageViewFotos = new ImageView(context);
			imageViewFotos.setAdjustViewBounds(true);
			imageViewFotos.setImageDrawable(context.getResources().getDrawable(R.drawable.carregamento));
			imageViewFotos.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT));
			
			llFotosInternas.addView(imageViewFotos);
	
			imageLoader.get(urlImagem, imageLoader.getImageListener(imageViewFotos, R.drawable.carregamento, R.drawable.erro));
		}
	}
	
	private void acaoAposClique(int codImovel, EditText etNome, EditText etEmail, EditText etTelefone, EditText etMensagem){
	
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

						respostaEnviaMensagemWS(jSONObjectResposta, progressDialog);				
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
	
	private void respostaEnviaMensagemWS(JSONObject jSONObjectResposta, ProgressDialog progressDialog) {
		
		MeuProgressDialog.encerraProgressDialog(progressDialog);

		new MeuAlerta("jSONObjectResposta", ""+jSONObjectResposta, context).meuAlertaOk();									
	}


}
