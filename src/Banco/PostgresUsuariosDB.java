package Banco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import Modelo.Usuarios;




public class PostgresUsuariosDB implements UsuariosDB {

	private Connection conn;

	public PostgresUsuariosDB(Connection conn) {
		
		this.conn = conn;
	}
	
	
	public Usuarios buscaPorEmail(String email) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Usuarios i = null;
		
		try {
			pstmt = conn.prepareStatement("select nome, email, senha from cadastrousuario where email = ?");

			pstmt.setString(1, email);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				i = new Usuarios();
				i.setNome(rs.getString("nome"));
				i.setEmail(rs.getString("email"));
				i.setSenha(rs.getString("senha"));	
				
			}
		}catch (SQLException se) {
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
	
	
		
		
	
	
	
	public Usuarios buscaPorSenha(String senha) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Usuarios i = null;
		
		try {
			pstmt = conn.prepareStatement("select nome, email, senha from cadastrousuario where senha = ?");

			pstmt.setString(1, senha);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				i = new Usuarios();
				i.setNome(rs.getString("nome"));
				i.setEmail(rs.getString("email"));
				i.setSenha(rs.getString("senha"));	
				
			}
		}catch (SQLException se) {
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
		
	/*
	@Override
	public Usuarios buscaPorCodigo(int codigo) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Usuarios i = null;

		try {

			pstmt = conn.prepareStatement("select codigo, nome, email, senha from cadastrousuario where codigo = ?");
			pstmt.setInt(1, codigo);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				i = new Usuarios();
				i.setNome(rs.getString("nome"));
				i.setEmail(rs.getString("email"));
				i.setSenha(rs.getString("senha"));
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
	*/

	@Override
	public void insere(Usuarios usuario) {

		PreparedStatement pstmt = null;

		try {

			pstmt = conn.prepareStatement("insert into cadastrousuario(nome, email, senha) values (?,?,?)");
			pstmt.setString(1, usuario.getNome());
			pstmt.setString(2, usuario.getEmail());
			pstmt.setString(3, usuario.getSenha());
			pstmt.executeUpdate();

		} catch (SQLException se) {
			System.out.println("Ocorreu um erro inserindo os dados :");
			JOptionPane.showMessageDialog(null, "Erro ao inserir dados, verifique as informacoes digitadas ou tente um codigo diferente");
				
			
			se.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
	}
	/*
	@Override
	public void altera(Usuarios usuario) {
		PreparedStatement pstmt = null;

		try {
			pstmt = conn.prepareStatement("update cadastrousuario set nome = ?, email = ?, senha = ? where codigo = ?");
			
			pstmt.setString(1, usuario.getNome());
			pstmt.setString(2, usuario.getEmail());
			pstmt.setString(3, usuario.getSenha());
			pstmt.setInt(4, usuario.getCodigo());
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
	*/
	/*
	@Override
	public void remove(int codigo) {
		PreparedStatement pstmt = null;

		try {

			pstmt = conn.prepareStatement("delete from cadastrousuario where codigo = ?");
			pstmt.setInt(1, codigo);
			pstmt.executeUpdate();

		} catch (SQLException se) {
			System.out.println("Ocorreu um erro removendo os dados :");
			JOptionPane.showMessageDialog(null, "Erro ao remover dados");

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
	public void remove(Usuarios u) {
		this.remove(u.getCodigo());
	}
*/
	
}

