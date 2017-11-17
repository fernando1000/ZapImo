package br.com.zapimo.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import br.com.zapimo.R;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ScrollView;

@SuppressLint("NewApi")
public class MeusWidgetsBuilder {

	private Context context;
	
	public MeusWidgetsBuilder(Context _context){
		this.context = _context;
	}
	
	public TextView criaTextViewTITULO(String texto){
		
		LayoutParams lllp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);			

		TextView textView = new TextView(context);
		textView.setTextSize(24);
		textView.setTextColor(context.getResources().getColor(R.color.azul)); 
		textView.setTypeface(textView.getTypeface(), Typeface.BOLD);
		textView.setText(texto);	

								 lllp.setMargins(0, 20, 0, 0);		
		textView.setLayoutParams(lllp);

		return textView;
	}

	public TextView criaTextViewCONTEUDO(String texto){
		
		LayoutParams lllp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);			

		TextView textView = new TextView(context);
		textView.setTextSize(20);
		textView.setTextColor(context.getResources().getColor(R.color.preto)); 
		textView.setTypeface(textView.getTypeface(), Typeface.ITALIC);
		textView.setText(texto);	
		
								 lllp.setMargins(0, 20, 0, 0);		
		textView.setLayoutParams(lllp);

		return textView;
	}

	public Button criaBotao(String texto, LayoutParams lllp){

		Button button = new Button(context);
		button.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.style_btn));
		button.setText(texto);
		button.setTextColor(context.getResources().getColor(R.color.branco));
		button.setTextSize(30);
		button.setLayoutParams(lllp);

		return button;
	}

	public EditText criaEditText(String texto){
	
	    EditText editText = new EditText(context);
	    editText.setTextSize(24);
	    editText.setBackground(context.getResources().getDrawable(R.drawable.style_edit));    		  
	    editText.setText(texto);
	    
	    return editText;
	}

	public LinearLayout criaLinearLayoutTELA(){
	
		LinearLayout linearLayout = new LinearLayout(context);
		linearLayout.setOrientation(LinearLayout.VERTICAL);
		linearLayout.setBackgroundColor(context.getResources().getColor(R.color.azul_claro));
		linearLayout.setPadding(20, 0, 20, 0);
	
		return linearLayout;
	}
	
	public LinearLayout criaLinearLayoutLINHA(String titulo, TextView tv_conteudo){
		
		LinearLayout linearLayout = new LinearLayout(context);
		linearLayout.setOrientation(LinearLayout.HORIZONTAL);
		linearLayout.addView(criaTextViewTITULO(titulo));
		linearLayout.addView(tv_conteudo);
		
		return linearLayout;
	}

	public LinearLayout criaLinearLayoutLINHAet(String titulo, EditText et_conteudo){
		
		LinearLayout linearLayout = new LinearLayout(context);
		linearLayout.setOrientation(LinearLayout.HORIZONTAL);
		linearLayout.addView(criaTextViewTITULO(titulo));
		linearLayout.addView(et_conteudo);
		
		return linearLayout;
	}

	public ScrollView criaScrollView(){
		
		ScrollView scrollView = new ScrollView(context);
	
		return scrollView;
	}

	
	
	
}
