package br.com.zapimo.util;

import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

public class PercorreViews {

	public boolean faltaPreencherEditText(LinearLayout llTela) {

		boolean temVazio = false;

		for (int i = 0; i < llTela.getChildCount(); i++) {

			Object child = (Object) llTela.getChildAt(i);

			if (child instanceof LinearLayout) {

				LinearLayout linearLayoutChild = (LinearLayout) child;

				for (int x = 0; x < linearLayoutChild.getChildCount(); x++) {

					View view = (View) linearLayoutChild.getChildAt(x);
					
					if (view instanceof LinearLayout) {

						LinearLayout linearLayoutChild2 = (LinearLayout) view;

						for (int y = 0; y < linearLayoutChild2.getChildCount(); y++) {

							View view2 = (View) linearLayoutChild2.getChildAt(y);

							if (view2 instanceof EditText) {

								EditText editTextChild = (EditText) view2;

								if(editTextChild.getText().toString().isEmpty() ){
								
									temVazio = true;
									break;
								}	
							}
						}
					}					
				}
			}		
		}
		return temVazio;
	}
	
	public void limpaTodosEditText(LinearLayout llTela) {

		for (int i = 0; i < llTela.getChildCount(); i++) {

			Object child = (Object) llTela.getChildAt(i);

			if (child instanceof LinearLayout) {

				LinearLayout linearLayoutChild = (LinearLayout) child;

				for (int x = 0; x < linearLayoutChild.getChildCount(); x++) {

					View view = (View) linearLayoutChild.getChildAt(x);
					
					if (view instanceof LinearLayout) {

						LinearLayout linearLayoutChild2 = (LinearLayout) view;

						for (int y = 0; y < linearLayoutChild2.getChildCount(); y++) {

							View view2 = (View) linearLayoutChild2.getChildAt(y);

							if (view2 instanceof EditText) {

								EditText editTextChild = (EditText) view2;

								editTextChild.setText("");	
							}
						}
					}					
				}
			}		
		}	
	}

}
