package br.com.zapimo.util;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

@SuppressLint("NewApi")
public class MeuAlerta {

	private String titulo;
	private String conteudo;
	private Context context;
	
	public MeuAlerta(String _titulo, String _conteudo, Context _context) {
		this.titulo = _titulo;
		this.conteudo = _conteudo;
		this.context = _context;
	}
	
	public void meuAlertaOk() {
		
		AlertDialog.Builder builder = new AlertDialog.Builder(context, 1);
		builder.setTitle(titulo)
			   .setMessage(conteudo)
		   	   .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
		   		   public void onClick(DialogInterface dialog, int id) {
		   		   }
		   	   });
		builder.show();
	}
		
}
