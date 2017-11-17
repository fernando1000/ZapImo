package br.com.zapimo.dao;

import java.lang.reflect.Field;
import android.database.Cursor;

public class PreencheObjetoUsandoCursor {

	public static Object devolveObjetoPreenchido(Class<?> classe, Cursor cursor) {
	
		Object objectInstance = null;

		try {
			objectInstance = classe.getConstructor().newInstance();

			for (Field field : classe.getDeclaredFields()) {

				field.setAccessible(true);

				if (!field.getName().contains("COLUMN")) {
					
					if (field.getType() == int.class || field.getType() == java.lang.String.class) {

						if (field.getType() == int.class) {
	
							field.setInt(objectInstance, cursor.getInt(cursor.getColumnIndex(field.getName())));
						} 	
						else {
							
							if (null == cursor.getString(cursor.getColumnIndex(field.getName()))) {

								field.set(objectInstance, "equals(null)");
							} 
							else {
								field.set(objectInstance, cursor.getString(cursor.getColumnIndex(field.getName())));
							}	
						}						
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return objectInstance;
	}
	
}
