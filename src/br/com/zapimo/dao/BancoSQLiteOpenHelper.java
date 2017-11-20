package br.com.zapimo.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import br.com.zapimo.util.IpURL;
import br.com.zapimo.util.MeuAlerta;

public class BancoSQLiteOpenHelper extends SQLiteOpenHelper {

	public static final String BANCO_NOME = "ZapImo";
	public static final int BANCO_VERSAO = 7;
	private Context context;
	
	public BancoSQLiteOpenHelper(Context _context) {
		super(_context, BANCO_NOME, null, BANCO_VERSAO);
		this.context = _context;
	}

	@Override
	public void onCreate(SQLiteDatabase sQLiteDatabase) {
		
		for (String tabela : ListaComTabelasModel.devolveListaComTabelasModel()) {

			try {
				Class<?> classe = Class.forName(IpURL.NOME_PACOTE_MODELL.getValor()  + tabela);
			
				sQLiteDatabase.execSQL(Query.criaCreateTableComKeyy(classe));
			} 
			catch (Exception erro) {
				new MeuAlerta("Erro onCreate", erro.toString(), context).meuAlertaOk();
			}
		}
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase sQLiteDatabase, int oldVersion, int newVersion) {

		for (String tabela : ListaComTabelasModel.devolveListaComTabelasModel()) {

			try {
				Class<?> classe = Class.forName(IpURL.NOME_PACOTE_MODELL.getValor() + tabela);
				
				sQLiteDatabase.execSQL(Query.criaDropTable_final(classe));				
			} 
			catch (Exception erro) {	
				new MeuAlerta("Erro onUpgrade", erro.toString(), context).meuAlertaOk();
			} 
		}
		
		onCreate(sQLiteDatabase);
	}
}