package br.com.zapimo.model;

public class Contato {
		
	private int codImovel;
	private String nome;
	private String email;
	private String telefone;
	private String mensagem; 
	
	public static final String COLUMN_INTEGER_COD_IMOVEL = "codImovel";
	public static final String COLUMN_TEXT_NOME = "nome";
	public static final String COLUMN_TEXT_EMAIL = "email";
	public static final String COLUMN_TEXT_TELEFONE = "telefone";
	public static final String COLUMN_TEXT_MENSAGEM = "mensagem";
	
	
	public int getCodImovel() {
		return codImovel;
	}
	public void setCodImovel(int codImovel) {
		this.codImovel = codImovel;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public String getMensagem() {
		return mensagem;
	}
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	

}
