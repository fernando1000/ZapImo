package br.com.zapimo.view;

import java.util.ArrayList;

import org.json.JSONException;
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
import br.com.zapimo.model.Endereco;
import br.com.zapimo.model.Imoveis;
import br.com.zapimo.util.IpURL;
import br.com.zapimo.util.JsonUtil;
import br.com.zapimo.util.MeuAlerta;
import br.com.zapimo.util.MeuProgressDialog;
import br.com.zapimo.util.TelaBuilder;
import br.com.zapimo.util.MontaJSONObjectGenerico;
import br.com.zapimo.util.PercorreViews;
import br.com.zapimo.util.Moeda;
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
	private TelaBuilder telaBuilder;
	private LinearLayout llTela;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	    Bundle bundle = getIntent().getExtras();
	    imovelDoSerializable = (Imoveis) bundle.getSerializable("Imoveis");

		context = DetalheDoImovel.this;
						
		telaBuilder = new TelaBuilder(context);

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

		setContentView(devolveTela());
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

						adicionaImagensNoLinearLayoutFotosInternas();
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
				
				adicionaImagensNoLinearLayoutFotosInternas();
				adicionaLinearLayoutEnderecoNaTela();
				adicionaLinearLayoutClienteNaTela();
				adicionaLinearLayoutObservacaoNaTela();
				adicionaLinearLayoutCaracteristicasNaTela();
				adicionaLinearLayoutCaracteristicasComumNaTela();
			}	
		} 
		catch (Exception erro) {
	
			new MeuAlerta("Erro", "Erro imprevisto"+erro, context).meuAlertaOk();	
		}					
	}
	
	private void adicionaLinearLayoutEnderecoNaTela(){
		
		LinearLayout ll = telaBuilder.criaLinearLayoutcomBordaArredondada();
		
		Endereco endereco = imovelDoWS.getEndereco();
		
		ll.addView(telaBuilder.criaTextViewTITULO("Endereço"));	
		ll.addView(telaBuilder.criaTextViewCONTEUDO(endereco.getLogradouro()+" "+endereco.getNumero()+" "+endereco.getComplemento()+", "+ 
														   endereco.getBairro() +", "+
														   endereco.getCidade() + "/"+
														   endereco.getEstado() + " - "+
														   endereco.getZona() + " - "+
														   endereco.getCEP()));
	
		llTela.addView(ll);	
		
	}

	private void adicionaLinearLayoutClienteNaTela(){

		LinearLayout ll = telaBuilder.criaLinearLayoutcomBordaArredondada();

		ll.addView(telaBuilder.criaLinearLayoutLINHA("Código do cliente: ", telaBuilder.criaTextViewCONTEUDO(""+imovelDoWS.getCliente().getCodCliente())));	
		ll.addView(telaBuilder.criaLinearLayoutLINHA("Nome do Cliente: ", telaBuilder.criaTextViewCONTEUDO(imovelDoWS.getCliente().getNomeFantasia())));	

		llTela.addView(ll);	
	}
	
	private void adicionaLinearLayoutObservacaoNaTela(){

		LinearLayout ll = telaBuilder.criaLinearLayoutcomBordaArredondada();

		ll.addView(telaBuilder.criaTextViewTITULO("Observação"));	
		ll.addView(telaBuilder.criaTextViewCONTEUDO(imovelDoWS.getObservacao()));	

		llTela.addView(ll);	
	}

	private void adicionaLinearLayoutCaracteristicasNaTela(){

		LinearLayout ll = telaBuilder.criaLinearLayoutcomBordaArredondada();

		ll.addView(telaBuilder.criaTextViewTITULO("Caracteristicas"));	

		for(String caracteristica : imovelDoWS.getCaracteristicas()) {
			
			ll.addView(telaBuilder.criaTextViewCONTEUDO("°"+caracteristica));	
		}	
		
		llTela.addView(ll);			
	}

	private void adicionaLinearLayoutCaracteristicasComumNaTela(){

		LinearLayout ll = telaBuilder.criaLinearLayoutcomBordaArredondada();

		ll.addView(telaBuilder.criaTextViewTITULO("Caracteristicas comum"));	

		for(String caracComum : imovelDoWS.getCaracteristicasComum()) {
			
			ll.addView(telaBuilder.criaTextViewCONTEUDO("°"+caracComum));	
		}

		llTela.addView(ll);
	}

	private ScrollView devolveTela() {
		
		ScrollView scrollViewTela = telaBuilder.criaScrollView();
		scrollViewTela.setBackgroundColor(context.getResources().getColor(R.color.azul_claro));

		llTela = telaBuilder.criaLinearLayoutTELA();
		
		LinearLayout llFotosHolder = new LinearLayout(context);
		llFotosHolder.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 700));
			
		HorizontalScrollView horizontalScrollView = new HorizontalScrollView(context); 
		horizontalScrollView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

		llFotosInternas = new LinearLayout(context);
		llFotosInternas.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		
		adicionaImagensNoLinearLayoutFotosInternas();
		
		horizontalScrollView.addView(llFotosInternas);
		
		llFotosHolder.addView(horizontalScrollView);
		
		llTela.addView(llFotosHolder);
		
		adicionaLinearLayoutDescricaoImovelNaTela();
		
		adicionaLinearLayoutMensagemNaTela();
		
		scrollViewTela.addView(llTela);

		return scrollViewTela;
	}

	private void adicionaLinearLayoutMensagemNaTela(){
				
		LinearLayout ll = telaBuilder.criaLinearLayoutcomBordaArredondada();

		final EditText etNome = telaBuilder.criaEditText("nome");
		final EditText etEmail = telaBuilder.criaEditText("email");
		final EditText etTelefone = telaBuilder.criaEditText("telefone");
		final EditText etMensagem = telaBuilder.criaEditText("Mensagem");

		LayoutParams lp_MATCH_WRAP = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);			
					 lp_MATCH_WRAP.setMargins(0, 20, 0, 0);		

		Button botaoEnviarMensagem = telaBuilder.criaBotao("Enviar Mensagem", lp_MATCH_WRAP);	
		botaoEnviarMensagem.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				acaoAposClique(imovelDoSerializable.getCodImovel(), etNome, etEmail, etTelefone, etMensagem);
			}
		});

		ll.addView(telaBuilder.criaLinearLayoutLINHAet("Nome: ", etNome));
		ll.addView(telaBuilder.criaLinearLayoutLINHAet("E-Mail: ", etEmail));
		ll.addView(telaBuilder.criaLinearLayoutLINHAet("Telefone: ", etTelefone));
		ll.addView(telaBuilder.criaLinearLayoutLINHAet("Mensagem: ", etMensagem));	
		ll.addView(botaoEnviarMensagem);
		
		llTela.addView(ll);
	}

	private void adicionaLinearLayoutDescricaoImovelNaTela(){
		
		Moeda reais = new Moeda();
		
		LinearLayout ll = telaBuilder.criaLinearLayoutcomBordaArredondada();

		ll.addView(telaBuilder.criaLinearLayoutLINHA("Tipo de imóvel: ", telaBuilder.criaTextViewCONTEUDO(imovelDoSerializable.getSubtipoImovel())));
		ll.addView(telaBuilder.criaLinearLayoutLINHA("Tipo de oferta: ", telaBuilder.criaTextViewCONTEUDO(imovelDoSerializable.getSubTipoOferta())));
		ll.addView(telaBuilder.criaLinearLayoutLINHA("Valor: ", telaBuilder.criaTextViewCONTEUDO(reais.desenhaReaisComPontoEvirgula(""+imovelDoSerializable.getPrecoVenda(), 2, 1))));
		ll.addView(telaBuilder.criaLinearLayoutLINHA("Dormitório(s): ", telaBuilder.criaTextViewCONTEUDO(""+imovelDoSerializable.getDormitorios())));
		ll.addView(telaBuilder.criaLinearLayoutLINHA("Suíte(s): ", telaBuilder.criaTextViewCONTEUDO(""+imovelDoSerializable.getSuites())));
		ll.addView(telaBuilder.criaLinearLayoutLINHA("Vaga(s): ", telaBuilder.criaTextViewCONTEUDO(""+imovelDoSerializable.getVagas())));
		ll.addView(telaBuilder.criaLinearLayoutLINHA("Área util: ", telaBuilder.criaTextViewCONTEUDO(""+imovelDoSerializable.getAreaUtil()+"m²")));
		ll.addView(telaBuilder.criaLinearLayoutLINHA("Área total: ", telaBuilder.criaTextViewCONTEUDO(""+imovelDoSerializable.getAreaTotal()+"m²")));

		llTela.addView(ll);

	}

	private void adicionaImagensNoLinearLayoutFotosInternas() {
		
		for(String urlImagem : listaComImagens) {
			
			ImageView imageViewFotos = new ImageView(context);
			imageViewFotos.setAdjustViewBounds(true);
			imageViewFotos.setImageDrawable(context.getResources().getDrawable(R.drawable.casa));
			imageViewFotos.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT));
			
			llFotosInternas.addView(imageViewFotos);
	
			imageLoader.get(urlImagem, imageLoader.getImageListener(imageViewFotos, R.drawable.casa, R.drawable.erro));
		}
	}
	
	private void acaoAposClique(int codImovel, EditText etNome, EditText etEmail, EditText etTelefone, EditText etMensagem){
	
		PercorreViews percorreViews = new PercorreViews();
		
		if(percorreViews.faltaPreencherEditText(llTela)){
		
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

		try {
			if(jSONObjectResposta.has("msg")) {
			
				String respostaDoPOST = jSONObjectResposta.getString("msg");
				
				if(respostaDoPOST.equalsIgnoreCase("ok")){
					
					new MeuAlerta("Aviso", "Mensagem enviada com sucesso!", context).meuAlertaOk();									
				}else {
					new MeuAlerta("Erro", "Erro no envio dos dados, favor tentar novamente", context).meuAlertaOk();									
				}			
			}
		} 
		catch (JSONException jSONException) {
		
			new MeuAlerta("Erro", ""+jSONException, context).meuAlertaOk();													
		}
	}


}
