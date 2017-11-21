package br.com.zapimo.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import br.com.zapimo.R;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ScrollView;

@SuppressLint("NewApi")
public class TelaBuilder {

	private Context context;
	
	public TelaBuilder(Context _context){
		this.context = _context;
	}
	
	public LinearLayout criaLinearLayoutcomBordaArredondada(){
		
		LinearLayout ll_holder = new LinearLayout(context);
		ll_holder.setOrientation(LinearLayout.VERTICAL);
		ll_holder.setBackground(criaGradientDrawable());
		ll_holder.setPadding(10, 10, 10, 10);
		
		LayoutParams lp_MATCH_WRAP = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);			
							lp_MATCH_WRAP.setMargins(0, 20, 0, 20);		
		ll_holder.setLayoutParams(lp_MATCH_WRAP);
		
		return ll_holder;
	}
	private GradientDrawable criaGradientDrawable(){
		
		GradientDrawable drawable = new GradientDrawable();
		drawable.setShape(GradientDrawable.RECTANGLE);
		drawable.setStroke(3, Color.BLACK);
		drawable.setCornerRadius(8);
		drawable.setColor(Color.WHITE);
		
		return drawable;
	}

	public TextView criaTextViewTITULO(String texto){
		
		TextView textView = new TextView(context);
		textView.setTextSize(16);
		textView.setTextColor(context.getResources().getColor(R.color.azul)); 
		textView.setTypeface(textView.getTypeface(), Typeface.BOLD);
		textView.setText(texto);	

		return textView;
	}

	public TextView criaTextViewCONTEUDO(String texto){

		TextView textView = new TextView(context);
		textView.setTextSize(16);
		textView.setTextColor(context.getResources().getColor(R.color.preto)); 
		textView.setTypeface(textView.getTypeface(), Typeface.ITALIC);
		textView.setText(texto);	

		return textView;
	}

	public TextView criaTextViewItemLista(String texto){
		
		TextView tvTexto = new TextView(context);
		tvTexto.setText(texto);
		tvTexto.setTextColor(Color.WHITE);
		tvTexto.setTextSize(10);
		
		return tvTexto;
	}

	public Button criaBotao(String texto, LayoutParams lllp){

		Button button = new Button(context);
		button.setBackground(context.getResources().getDrawable(R.drawable.style_btn));
		button.setText(texto);
		button.setTextColor(context.getResources().getColor(R.color.branco));
		button.setTextSize(30);
		button.setLayoutParams(lllp);

		return button;
	}

	public EditText criaEditText(String texto){
	
	    EditText editText = new EditText(context);
	    editText.setTextSize(12);
	    editText.setBackground(context.getResources().getDrawable(R.drawable.style_edit));    		  
	    editText.setText(texto);
	    
		LayoutParams lp_MATCH_WRAP = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);			
						//lp_MATCH_WRAP.setMargins(0, 20, 0, 20);		
		editText.setLayoutParams(lp_MATCH_WRAP);

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
