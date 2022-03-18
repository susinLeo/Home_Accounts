package Banco;

import java.util.List;

import Modelo.Imposto;
import Modelo.Pessoas;
import Modelo.Usuarios;


public interface PessoaDB {
	List<Pessoas> buscaTodos();
	List<Imposto> bucaTodosCpf(String cpf);
	Pessoas buscaPorCpf(String cpf);
	
	void insere(Pessoas pessoa);
	void altera(Pessoas pessoa);
	void remove(String cpf);
	void remove(Pessoas i);
	
}
