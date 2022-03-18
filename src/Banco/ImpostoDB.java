package Banco;

import java.util.List;

import Modelo.Imposto;

public interface ImpostoDB {
	
	List<Imposto> buscaTodos();
	List<Imposto> buscaTodos2();
	List<Imposto> buscaTodosCpf(String cpf);
	List<Imposto> buscaTodosCpf2(String cpf);

	Imposto buscaPorCodigo(int codigo);
	Imposto buscaPorCpf(String cpf);

	Imposto buscaPorCpf2(String cpf);

	void insere(Imposto imposto);
	void altera(Imposto imposto);
	void remove(int codigo);
	void remove(Imposto i);
	void insere2(Imposto imposto);

}
