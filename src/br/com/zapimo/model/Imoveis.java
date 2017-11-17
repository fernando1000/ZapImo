package br.com.zapimo.model;

import java.io.Serializable;

public class Imoveis implements Serializable{
	
	private String DataAtualizacao;
	public static final String COLUMN_TEXT_DATAATUALIZACAO = "DataAtualizacao";

	public void setDataAtualizacao(String DataAtualizacao) {
		this.DataAtualizacao = DataAtualizacao;
	}

	public String getDataAtualizacao() {
		return DataAtualizacao;
	}

	private String TipoImovel;
	public static final String COLUMN_TEXT_TIPOIMOVEL = "TipoImovel";

	public void setTipoImovel(String TipoImovel) {
		this.TipoImovel = TipoImovel;
	}

	public String getTipoImovel() {
		return TipoImovel;
	}

	private int AreaTotal;
	public static final String COLUMN_INTEGER_AREATOTAL = "AreaTotal";

	public void setAreaTotal(int AreaTotal) {
		this.AreaTotal = AreaTotal;
	}

	public int getAreaTotal() {
		return AreaTotal;
	}

	private int AreaUtil;
	public static final String COLUMN_INTEGER_AREAUTIL = "AreaUtil";

	public void setAreaUtil(int AreaUtil) {
		this.AreaUtil = AreaUtil;
	}

	public int getAreaUtil() {
		return AreaUtil;
	}

	private int Vagas;
	public static final String COLUMN_INTEGER_VAGAS = "Vagas";

	public void setVagas(int Vagas) {
		this.Vagas = Vagas;
	}

	public int getVagas() {
		return Vagas;
	}

	private String SubtipoImovel;
	public static final String COLUMN_TEXT_SUBTIPOIMOVEL = "SubtipoImovel";

	public void setSubtipoImovel(String SubtipoImovel) {
		this.SubtipoImovel = SubtipoImovel;
	}

	public String getSubtipoImovel() {
		return SubtipoImovel;
	}

	private int CodImovel;
	public static final String COLUMN_INTEGER_CODIMOVEL = "CodImovel";

	public void setCodImovel(int CodImovel) {
		this.CodImovel = CodImovel;
	}

	public int getCodImovel() {
		return CodImovel;
	}

	private int PrecoVenda;
	public static final String COLUMN_INTEGER_PRECOVENDA = "PrecoVenda";

	public void setPrecoVenda(int PrecoVenda) {
		this.PrecoVenda = PrecoVenda;
	}

	public int getPrecoVenda() {
		return PrecoVenda;
	}

	private Endereco Endereco;
	public static final String COLUMN_TEXT_ENDERECO = "Endereco";

	public void setEndereco(Endereco Endereco) {
		this.Endereco = Endereco;
	}

	public Endereco getEndereco() {
		return Endereco;
	}

	private double DistanciaKilometros;
	public static final String COLUMN_TEXT_DISTANCIAKILOMETROS = "DistanciaKilometros";

	public void setDistanciaKilometros(double DistanciaKilometros) {
		this.DistanciaKilometros = DistanciaKilometros;
	}

	public double getDistanciaKilometros() {
		return DistanciaKilometros;
	}

	private String SubTipoOferta;
	public static final String COLUMN_TEXT_SUBTIPOOFERTA = "SubTipoOferta";

	public void setSubTipoOferta(String SubTipoOferta) {
		this.SubTipoOferta = SubTipoOferta;
	}

	public String getSubTipoOferta() {
		return SubTipoOferta;
	}

	private String EstagioObra;
	public static final String COLUMN_TEXT_ESTAGIOOBRA = "EstagioObra";

	public void setEstagioObra(String EstagioObra) {
		this.EstagioObra = EstagioObra;
	}

	public String getEstagioObra() {
		return EstagioObra;
	}

	private Boolean StatusQualidadeTotal;
	public static final String COLUMN_TEXT_STATUSQUALIDADETOTAL = "StatusQualidadeTotal";

	public void setStatusQualidadeTotal(Boolean StatusQualidadeTotal) {
		this.StatusQualidadeTotal = StatusQualidadeTotal;
	}

	public Boolean getStatusQualidadeTotal() {
		return StatusQualidadeTotal;
	}

	private int Dormitorios;
	public static final String COLUMN_INTEGER_DORMITORIOS = "Dormitorios";

	public void setDormitorios(int Dormitorios) {
		this.Dormitorios = Dormitorios;
	}

	public int getDormitorios() {
		return Dormitorios;
	}

	private Cliente Cliente;
	public static final String COLUMN_TEXT_CLIENTE = "Cliente";

	public void setCliente(Cliente Cliente) {
		this.Cliente = Cliente;
	}

	public Cliente getCliente() {
		return Cliente;
	}

	private String UrlImagem;
	public static final String COLUMN_TEXT_URLIMAGEM = "UrlImagem";

	public void setUrlImagem(String UrlImagem) {
		this.UrlImagem = UrlImagem;
	}

	public String getUrlImagem() {
		return UrlImagem;
	}

	private int Suites;
	public static final String COLUMN_INTEGER_SUITES = "Suites";

	public void setSuites(int Suites) {
		this.Suites = Suites;
	}

	public int getSuites() {
		return Suites;
	}
}
