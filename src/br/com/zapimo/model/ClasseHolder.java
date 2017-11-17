package br.com.zapimo.model;

import java.util.List;

public class ClasseHolder {
	
	private List<Imoveis> Imoveis;
	public static final String COLUMN_TEXT_IMOVEIS = "Imoveis";

	public void setImoveis(List<Imoveis> Imoveis) {
		this.Imoveis = Imoveis;
	}

	public List<Imoveis> getImoveis() {
		return Imoveis;
	}
}
