package br.com.zapimo.util;

import org.json.JSONArray;
import org.json.JSONObject;

public class MontaJSONObjectGenerico {
		
	public JSONObject montaJSONObject(Object objeto) {

		JSONObject jSONObject = new JSONObject();

		try {			
													 JSONArray jSONArray = new JSONArray();				
															   jSONArray.put(DevolveJsonObjectDeUmaClasse.devolve(objeto));				
			jSONObject.put(objeto.getClass().getSimpleName(), jSONArray);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return jSONObject;
	}
	
}
