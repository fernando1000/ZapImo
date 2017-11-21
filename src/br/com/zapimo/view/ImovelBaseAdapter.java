package br.com.zapimo.view;

import java.util.List;
import com.android.volley.toolbox.ImageLoader;
import br.com.zapimo.R;
import br.com.zapimo.model.Imoveis;
import br.com.zapimo.util.TelaBuilder;
import br.com.zapimo.util.Moeda;
import android.R.color;
import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

@SuppressLint("NewApi")
public class ImovelBaseAdapter extends BaseAdapter {
	
	private Context context;
	private List<Imoveis> listaDeImoveis;
	private ImageLoader imageLoader;	
	private TelaBuilder meusWidgetsBuilder;
	private Moeda reais;
	
	public ImovelBaseAdapter(Context _context, List<Imoveis> list, ImageLoader il){
		
		this.context = _context;
		this.listaDeImoveis = list;
		this.imageLoader = il;
		meusWidgetsBuilder = new TelaBuilder(context);
	    reais = new Moeda();
	}
	
	@Override
	public int getCount() {
		return listaDeImoveis.size();
	}

	@Override
	public Object getItem(int position) {
		return listaDeImoveis.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		
		Imoveis imovel = listaDeImoveis.get(position);
		
		LinearLayout llTela = new LinearLayout(context);
		llTela.setOrientation(LinearLayout.VERTICAL);		
		llTela.setBackgroundColor(context.getResources().getColor(R.color.silver));
	
		LinearLayout llTelaInterna = new LinearLayout(context);
		LinearLayout.LayoutParams lllp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);			
									lllp.setMargins(30, 30, 30, 30);
		llTelaInterna.setLayoutParams(lllp);
		llTelaInterna.setOrientation(LinearLayout.VERTICAL);		
		llTelaInterna.setBackgroundColor(context.getResources().getColor(R.color.azulArdosiaEscuro));

		ImageView imageView = new ImageView(context);
		imageView.setAdjustViewBounds(true);
		imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.casa));
			
		llTelaInterna.addView(imageView);
		llTelaInterna.addView(meusWidgetsBuilder.criaTextViewItemLista(imovel.getSubtipoImovel()));
		llTelaInterna.addView(meusWidgetsBuilder.criaTextViewItemLista(reais.desenhaReaisComPontoEvirgula(""+imovel.getPrecoVenda(), 2, 1)));
		llTelaInterna.addView(meusWidgetsBuilder.criaTextViewItemLista(""+imovel.getDormitorios()+" dorms, "+imovel.getVagas()+" vagas, "+imovel.getAreaUtil()+" m²"));
		llTela.addView(llTelaInterna);
		view = llTela;
			
		imageLoader.get(imovel.getUrlImagem(), imageLoader.getImageListener(imageView, R.drawable.casa, R.drawable.erro));
					
		view.setId(imovel.getCodImovel());
		
		return view;
	}
		
}
