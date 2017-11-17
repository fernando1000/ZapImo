package br.com.zapimo.view;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;
import br.com.zapimo.R;
import br.com.zapimo.model.Imoveis;


public class ImoveisAdapter extends ArrayAdapter<String> {

	private Context context;
	private List<Imoveis> listaDeImoveis;
	private List<Imoveis> listaDeImoveisTemporaria;
	private Filter ImoveisFilter;
	private Imoveis imovel;
	private LayoutInflater layoutInflater;

	public ImoveisAdapter(Context activitySpinner, int textViewResourceId, List _listImoveis) {
		super(activitySpinner, textViewResourceId, _listImoveis);

		context = activitySpinner;
		listaDeImoveis = _listImoveis;
		listaDeImoveisTemporaria = _listImoveis;
		layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public View getCustomView(int position, View convertView, ViewGroup parent) {

		View viewRow = layoutInflater.inflate(R.layout.item_lista_generica, parent, false);
		
		imovel = null;
		imovel = listaDeImoveis.get(position);
		
		TextView textView_1 = (TextView) viewRow.findViewById(R.id.tv_chave);
		 		 textView_1.setText(""+imovel.getCodImovel());

		TextView textView_2 = (TextView) viewRow.findViewById(R.id.tv_valor);
		 		 textView_2.setText( imovel.getTipoImovel());
		
		ImageView imageView = (ImageView) viewRow.findViewById(R.id.iv_chave);
				  imageView.setImageResource(R.drawable.icon_casa);
		
		viewRow.setId(imovel.getCodImovel());

		return viewRow;
	}
	
	public int getCount() {
		return listaDeImoveis.size();
	}
	
	public long getItemId(int position) {
		return listaDeImoveis.get(position).hashCode();
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		return getCustomView(position, convertView, parent);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return getCustomView(position, convertView, parent);
	}

	public void resetData() {
		listaDeImoveis = listaDeImoveisTemporaria;
	}

	@Override
	public Filter getFilter() {
		if (ImoveisFilter == null)
			ImoveisFilter = new ImoveisFilter();

		return ImoveisFilter;
	}

	private class ImoveisFilter extends Filter {
		@Override
		protected FilterResults performFiltering(CharSequence charSequence_constraint) {
			FilterResults filterResults = new FilterResults();
			
			if (charSequence_constraint == null || charSequence_constraint.length() == 0) {
				
				filterResults.values = listaDeImoveisTemporaria;
				filterResults.count = listaDeImoveisTemporaria.size();
			} 
			else {	
				List<Imoveis> listaDeImoveis_Local = new ArrayList<Imoveis>();

				for (Imoveis Imoveis : listaDeImoveis) {
					
					if (containsIgnoreCase(removerAcentos(""+Imoveis.getCodImovel()), charSequence_constraint.toString())) {

						listaDeImoveis_Local.add(Imoveis);
					}					
				}

				filterResults.values = listaDeImoveis_Local;
				filterResults.count = listaDeImoveis_Local.size();
			}
			return filterResults;
		}
		
		public CharSequence removerAcentos(CharSequence str) {
		    return Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
		}

		@Override
		protected void publishResults(CharSequence constraint, FilterResults results) {
			
				listaDeImoveis = (List<Imoveis>) results.values;
				
				notifyDataSetChanged();
		}
		
		public boolean containsIgnoreCase(CharSequence haystack, String needle) {
			if (needle.equals(""))
				return true;
			if (haystack == null || needle == null || haystack.equals(""))
				return false;

			Pattern p = Pattern.compile(needle, Pattern.CASE_INSENSITIVE
					+ Pattern.LITERAL);
			Matcher m = p.matcher(haystack);
			return m.find();
		}

	}
}