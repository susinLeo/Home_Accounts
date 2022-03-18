package Banco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import Banco.PessoaDB;
import Banco.ImpostoDB;
import Modelo.Pessoas;
import Modelo.Imposto;
import Banco.PostgresPessoasDB;



public class PostgresImpostoDB implements ImpostoDB {

	private Connection conn;

	public PostgresImpostoDB(Connection conn) {
		this.conn = conn;
	}
	
	public List<Imposto> buscaTodosCpf(String cpf) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Imposto> imposto = new ArrayList<Imposto>();
		try {

			pstmt = conn.prepareStatement("select * from imposto where cpf = ?");
			pstmt.setString(1, cpf);
			rs = pstmt.executeQuery();
			
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
				pstmt.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
		return imposto;
		
	}
		
	@Override
	public List<Imposto> buscaTodos() {

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
		return imposto;
	}
	
	public List<Imposto> buscaTodos2() {

		Statement stmt = null;
		ResultSet rs = null;
		List<Imposto> imposto = new ArrayList<Imposto>();

		try {

			stmt = conn.createStatement();
			rs = stmt.executeQuery("select codigo, nome, tipo, dv, total, cpf from impostopago order by codigo");

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
		return imposto;
	}

	@Override
	public Imposto buscaPorCodigo(int codigo) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Imposto i = null;

		try {

			pstmt = conn.prepareStatement("select codigo, nome, tipo, dv, total, cpf from imposto where codigo = ?");
			pstmt.setInt(1, codigo);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				i = new Imposto();
				i.setCodigo(rs.getInt("codigo"));
				i.setNome(rs.getString("nome"));
				i.setTipo(rs.getString("tipo"));
				i.setDv(rs.getString("dv"));
				i.setTotal(rs.getDouble("total"));
				i.setCpf(rs.getString("cpf"));
				
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
	public void insere(Imposto imposto) {

		PreparedStatement pstmt = null;

		try {

			pstmt = conn.prepareStatement("insert into imposto(codigo, nome, tipo, dv, total, cpf) values (?,?,?,?,?,?)");
			pstmt.setInt(1, imposto.getCodigo());
			pstmt.setString(2, imposto.getNome());
			pstmt.setString(3, imposto.getTipo());
			pstmt.setString(4, imposto.getDv());
			pstmt.setDouble(5, imposto.getTotal());
			pstmt.setString(6, imposto.getCpf());
			pstmt.executeUpdate();

		} catch (SQLException se) {
			System.out.println("Ocorreu um erro inserindo os dados :");
			JOptionPane.showMessageDialog(null, "Erro ao inserir dados, verifique o cpf e o codigo digitado");

			se.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
	}
	
	public void insere2(Imposto imposto) {

		PreparedStatement pstmt = null;

		try {

			pstmt = conn.prepareStatement("insert into impostopago(codigo, nome, tipo, dv, total, cpf) values (?,?,?,?,?,?)");
			pstmt.setInt(1, imposto.getCodigo());
			pstmt.setString(2, imposto.getNome());
			pstmt.setString(3, imposto.getTipo());
			pstmt.setString(4, imposto.getDv());
			pstmt.setDouble(5, imposto.getTotal());
			pstmt.setString(6, imposto.getCpf());
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
	public void altera(Imposto imposto) {
		PreparedStatement pstmt = null;

		try {
			pstmt = conn.prepareStatement("update imposto set nome = ?, tipo = ?, dv = ?, total = ?, cpf = ? where codigo = ?");
			
			pstmt.setString(1, imposto.getNome());
			pstmt.setString(2, imposto.getTipo());
			pstmt.setString(3, imposto.getDv());
			pstmt.setDouble(4, imposto.getTotal());
			pstmt.setString(5, imposto.getCpf());

			pstmt.setInt(6, imposto.getCodigo());

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
	public void remove(int codigo) {
		PreparedStatement pstmt = null;

		try {

			pstmt = conn.prepareStatement("delete from imposto where codigo = ?");
			pstmt.setInt(1, codigo);
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
	public void remove(Imposto i) {
		this.remove(i.getCodigo());
	}

	@Override
	public Imposto buscaPorCpf(String cpf) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Imposto i = null;

		try {

			pstmt = conn.prepareStatement("select * from imposto where cpf = ?");
			pstmt.setString(1, cpf);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				i = new Imposto();
				i.setCodigo(rs.getInt("codigo"));
				i.setNome(rs.getString("nome"));
				i.setTipo(rs.getString("tipo"));
				i.setDv(rs.getString("dv"));
				i.setTotal(rs.getDouble("total"));
				i.setCpf(rs.getString("cpf"));
				
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
	public List<Imposto> buscaTodosCpf2(String cpf) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Imposto> imposto = new ArrayList<Imposto>();
		try {

			pstmt = conn.prepareStatement("select * from impostopago where cpf = ?");
			pstmt.setString(1, cpf);
			rs = pstmt.executeQuery();
			
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
				pstmt.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
		return imposto;
		
	
	}

	@Override
	public Imposto buscaPorCpf2(String cpf) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Imposto i = null;

		try {

			pstmt = conn.prepareStatement("select * from impostopago where cpf = ?");
			pstmt.setString(1, cpf);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				i = new Imposto();
				i.setCodigo(rs.getInt("codigo"));
				i.setNome(rs.getString("nome"));
				i.setTipo(rs.getString("tipo"));
				i.setDv(rs.getString("dv"));
				i.setTotal(rs.getDouble("total"));
				i.setCpf(rs.getString("cpf"));
				
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
	

}
