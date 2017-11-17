package br.com.zapimo.util;

import java.lang.reflect.Field;
import org.json.JSONObject;

public class DevolveJsonObjectDeUmaClasse {

	public static JSONObject devolve(Object objeto){
		
		Class<?> classe = objeto.getClass();
		
		JSONObject jSONObject = new JSONObject();
		
		try {

			for(Field field : classe.getDeclaredFields()){
			
					  field.setAccessible(true);
			
			jSONObject.put(field.getName(), field.get(objeto));
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
		} 
		return jSONObject;
	} 
}
