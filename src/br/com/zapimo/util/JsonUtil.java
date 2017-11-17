package br.com.zapimo.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import android.content.Context;
import android.widget.Toast;
import br.com.zapimo.dao.Dao;
import br.com.zapimo.model.Cliente;
import br.com.zapimo.model.Endereco;
import br.com.zapimo.model.Imoveis;

public class JsonUtil {
		
	public boolean insereInformacoesDoJsonNoBancoDeDados(Context context, JSONObject jSONObjectResposta, Dao dao, Class<?> classeHOLDER) {
		
		boolean deuErro = false;
		
		try {
			
		Class<?> classe = classeHOLDER;
		
			if(jSONObjectResposta.has(classe.getSimpleName())){
				
				JSONArray jSONArrayy = jSONObjectResposta.getJSONArray(classe.getSimpleName());
			
				List<Imoveis> lista = (List<Imoveis>) devolveListaDaClasseInformada(classe, jSONArrayy);
		
				for (Imoveis imovel : lista) {
					
					dao.insereOUatualiza(imovel, 
									 	 Imoveis.COLUMN_INTEGER_CODIMOVEL, imovel.getCodImovel());
				}						
			}
			else{		
				
				deuErro = true;
				
				Toast.makeText(context, "Não encontrou a classe "+classe.getSimpleName(), Toast.LENGTH_LONG).show();
			}
		
		} 
		catch (Exception e) {
			
			deuErro = true;
			e.printStackTrace();
		}

		return deuErro;
	}
	
	private <E> List<E> devolveListaDaClasseInformada(Class<E> classe, JSONArray jSONArray) throws Exception {

		List<E> lista = new ArrayList<E>();

		for (int i = 0; i < jSONArray.length(); i++) {

			JSONObject jSONObject = jSONArray.getJSONObject(i);

			Object objectInstance = classe.getConstructor().newInstance();

			for (Field field : classe.getDeclaredFields()) {

				field.setAccessible(true);

				if (!field.getName().contains("COLUMN")) {

					if(field.getType() == Cliente.class) {
						
						if(jSONObject.has(field.getName())){
							
							JSONObject jSONObjInterno = (JSONObject) jSONObject.get(field.getName());
							
							field.set(objectInstance, devolveObjetoPopulado(Cliente.class, jSONObjInterno));
						}			
					} 
					
					if(field.getType() == Endereco.class) {

						if(jSONObject.has(field.getName())){
							
							JSONObject jSONObjInterno = (JSONObject) jSONObject.get(field.getName());
							
							field.set(objectInstance, devolveObjetoPopulado(Endereco.class, jSONObjInterno));
						}			
					} 
					
					if(field.getType() == int.class) {

						field.setInt(objectInstance, jSONObject.getInt(field.getName()));
					}
					
					if(field.getType() == String.class){
						
						field.set(objectInstance, jSONObject.getString(field.getName()));
					}
				}
			}
			lista.add((E) objectInstance);
		}
		return lista;
	}

	public Object devolveObjetoPopulado(Class<?> classe, JSONObject jSONObject) {

		Object objectInstance = null;
		try {
		objectInstance = classe.getConstructor().newInstance();
	
				for (Field field : classe.getDeclaredFields()) {
	
					field.setAccessible(true);
	
					if (!field.getName().contains("COLUMN")) {
						
						if (field.getType() == int.class || field.getType() == String.class) {
	
							if(jSONObject.has(field.getName())){
							
								if (field.getType() == int.class) {
			
									field.setInt(objectInstance, jSONObject.getInt(field.getName()));
								}
								
								if(field.getType() == String.class){
									
									field.set(objectInstance, jSONObject.getString(field.getName()));	
								}						
							}
						}
					}
				}
		} 
		catch (Exception e) {
			e.printStackTrace();
		}

		return objectInstance;
	}


}
