package vo;

import java.util.Date;

public class Pessoa {

	private int cod_pessoa;
	
	private String universidade;
	private String RA;
	private String descricao;

	private String nome;
	private String email;
	private String senha;
	private int sexo;
	private int tipoPerf;
	private boolean Verificado;
	private boolean primeiroAcesso;
	private Date dataCadastro;
	private boolean cadastroFb;
	
	public Pessoa(int cod_pessoa, String universidade, String rA, String descricao, String nome, String email,
			String senha, int sexo, int tipoPerf, boolean verificado, boolean primeiroAcesso, Date dataCadastro, boolean cadastroFB) {
		
	}

	public Pessoa() {
		
	}

	public int getCod_pessoa() {
		return cod_pessoa;
	}

	public void setCod_pessoa(int cod_pessoa) {
		this.cod_pessoa = cod_pessoa;
	}

	public String getUniversidade() {
		return universidade;
	}

	public void setUniversidade(String universidade) {
		this.universidade = universidade;
	}

	public String getRA() {
		return RA;
	}

	public void setRA(String rA) {
		RA = rA;
	}
	
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
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

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public int getSexo() {
		return sexo;
	}

	public void setSexo(int sexo) {
		this.sexo = sexo;
	}

	public int getTipoPerf() {
		return tipoPerf;
	}

	public void setTipoPerf(int tipoPerf) {
		this.tipoPerf = tipoPerf;
	}

	public boolean isVerificado() {
		return Verificado;
	}

	public void setVerificado(boolean verificado) {
		Verificado = verificado;
	}

	public boolean isPrimeiroAcesso() {
		return primeiroAcesso;
	}

	public void setPrimeiroAcesso(boolean primeiroAcesso) {
		this.primeiroAcesso = primeiroAcesso;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public boolean isCadastroFb() {
		return cadastroFb;
	}

	public void setCadastroFb(boolean cadastroFb) {
		this.cadastroFb = cadastroFb;
	}
	
	
	
}


