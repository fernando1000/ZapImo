package br.com.zapimo.util;

import android.app.ProgressDialog;
import android.content.Context;

public class MeuProgressDialog {
	
	public static ProgressDialog criaProgressDialog(Context context, String mensagem) {
		
		ProgressDialog progressDialog = new ProgressDialog(context);
					   progressDialog.setCanceledOnTouchOutside(false);
					   progressDialog.setMessage(mensagem);
					   progressDialog.show();
		
		return progressDialog;
	}

	public static void encerraProgressDialog(ProgressDialog progressDialog) {
		
		if (progressDialog != null) {
			progressDialog.dismiss();
			progressDialog = null;
		}
	}

}
