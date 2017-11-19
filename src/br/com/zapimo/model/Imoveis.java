package br.com.zapimo.model;

import java.io.Serializable;
import java.util.List;

public class Imoveis implements Serializable {

	private int CodImovel;
	private int Suites;
	private int Dormitorios;
	private int PrecoVenda;
	private int AreaTotal;
	private int Vagas;
	private int AreaUtil;
	private double DistanciaKilometros;
	private String DataAtualizacao;
	private String UrlImagem;
	private String EstagioObra;
	private String SubTipoOferta;
	private String TipoImovel;
	private String SubtipoImovel;
	private Boolean StatusQualidadeTotal;
	private Cliente Cliente;
	private Endereco Endereco;

	private int PrecoCondominio;
	private String InformacoesComplementares;
	private String Observacao;
	private List<String> Fotos;

	public static final String COLUMN_TEXT_INFORMACOESCOMPLEMENTARES = "InformacoesComplementares";
	public static final String COLUMN_INTEGER_PRECOCONDOMINIO = "PrecoCondominio";
	public static final String COLUMN_TEXT_OBSERVACAO = "Observacao";
	public static final String COLUMN_INTEGER_PRECOVENDA = "PrecoVenda";
	public static final String COLUMN_TEXT_DISTANCIAKILOMETROS = "DistanciaKilometros";
	public static final String COLUMN_TEXT_ESTAGIOOBRA = "EstagioObra";
	public static final String COLUMN_TEXT_SUBTIPOOFERTA = "SubTipoOferta";
	public static final String COLUMN_TEXT_DATAATUALIZACAO = "DataAtualizacao";
	public static final String COLUMN_TEXT_TIPOIMOVEL = "TipoImovel";
	public static final String COLUMN_INTEGER_AREATOTAL = "AreaTotal";
	public static final String COLUMN_TEXT_URLIMAGEM = "UrlImagem";
	public static final String COLUMN_INTEGER_SUITES = "Suites";
	public static final String COLUMN_TEXT_STATUSQUALIDADETOTAL = "StatusQualidadeTotal";
	public static final String COLUMN_INTEGER_DORMITORIOS = "Dormitorios";
	public static final String COLUMN_INTEGER_VAGAS = "Vagas";
	public static final String COLUMN_INTEGER_CODIMOVEL = "CodImovel";
	public static final String COLUMN_TEXT_SUBTIPOIMOVEL = "SubtipoImovel";
	public static final String COLUMN_INTEGER_AREAUTIL = "AreaUtil";

	public int getPrecoCondominio() {
		return PrecoCondominio;
	}

	public void setPrecoCondominio(int precoCondominio) {
		PrecoCondominio = precoCondominio;
	}

	public String getInformacoesComplementares() {
		return InformacoesComplementares;
	}

	public void setInformacoesComplementares(String informacoesComplementares) {
		InformacoesComplementares = informacoesComplementares;
	}

	public String getObservacao() {
		return Observacao;
	}

	public void setObservacao(String observacao) {
		Observacao = observacao;
	}

	public List<String> getFotos() {
		return Fotos;
	}

	public void setFotos(List<String> fotos) {
		Fotos = fotos;
	}

	public void setDataAtualizacao(String DataAtualizacao) {
		this.DataAtualizacao = DataAtualizacao;
	}

	public String getDataAtualizacao() {
		return DataAtualizacao;
	}

	public void setTipoImovel(String TipoImovel) {
		this.TipoImovel = TipoImovel;
	}

	public String getTipoImovel() {
		return TipoImovel;
	}

	public void setAreaTotal(int AreaTotal) {
		this.AreaTotal = AreaTotal;
	}

	public int getAreaTotal() {
		return AreaTotal;
	}

	public void setAreaUtil(int AreaUtil) {
		this.AreaUtil = AreaUtil;
	}

	public int getAreaUtil() {
		return AreaUtil;
	}

	public void setVagas(int Vagas) {
		this.Vagas = Vagas;
	}

	public int getVagas() {
		return Vagas;
	}

	public void setSubtipoImovel(String SubtipoImovel) {
		this.SubtipoImovel = SubtipoImovel;
	}

	public String getSubtipoImovel() {
		return SubtipoImovel;
	}

	public void setCodImovel(int CodImovel) {
		this.CodImovel = CodImovel;
	}

	public int getCodImovel() {
		return CodImovel;
	}

	public void setPrecoVenda(int PrecoVenda) {
		this.PrecoVenda = PrecoVenda;
	}

	public int getPrecoVenda() {
		return PrecoVenda;
	}

	public void setEndereco(Endereco Endereco) {
		this.Endereco = Endereco;
	}

	public Endereco getEndereco() {
		return Endereco;
	}

	public void setDistanciaKilometros(double DistanciaKilometros) {
		this.DistanciaKilometros = DistanciaKilometros;
	}

	public double getDistanciaKilometros() {
		return DistanciaKilometros;
	}

	public void setSubTipoOferta(String SubTipoOferta) {
		this.SubTipoOferta = SubTipoOferta;
	}

	public String getSubTipoOferta() {
		return SubTipoOferta;
	}

	public void setEstagioObra(String EstagioObra) {
		this.EstagioObra = EstagioObra;
	}

	public String getEstagioObra() {
		return EstagioObra;
	}

	public void setStatusQualidadeTotal(Boolean StatusQualidadeTotal) {
		this.StatusQualidadeTotal = StatusQualidadeTotal;
	}

	public Boolean getStatusQualidadeTotal() {
		return StatusQualidadeTotal;
	}

	public void setDormitorios(int Dormitorios) {
		this.Dormitorios = Dormitorios;
	}

	public int getDormitorios() {
		return Dormitorios;
	}

	public void setCliente(Cliente Cliente) {
		this.Cliente = Cliente;
	}

	public Cliente getCliente() {
		return Cliente;
	}

	public void setUrlImagem(String UrlImagem) {
		this.UrlImagem = UrlImagem;
	}

	public String getUrlImagem() {
		return UrlImagem;
	}

	public void setSuites(int Suites) {
		this.Suites = Suites;
	}

	public int getSuites() {
		return Suites;
	}
}
