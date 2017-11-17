package br.com.zapimo.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

import br.com.zapimo.dao.Dao;

public class VarreJsonArray {
	
	public static <E> List<E> devolveLista(Class<E> classe, JSONArray jSONArray) {

		List<E> lista = new ArrayList<E>();

		try {
			
			for (int i = 0; i < jSONArray.length(); i++) {
	
				JSONObject jSONObject = jSONArray.getJSONObject(i);
	
				Object objectInstance = classe.getConstructor().newInstance();
	
				for (Field field : classe.getDeclaredFields()) {
	
					field.setAccessible(true);
	
					if (!field.getName().contains("COLUMN")) {
						
						if (field.getType() == int.class || field.getType() == String.class) {
	
							if(jSONObject.has(field.getName())){
							
								if (field.getType() == int.class) {
			
									field.setInt(objectInstance, jSONObject.getInt(field.getName()));
								} 
								else {
									field.set(objectInstance, jSONObject.getString(field.getName()));	
								}						
							}
						}
					}
				}
				lista.add((E) objectInstance);
			}
		
		} 
		catch (Exception e) {
			e.printStackTrace();
		}

		return lista;
	}
	
	public static <E> boolean insereDados(Class<E> classe, JSONArray jSONArray, Dao dao) {

		boolean deuErro = false;
		
		try {
			
			for (int i = 0; i < jSONArray.length(); i++) {
	
				JSONObject jSONObject = jSONArray.getJSONObject(i);
	
				Object objectInstance = classe.getConstructor().newInstance();
	
				for (Field field : classe.getDeclaredFields()) {
	
					field.setAccessible(true);
	
					if (!field.getName().contains("COLUMN")) {
						
						if (field.getType() == int.class || field.getType() == java.lang.String.class) {
	
							if(jSONObject.has(field.getName())){
							
								if (field.getType() == int.class) {
			
									field.setInt(objectInstance, jSONObject.getInt(field.getName()));
								} 
								else {
									field.set(objectInstance, jSONObject.getString(field.getName()));	
								}						
							}
						}
					}
				}
				dao.insereObjeto_final(objectInstance);
			}
		
		} 
		catch (Exception e) {
			
			deuErro = true;
			e.printStackTrace();
		}
		
		return deuErro;
	}


}
