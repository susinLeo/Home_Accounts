package Banco;

import java.util.List;

import Modelo.Usuarios;

public interface UsuariosDB {
	
	//List<Usuarios> buscaTodos();
	Usuarios buscaPorSenha(String senha);
	Usuarios buscaPorEmail(String email);
	void insere(Usuarios usuario);
	//void altera(Usuarios usuario);
	//void remove(int codigo);
	//void remove(Usuarios i);

}
