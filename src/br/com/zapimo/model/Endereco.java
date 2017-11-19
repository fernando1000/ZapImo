package br.com.zapimo.model;

public class Endereco {
	
	private int CEP;
	private String Logradouro;
	private String Numero;
	private String Complemento;
	private String Bairro;
	private String Cidade;
	private String Estado;
	private String Zona;

	public static final String COLUMN_TEXT_LOGRADOURO = "Logradouro";
	public static final String COLUMN_TEXT_ESTADO = "Estado";
	public static final String COLUMN_INTEGER_CEP = "CEP";
	public static final String COLUMN_TEXT_CIDADE = "Cidade";
	public static final String COLUMN_TEXT_ZONA = "Zona";
	public static final String COLUMN_TEXT_COMPLEMENTO = "Complemento";
	public static final String COLUMN_TEXT_BAIRRO = "Bairro";
	public static final String COLUMN_TEXT_NUMERO = "Numero";

	public void setLogradouro(String Logradouro) {
		this.Logradouro = Logradouro;
	}

	public String getLogradouro() {
		return Logradouro;
	}


	public void setNumero(String Numero) {
		this.Numero = Numero;
	}

	public String getNumero() {
		return Numero;
	}


	public void setZona(String Zona) {
		this.Zona = Zona;
	}

	public String getZona() {
		return Zona;
	}


	public void setComplemento(String Complemento) {
		this.Complemento = Complemento;
	}

	public String getComplemento() {
		return Complemento;
	}


	public void setBairro(String Bairro) {
		this.Bairro = Bairro;
	}

	public String getBairro() {
		return Bairro;
	}


	public void setCEP(int CEP) {
		this.CEP = CEP;
	}

	public int getCEP() {
		return CEP;
	}


	public void setCidade(String Cidade) {
		this.Cidade = Cidade;
	}

	public String getCidade() {
		return Cidade;
	}


	public void setEstado(String Estado) {
		this.Estado = Estado;
	}

	public String getEstado() {
		return Estado;
	}
}
