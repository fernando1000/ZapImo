package br.com.zapimo.view;

import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import br.com.zapimo.dao.Dao;
import br.com.zapimo.model.Imoveis;

public class ListaImoveisActivity extends Activity {

	private Context context;
	private Dao dao;
	private List<Imoveis> listaDeImoveis;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		context = ListaImoveisActivity.this;

		LinearLayout llTela = new LinearLayout(context);
		llTela.setOrientation(LinearLayout.VERTICAL);

		dao = new Dao(context);

		listaDeImoveis = dao.listaTodaTabela(Imoveis.class);

		ListView listView = new ListView(context);
		listView.setAdapter(new ImoveisAdapter(context, 0, listaDeImoveis));
		listView.setDividerHeight(7);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				acaoAposClick(view.getId());
			}
		});

		llTela.addView(listView);

		setContentView(llTela);
	}

	private void acaoAposClick(int viewId) {

		Imoveis imovel = null;

		for (Imoveis imovel2 : listaDeImoveis) {

			if (imovel2.getCodImovel() == viewId) {

				imovel = imovel2;
				break;
			}
		}

		
		Intent intent = new Intent(context, DetalheDoImovel.class);

						 Bundle data = new Bundle();
								data.putSerializable("Imoveis", imovel);
			   intent.putExtras(data);
		startActivity(intent);
	}

}
