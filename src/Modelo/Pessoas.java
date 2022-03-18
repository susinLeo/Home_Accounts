package Modelo;

import java.awt.List;
import java.io.Serializable;
import java.util.ArrayList;

import Modelo.Imposto;
import Banco.ImpostoDB;
import Banco.PostgresImpostoDB;
public class Pessoas implements Serializable {
private static final long serialVersionUID = 1L;
	private String cpf;
	private String nome;
	private double salario;
	private ArrayList<Imposto> imposto;
	
	
	public Pessoas(){
		this.imposto = new ArrayList<Imposto>();
	}
	
	public Pessoas(String cpf, String nome, double salario) {
		super();
		this.cpf = cpf;
		this.nome = nome;
		this.salario = salario;
		
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public double getSalario() {
		return salario;
	}

	public void setSalario(double salario) {
		this.salario = salario;
	}
	
	public void addImposto(Imposto imposto) {
		imposto.setPessoas(this);
		this.imposto.add(imposto);
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		
		sb.append("Cpf  = ");
		sb.append(this.getCpf());
		sb.append(" ");

		sb.append(",Nome  = ");
		sb.append(this.getNome());
		sb.append(" ");
		
		sb.append(",Salario  = ");
		sb.append(this.getSalario());
		sb.append(" ");
		return sb.toString();
	}
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((cpf == null) ? 0 : cpf.hashCode());
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
		Pessoas other = (Pessoas) obj;
		if (cpf == null) {
			if (other.cpf != null)
				return false;
		} else if (!cpf.equals(other.cpf))
			return false;
		return true;
	}

	
	
}





