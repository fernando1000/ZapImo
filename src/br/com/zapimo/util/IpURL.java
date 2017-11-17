package br.com.zapimo.util;

public enum IpURL {
	
	URL_SERVER_REST("http://demo4573903.mockable.io"),
	
	NOME_PACOTE_MODELL("br.com.zapimo.model.");
	
	private String valor;
	
	private IpURL(String valor){
		this.valor = valor;
	}
	
	public String getValor(){
		return valor;
	}
}
