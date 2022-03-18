package homeaccounts;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Banco.UsuariosDB;
import Banco.PostgresUsuariosDB;
import Modelo.Usuarios;

public class PrincipalLogin extends JFrame implements ActionListener{
	
	private static final long serialVersionUID = 1L;
	private UsuariosDB pdb;
	private Connection conn;
	private JPanel fundo, botoes, campos;
	private JButton bEntrar, bCadastrar;
	private JLabel lEmail2, lSenha2;
	private JTextField tEmail2, tSenha2;
	
	
	public UsuariosDB getPdb() {
		return pdb;
	}

	public void setPdb(UsuariosDB pdb) {
		this.pdb = pdb;
	}
	
	public static void main(String[] args) {
		PrincipalLogin l = new PrincipalLogin();
		
		l.init();
		
	}
	
	public PrincipalLogin() {
		this.conn = this.abreConexao();
		this.setPdb(new PostgresUsuariosDB(this.conn));
	}
	public Connection abreConexao( ) {
		
		Connection conn = null;
		
		String driver = "org.postgresql.Driver";
		String url = "jdbc:postgresql://tuffi.db.elephantsql.com:5432/rjapwirb";
		String user = "rjapwirb";
		String pwd = "nQ9iZF-yL3JKNEDHj_3eh2OOQO5adDhn";
		
		try {
			
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, pwd);
		} catch (ClassNotFoundException cnfe) {
			System.out.println("Nao foi possivel encontrar o driver JDBC");
			cnfe.printStackTrace();
		} catch (SQLException se) {
			System.out.println("Nao foi possivel conectar ao Banco de Dados");
			se.printStackTrace();
		}
		
		return conn;
		
	}
	public void fechaConexao() {
		
		if(conn != null) {
			try {
				conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
	}
	
	public void init() {
		
		this.lEmail2 = new JLabel("E-mail");
		this.lSenha2 = new JLabel("Senha");
		this.tEmail2 = new JTextField();
		this.tSenha2 = new JTextField();
		this.bEntrar = new JButton("Entrar");
		this.bCadastrar = new JButton("Criar Login");
		this.fundo = new JPanel(new BorderLayout());
		this.campos = new JPanel(new GridLayout(2, 2));
		this.botoes = new JPanel(new FlowLayout()); 
		this.botoes.add(bEntrar);
		this.botoes.add(bCadastrar);
		this.campos.add(lEmail2);
		this.campos.add(tEmail2);
		this.campos.add(lSenha2);
		this.campos.add(tSenha2);
		this.bEntrar.addActionListener(this);
		this.bCadastrar.addActionListener(this);
		
		this.fundo.add(this.campos, BorderLayout.CENTER);
		this.fundo.add(this.botoes, BorderLayout.SOUTH);
		this.getContentPane().add(this.fundo);
		
		this.setSize(350, 150);
		setResizable(false);
		this.setTitle("Home Accounts - Login");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		this.setVisible(true);
	
	}
	
	
	private Usuarios daTela() {
		Usuarios i = new Usuarios();
		try {
			i.setEmail(tEmail2.getText());
			i.setSenha(tSenha2.getText());
			
		} catch (NumberFormatException nfe) {
			i = null;
			System.err.println("Uncaught exception - " + nfe.getMessage());
	        nfe.printStackTrace(System.err);
		}

		return i;

	}
	
	
	private void paraTela(Usuarios i) {
		if (i != null) {
			this.tEmail2.setText(i.getEmail());
			this.tSenha2.setText(i.getSenha());
			
		}
	}
	
	private void acaoCad() {
		CriaLogin c = new CriaLogin();
		c.setVisible(true);
	}
	
	private void acaoAbrir() {
		try {
			String senha = this.tSenha2.getText();
			String email = this.tEmail2.getText();
			Usuarios i = this.getPdb().buscaPorSenha(senha);
			Usuarios c =this.getPdb().buscaPorEmail(email);
			
			if (i != null) {
				if (c != null) {
					Principal p = new Principal();
					p.setVisible(true);
					this.setVisible(false);
				}else {
					JOptionPane.showMessageDialog(this, "Dados invalidos");
				}
				
			} else {
				JOptionPane.showMessageDialog(this, "Dados invalidos");
			}

			
		} catch (NumberFormatException nfe) {
			nfe.printStackTrace();
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(bEntrar)) {
			acaoAbrir();
		}else if(e.getSource().equals(bCadastrar)) {
			acaoCad();
		}
		
	}
	

}



