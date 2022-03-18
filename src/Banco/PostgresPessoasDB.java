package Banco;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import Modelo.Imposto;
import Modelo.Pessoas;

public class PostgresPessoasDB implements PessoaDB  {
	private Connection conn;

	public PostgresPessoasDB(Connection conn) {
		this.conn = conn;
	}
	
	public List<Imposto> bucaTodosCpf(String cpf) {
		Statement stmt = null;
		ResultSet rs = null;
		List<Imposto> imposto = new ArrayList<Imposto>();
		
		
		try {

			stmt = conn.createStatement();
			rs = stmt.executeQuery("select codigo, nome, tipo, dv, total, cpf from imposto order by cpf");
			while (rs.next()) {
				Imposto i = new Imposto();
				i.setCodigo(rs.getInt("codigo"));
				i.setNome(rs.getString("nome"));
				i.setTipo(rs.getString("tipo"));
				i.setDv(rs.getString("dv"));
				i.setTotal(rs.getDouble("total"));
				i.setCpf(rs.getString("cpf"));
				imposto.add(i);
			}
		}	catch (SQLException se) {
			System.out.println("Ocorreu um erro acessando os dados :");
			JOptionPane.showMessageDialog(null, "Erro ao acessar dados");

			se.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
		return imposto;
	}

	@Override
	public List<Pessoas> buscaTodos() {

		Statement stmt = null;
		ResultSet rs = null;
		List<Pessoas> pessoa = new ArrayList<Pessoas>();

		try {

			stmt = conn.createStatement();
			rs = stmt.executeQuery("select cpf, nome, salario from pessoa order by cpf");

			while (rs.next()) {
				Pessoas i = new Pessoas();
				i.setCpf(rs.getString("cpf"));
				i.setNome(rs.getString("nome"));
				i.setSalario(rs.getDouble("salario"));
				pessoa.add(i);
			}

		} catch (SQLException se) {
			System.out.println("Ocorreu um erro acessando os dados :");
			JOptionPane.showMessageDialog(null, "Erro ao acessar dados");

			se.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
		return pessoa;
	}

	@Override
	public Pessoas buscaPorCpf(String cpf) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Pessoas i = null;

		try {

			pstmt = conn.prepareStatement("select cpf, nome, salario from pessoa where cpf = ?");
			pstmt.setString(1, cpf);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				i = new Pessoas();
				i.setCpf(rs.getString("cpf"));
				i.setNome(rs.getString("nome"));
				i.setSalario(rs.getDouble("salario"));
				
			}

		} catch (SQLException se) {
			System.out.println("Ocorreu um erro acessando os dados :");
			JOptionPane.showMessageDialog(null, "Erro ao acessar dados");

			se.printStackTrace();
		} finally {
			try {
				rs.close();
				pstmt.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
		return i;
	}

	@Override
	public void insere(Pessoas pessoa) {

		PreparedStatement pstmt = null;

		try {

			pstmt = conn.prepareStatement("insert into pessoa(cpf, nome, salario) values (?,?,?)");
			pstmt.setString(1, pessoa.getCpf());
			pstmt.setString(2, pessoa.getNome());
			pstmt.setDouble(3, pessoa.getSalario());
			pstmt.executeUpdate();

		} catch (SQLException se) {
			System.out.println("Ocorreu um erro inserindo os dados :");
			JOptionPane.showMessageDialog(null, "Erro ao inserir dados");

			se.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
	}
	@Override
	public void altera(Pessoas pessoa) {
		PreparedStatement pstmt = null;

		try {
			pstmt = conn.prepareStatement("update pessoa set nome = ?, salario = ? where cpf = ?");
			
			pstmt.setString(1, pessoa.getNome());
			pstmt.setDouble(2, pessoa.getSalario());
			pstmt.setString(3, pessoa.getCpf());
			pstmt.executeUpdate();

		} catch (SQLException se) {
			System.out.println("Ocorreu um erro alterando os dados :");
			JOptionPane.showMessageDialog(null, "Erro ao alterar dados");

			se.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}

	}
	@Override
	public void remove(String cpf) {
		PreparedStatement pstmt = null;

		try {

			pstmt = conn.prepareStatement("delete from pessoa where cpf = ?");
			pstmt.setString(1, cpf);
			pstmt.executeUpdate();

		} catch (SQLException se) {
			System.out.println("Ocorreu um erro removendo os dados :");
			se.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	@Override
	public void remove(Pessoas i) {
		this.remove(i.getCpf());
	}

	


	
	

}

