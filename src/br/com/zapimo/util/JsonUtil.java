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

	public Object devolveObjetoDaClasseInformada(String nomeDoHolder, Class<?> classe, JSONObject jSONObjectImovel) throws Exception {

		Object objectInstance = null;
		
			if(jSONObjectImovel.has(nomeDoHolder)){
			
				JSONObject jSONObjectImoveis = (JSONObject) jSONObjectImovel.get(nomeDoHolder);
			
				objectInstance = classe.getConstructor().newInstance();

			for (Field field : classe.getDeclaredFields()) {

				field.setAccessible(true);

				if (!field.getName().contains("COLUMN")) {

					if(jSONObjectImoveis.has(field.getName())){

						
						if(field.getType() == java.util.List.class){
							
							JSONArray jSONArrayy = jSONObjectImoveis.getJSONArray(field.getName());
							
							List<String> lista = devolveListaDeStrings(jSONArrayy);
					
							field.set(objectInstance, lista);
						}
						
						if(field.getType() == Cliente.class) {
							
							JSONObject jSONObjInterno = (JSONObject) jSONObjectImoveis.get(field.getName());
							
							field.set(objectInstance, devolveObjetoPopulado(Cliente.class, jSONObjInterno));
						} 
					
						if(field.getType() == Endereco.class) {
		
							JSONObject jSONObjInterno = (JSONObject) jSONObjectImoveis.get(field.getName());
							
							field.set(objectInstance, devolveObjetoPopulado(Endereco.class, jSONObjInterno));	
						} 
					
						if(field.getType() == int.class) {

							field.setInt(objectInstance, jSONObjectImoveis.getInt(field.getName()));
						}
					
						if(field.getType() == String.class){
						
							if(jSONObjectImoveis.getString(field.getName()) == null){
								
								field.set(objectInstance, "nulo");
							}	
							else{		
								
								field.set(objectInstance, jSONObjectImoveis.getString(field.getName()));
							}		
						}
					}			
				}	
			}
			
			}
			
			
		return objectInstance;
	}
	
	private List<String> devolveListaDeStrings(JSONArray jSONArray) throws Exception {

		List<String> lista = new ArrayList<String>();

		for (int i = 0; i < jSONArray.length(); i++) {

			String string = jSONArray.getString(i);
	
			lista.add(string);
		}
		return lista;
	}

	public boolean insereInformacoesDoJsonNoBancoDeDadosQuandoHolderEhJSONArray(Context context, JSONObject jSONObjectResposta, Dao dao, Class<?> classeHOLDER) {
		
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

					if(jSONObject.has(field.getName())){

						if(field.getType() == Cliente.class) {
							
							JSONObject jSONObjInterno = (JSONObject) jSONObject.get(field.getName());
							
							field.set(objectInstance, devolveObjetoPopulado(Cliente.class, jSONObjInterno));
						} 
					
						if(field.getType() == Endereco.class) {
		
							JSONObject jSONObjInterno = (JSONObject) jSONObject.get(field.getName());
							
							field.set(objectInstance, devolveObjetoPopulado(Endereco.class, jSONObjInterno));	
						} 
					
						if(field.getType() == int.class) {

							field.setInt(objectInstance, jSONObject.getInt(field.getName()));
						}
					
						if(field.getType() == String.class){
						
							if(jSONObject.getString(field.getName()) == null){
								
								field.set(objectInstance, "nulo");
							}	
							else{		
								
								field.set(objectInstance, jSONObject.getString(field.getName()));
							}		
						}
					}			
				}
			}
			lista.add((E) objectInstance);
		}
		return lista;
	}

	private Object devolveObjetoPopulado(Class<?> classe, JSONObject jSONObject) {

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
