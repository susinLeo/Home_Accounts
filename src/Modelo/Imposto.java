package Modelo;

import java.io.Serializable;



public class Imposto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private int codigo;
	private String nome;
	private String tipo;
	private String dv;
	private double total;
	private String cpf;
	private Pessoas pessoas;
	
	public Imposto(){}
	
	public Imposto(int codigo, String cpf, String nome, String tipo, String dv, double total) {
		super();
		this.codigo = codigo;
		this.cpf = cpf;
		this.nome = nome;
		this.tipo = tipo;
		this.dv = dv;
		this.total = total;
		
		
	}

	public String getCpf() { 
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getDv() {
		return dv;
	}

	public void setDv(String dv) {
		this.dv = dv;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		
		sb.append("Codigo = ");
		sb.append(this.getCodigo());
		sb.append(" ");
		

		sb.append(",Empresa Cobradora  = ");
		sb.append(this.getNome());
		sb.append(" ");
		
		
		sb.append(",Tipo = ");
		sb.append(this.getTipo());
		sb.append(" ");
		
		sb.append(",Data de Vencimento = ");
		sb.append(this.getDv());
		sb.append(" ");
		
		sb.append(",Total = ");
		sb.append(this.getTotal());
		sb.append(" ");
		
		sb.append(",Cpf do responsavel = ");
		sb.append(this.getCpf());
		sb.append(" ");

		return sb.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + codigo;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Imposto other = (Imposto) obj;
		if (codigo != other.codigo)
			return false;
		return true;
	}
	

	public void setPessoas(Pessoas pessoas) {
		this.pessoas = pessoas;
	}
	public Pessoas getPessoa() {
		return pessoas;
	}

	
	
}



