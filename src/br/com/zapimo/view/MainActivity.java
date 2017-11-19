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
import android.content.Intent;
import android.os.Bundle;
import br.com.zapimo.dao.Dao;
import br.com.zapimo.model.Imoveis;
import br.com.zapimo.util.IpURL;
import br.com.zapimo.util.JsonUtil;
import br.com.zapimo.util.MeuAlerta;
import br.com.zapimo.util.MeuProgressDialog;
import br.com.zapimo.util.VolleySingleton;
import br.com.zapimo.util.VolleyTimeout;

public class MainActivity extends Activity {

	private static final String RESOURCE_LISTA_IMOVEIS = "/imoveis";	
	private RequestQueue queue;
	private Context context;	
	private Dao dao;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		context = MainActivity.this;

		dao = new Dao(context);		
							
		buscaImoveisWS();	
	}
		
	private void buscaImoveisWS() {
		
		queue = VolleySingleton.getInstance(context).getRequestQueue();

		String url = IpURL.URL_SERVER_REST.getValor() + RESOURCE_LISTA_IMOVEIS;
		
	    final ProgressDialog progressDialog = MeuProgressDialog.criaProgressDialog(context, "Buscando Imóveis...");

		JsonObjectRequest jsonObjRequest = new JsonObjectRequest(

				Request.Method.GET, url, null,

				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject jSONObjectResposta) {

						respostaBuscaImoveisWS(jSONObjectResposta, progressDialog);						
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

	private void respostaBuscaImoveisWS(JSONObject jSONObjectResposta, ProgressDialog progressDialog) {
					
		dao.deletaTodosDados();					
	
		JsonUtil jsonUtil = new JsonUtil();
					
		boolean deuErro = jsonUtil.insereInformacoesDoJsonNoBancoDeDados(context, jSONObjectResposta, dao, Imoveis.class);
				
		MeuProgressDialog.encerraProgressDialog(progressDialog);

		if(deuErro){

			new MeuAlerta("Erro", "Erro ao tentar inserir dados", context).meuAlertaOk();
		}else{		
			abreListaImoveis();
		}				
	}

	private void abreListaImoveis() {
				
		Intent intent = new Intent(MainActivity.this, ListaImoveisActivity.class);
		startActivity(intent);
		finish();
	}
	
}
