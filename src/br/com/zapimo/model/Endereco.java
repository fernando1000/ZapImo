package br.com.zapimo.model;

public class Endereco {
	
	private String Logradouro;
	public static final String COLUMN_TEXT_LOGRADOURO = "Logradouro";

	public void setLogradouro(String Logradouro) {
		this.Logradouro = Logradouro;
	}

	public String getLogradouro() {
		return Logradouro;
	}

	private String Numero;
	public static final String COLUMN_TEXT_NUMERO = "Numero";

	public void setNumero(String Numero) {
		this.Numero = Numero;
	}

	public String getNumero() {
		return Numero;
	}

	private String Zona;
	public static final String COLUMN_TEXT_ZONA = "Zona";

	public void setZona(String Zona) {
		this.Zona = Zona;
	}

	public String getZona() {
		return Zona;
	}

	private String Complemento;
	public static final String COLUMN_TEXT_COMPLEMENTO = "Complemento";

	public void setComplemento(String Complemento) {
		this.Complemento = Complemento;
	}

	public String getComplemento() {
		return Complemento;
	}

	private String Bairro;
	public static final String COLUMN_TEXT_BAIRRO = "Bairro";

	public void setBairro(String Bairro) {
		this.Bairro = Bairro;
	}

	public String getBairro() {
		return Bairro;
	}

	private int CEP;
	public static final String COLUMN_INTEGER_CEP = "CEP";

	public void setCEP(int CEP) {
		this.CEP = CEP;
	}

	public int getCEP() {
		return CEP;
	}

	private String Cidade;
	public static final String COLUMN_TEXT_CIDADE = "Cidade";

	public void setCidade(String Cidade) {
		this.Cidade = Cidade;
	}

	public String getCidade() {
		return Cidade;
	}

	private String Estado;
	public static final String COLUMN_TEXT_ESTADO = "Estado";

	public void setEstado(String Estado) {
		this.Estado = Estado;
	}

	public String getEstado() {
		return Estado;
	}
}
