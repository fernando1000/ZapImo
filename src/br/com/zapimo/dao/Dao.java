package br.com.zapimo.dao;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Dao extends BancoSQLiteOpenHelper {

	public Dao(Context context) {
		super(context);
	}
	
	public void insereOUatualiza(Object objeto, Object... parametros) {

		Class<?> classe = objeto.getClass();

		String select = "select * from " + classe.getSimpleName();

		String condicaoWhere = Query.criaCondicaoWhereComParametrosString(parametros);
		
		String querySelect = select + condicaoWhere;
	
		insereOUatualizaObjeto_final(objeto, querySelect, condicaoWhere);
	}

	private void insereOUatualizaObjeto_final(Object objeto, String querySelect, String condicaoWhere) {

		SQLiteDatabase sQLiteDatabase = this.getWritableDatabase();

		Cursor cursor = sQLiteDatabase.rawQuery(querySelect, null);
	
		if (cursor.moveToFirst()) {

			atualizaObjeto_final(objeto, condicaoWhere);
		} else {
			insereObjeto_final(objeto);
		}
		cursor.close();
		sQLiteDatabase.close();
	}

	private void atualizaObjeto_final(Object objeto, String condicaoWhere) {

		SQLiteDatabase sQLiteDatabase = this.getWritableDatabase();
		sQLiteDatabase.execSQL(Query.criaUpdate_final(objeto, condicaoWhere));
		sQLiteDatabase.close();
	}

	public void insereObjeto_final(Object objeto) {

		SQLiteDatabase sQLiteDatabase = this.getWritableDatabase();
		sQLiteDatabase.execSQL(Query.criaInsert_final(objeto));
		sQLiteDatabase.close();
	}
		
	public <T> List<T> listaTodaTabela(Class<T> classe, Object... parametros) {

		String select = "select * from " + classe.getSimpleName();

		String condicaoWhere = Query.criaCondicaoWhereComParametrosString(parametros);
		
		String querySelect = select + condicaoWhere;

		return devolveListaBaseadoEmSQL_final(classe, querySelect);
	}

	public <T> Object devolveObjeto(Class<T> classe, Object... parametros) {

		String select = "select * from " + classe.getSimpleName();

		String condicaoWhere = Query.criaCondicaoWhereComParametrosString(parametros);

		String querySelect = select + condicaoWhere;

		return devolveObjetoBaseadoEmSQL_final(classe, querySelect);
	}

	public void deletaTodosDados() {

		SQLiteDatabase sQLiteDatabase = this.getWritableDatabase();

		for (String tabela : ListaComTabelasModel.devolveListaComTabelasModel()) {

			sQLiteDatabase.execSQL("delete from " + tabela);
		}
		sQLiteDatabase.close();
	}

	public void deletaObjeto(Class<?> classe, Object... parametros) {

		String delete = "delete from " + classe.getSimpleName();
		
		String condicaoWhere = Query.criaCondicaoWhereComParametrosString(parametros);
		
		String queryDelete = delete + condicaoWhere;
		
		SQLiteDatabase sQLiteDatabase = this.getWritableDatabase();
		sQLiteDatabase.execSQL(queryDelete);
		sQLiteDatabase.close();
	}

	private Object devolveObjetoBaseadoEmSQL_final(Class<?> classe, String querySelect) {

		SQLiteDatabase sQLiteDatabase = this.getReadableDatabase();

		Cursor cursor = sQLiteDatabase.rawQuery(querySelect, null);

		Object objeto = null;

		if (cursor.moveToNext()) {

			objeto = PreencheObjetoUsandoCursor.devolveObjetoPreenchido(classe, cursor);
		}
		cursor.close();
		sQLiteDatabase.close();

		return objeto;
	}

	private <T> List<T> devolveListaBaseadoEmSQL_final(Class<T> classe, String querySelect) {

		List<T> lista = new ArrayList<T>();

		try {
			SQLiteDatabase sQLiteDatabase = this.getReadableDatabase();

			Cursor cursor = sQLiteDatabase.rawQuery(querySelect, null);

			while (cursor.moveToNext()) {

				lista.add((T) PreencheObjetoUsandoCursor.devolveObjetoPreenchido(classe, cursor));
			}
			cursor.close();
			sQLiteDatabase.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (List<T>) lista;
	}
	
	public int selectCount(Class<?> classe, Object... parametros){

		String select = "SELECT count(*) FROM " + classe.getSimpleName();

		String condicaoWhere = Query.criaCondicaoWhereComParametrosString(parametros);

		String querySelect = select + condicaoWhere;

		return devolveQtdEncontrada(querySelect);
	}

	public int selectSum(Class<?> classe, String colunaAserSomada, Object... parametros){

		String select = "SELECT sum("+colunaAserSomada+") FROM " + classe.getSimpleName(); 
		
		String condicaoWhere = Query.criaCondicaoWhereComParametrosString(parametros);

		String querySelect = select + condicaoWhere;

		return devolveQtdEncontrada(querySelect);
	}
		
	private int devolveQtdEncontrada(String sql){

		int qtd = 0;

		SQLiteDatabase sQLiteDatabase = this.getReadableDatabase();

		Cursor cursor = sQLiteDatabase.rawQuery(sql, null);

		if (cursor.moveToFirst()) {

			qtd = cursor.getInt(0);
		}
		cursor.close();
		sQLiteDatabase.close();

		return qtd;
	}


}
