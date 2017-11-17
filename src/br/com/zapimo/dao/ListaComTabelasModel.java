package br.com.zapimo.dao;

import java.util.ArrayList;
import java.util.List;

import br.com.zapimo.model.Cliente;
import br.com.zapimo.model.Contato;
import br.com.zapimo.model.Endereco;
import br.com.zapimo.model.Imoveis;

public class ListaComTabelasModel{
	
	public static List<String> devolveListaComTabelasModel(){
		
		List<String> lista = new ArrayList<String>(); 
		 			 lista.add(Cliente.class.getSimpleName());
		 			 lista.add(Contato.class.getSimpleName());
		 			 lista.add(Endereco.class.getSimpleName());
		 			 lista.add(Imoveis.class.getSimpleName());

		 return lista;
	}	

}
