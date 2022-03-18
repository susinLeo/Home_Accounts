package Modelo;

import java.io.Serializable;


public class Usuarios implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String nome;
	private String email;
	private String senha;

	
	public Usuarios(){}
	
	public Usuarios( String nome, String email, String senha) {
		super();
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		
	}


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		
		

		sb.append(",Nome  = ");
		sb.append(this.getNome());
		sb.append(" ");
		
		
		sb.append(",Email = ");
		sb.append(this.getEmail());
		sb.append(" ");
		
		sb.append(",Senha = ");
		sb.append(this.getSenha());
		sb.append(" ");
		
		return sb.toString();
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
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
		Usuarios other = (Usuarios) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		return true;
	}
	
}
