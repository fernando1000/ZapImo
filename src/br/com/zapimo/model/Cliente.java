package br.com.zapimo.model;

public class Cliente {
	
	private int CodCliente;
	private String NomeFantasia;

	public static final String COLUMN_INTEGER_CODCLIENTE = "CodCliente";
	public static final String COLUMN_TEXT_NOMEFANTASIA = "NomeFantasia";

	public void setNomeFantasia(String NomeFantasia) {
		this.NomeFantasia = NomeFantasia;
	}

	public String getNomeFantasia() {
		return NomeFantasia;
	}


	public void setCodCliente(int CodCliente) {
		this.CodCliente = CodCliente;
	}

	public int getCodCliente() {
		return CodCliente;
	}
}
