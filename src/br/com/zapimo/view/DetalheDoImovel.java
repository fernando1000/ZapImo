package br.com.zapimo.view;

import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.LinearLayout.LayoutParams;
import br.com.zapimo.model.Contato;
import br.com.zapimo.model.Imoveis;
import br.com.zapimo.util.IpURL;
import br.com.zapimo.util.MeuAlerta;
import br.com.zapimo.util.MeuProgressDialog;
import br.com.zapimo.util.MeusWidgetsBuilder;
import br.com.zapimo.util.MontaJSONObjectGenerico;
import br.com.zapimo.util.VolleyTimeout;

public class DetalheDoImovel extends Activity {

	private static final String RESOURCE_ENVIA_MENSAGEM = "/imoveis/contato";	
	private RequestQueue queue;
	private Context context;	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	    Bundle bundle = getIntent().getExtras();
	    final Imoveis imovel = (Imoveis) bundle.getSerializable("Imoveis");

		context = DetalheDoImovel.this;
		
		MeusWidgetsBuilder meusWidgetsBuilder = new MeusWidgetsBuilder(context);
		
		ScrollView scrollView = meusWidgetsBuilder.criaScrollView();
		
		final LinearLayout llTela = meusWidgetsBuilder.criaLinearLayoutTELA();
		
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


        	
        	
		llTela.addView(meusWidgetsBuilder.criaLinearLayoutLINHA("CodImovel: ", meusWidgetsBuilder.criaTextViewCONTEUDO(""+imovel.getCodImovel())));
		llTela.addView(meusWidgetsBuilder.criaLinearLayoutLINHA("TipoImovel: ", meusWidgetsBuilder.criaTextViewCONTEUDO(imovel.getTipoImovel())));
		llTela.addView(meusWidgetsBuilder.criaLinearLayoutLINHA("PrecoVenda: ", meusWidgetsBuilder.criaTextViewCONTEUDO(""+imovel.getPrecoVenda())));
		llTela.addView(meusWidgetsBuilder.criaLinearLayoutLINHA("Dormitorios: ", meusWidgetsBuilder.criaTextViewCONTEUDO(""+imovel.getDormitorios())));
		llTela.addView(meusWidgetsBuilder.criaLinearLayoutLINHA("Suites: ", meusWidgetsBuilder.criaTextViewCONTEUDO(""+imovel.getSuites())));
		llTela.addView(meusWidgetsBuilder.criaLinearLayoutLINHA("Vagas: ", meusWidgetsBuilder.criaTextViewCONTEUDO(""+imovel.getVagas())));
		llTela.addView(meusWidgetsBuilder.criaLinearLayoutLINHA("AreaUtil: ", meusWidgetsBuilder.criaTextViewCONTEUDO(""+imovel.getAreaUtil())));
		llTela.addView(meusWidgetsBuilder.criaLinearLayoutLINHA("AreaTotal: ", meusWidgetsBuilder.criaTextViewCONTEUDO(""+imovel.getAreaTotal())));
		llTela.addView(meusWidgetsBuilder.criaLinearLayoutLINHA("DataAtualizacao: ", meusWidgetsBuilder.criaTextViewCONTEUDO(imovel.getDataAtualizacao())));
		llTela.addView(meusWidgetsBuilder.criaLinearLayoutLINHA("UrlImagem: ", meusWidgetsBuilder.criaTextViewCONTEUDO(imovel.getUrlImagem())));
		llTela.addView(meusWidgetsBuilder.criaLinearLayoutLINHA("SubTipoOferta: ", meusWidgetsBuilder.criaTextViewCONTEUDO(imovel.getSubTipoOferta())));
		llTela.addView(meusWidgetsBuilder.criaLinearLayoutLINHA("SubtipoImovel: ", meusWidgetsBuilder.criaTextViewCONTEUDO(imovel.getSubtipoImovel())));

		llTela.addView(meusWidgetsBuilder.criaLinearLayoutLINHAet("Nome: ", etNome));
		llTela.addView(meusWidgetsBuilder.criaLinearLayoutLINHAet("E-Mail: ", etEmail));
		llTela.addView(meusWidgetsBuilder.criaLinearLayoutLINHAet("Telefone: ", etTelefone));
		llTela.addView(meusWidgetsBuilder.criaLinearLayoutLINHAet("Mensagem: ", etMensagem));
		llTela.addView(botaoEmviarMensagem);

		scrollView.addView(llTela);
		
		setContentView(scrollView);
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

		queue.add(jsonObjRequest);		
	}
	
	private void respostaBuscaUsuarioWS(JSONObject jSONObjectResposta, ProgressDialog progressDialog) {
		
		MeuProgressDialog.encerraProgressDialog(progressDialog);

		new MeuAlerta("jSONObjectResposta", ""+jSONObjectResposta, context).meuAlertaOk();									
	}


}
