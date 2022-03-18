package TableModels;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import Modelo.Imposto;

public class ImpostosTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private List<Imposto> impostos;
	private String[] nomes = { "Codigo", "CPF", "Nome", "Tipo", "Data Vencimento", "Valor Total" };

	public ImpostosTableModel(List<Imposto> impostos) {
		this.impostos = impostos;
	}

	public ImpostosTableModel() {
		this.impostos = new ArrayList<Imposto>();
	}

	public List<Imposto> getImpostos() {
		return impostos;
	}

	public void setImpostos(List<Imposto> impostos) {
		this.impostos = impostos;
		this.fireTableDataChanged();
	}

	@Override
	public int getRowCount() {
		if(impostos==null) {
			return 0;
		}
		return impostos.size();
	}

	@Override
	public int getColumnCount() {
		return nomes.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {

		Imposto a = impostos.get(rowIndex);

		if (a == null) {
			return null;
		}

		switch (columnIndex) {
		case 0:
			return a.getCodigo();
		case 1:
			return a.getCpf();
		case 2:
			return a.getNome();
		case 3:
			return a.getTipo();
		case 4:
			return a.getDv();
		case 5:
			return a.getTotal();
		}
		return null;
	}

	public String getColumnName(int number) {
		return nomes[number];
	}
	
	public void addAluno(Imposto a) {
		this.impostos.add(a);
		this.fireTableDataChanged();
	}

}

