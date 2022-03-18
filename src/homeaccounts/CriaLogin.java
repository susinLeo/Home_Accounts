package homeaccounts;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Banco.UsuariosDB;
import Banco.PostgresUsuariosDB;
import Modelo.Usuarios;

public class CriaLogin extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private UsuariosDB pdb;
	private Connection conn;
	private JPanel fundo, botoes, campos;
	private JButton bCadastro, bLimpar, bSair; 
	
	private JLabel  lNome, lEmail, lSenha;
	private JTextField tNome, tEmail, tSenha;
	
	public UsuariosDB getPdb() {
		return pdb;
	}

	public void setPdb(UsuariosDB pdb) {
		this.pdb = pdb;
	}
	
	public CriaLogin() {
		
		
		this.init();
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
		
		this.lNome = new JLabel("Nome										");
		this.lEmail = new JLabel("E-mail");
		this.lSenha = new JLabel("Senha");
		
		this.tNome = new JTextField(												);
		this.tEmail = new JTextField();
		this.tSenha = new JTextField();
		
		this.bSair = new JButton("Sair");
		this.bCadastro = new JButton("Cadastrar");
		this.bCadastro.addActionListener(this);
		this.bLimpar = new JButton("Limpar");
		this.bLimpar.addActionListener(this);
		this.bSair.addActionListener(this);
		
		this.fundo = new JPanel(new BorderLayout());
		this.campos = new JPanel(new GridLayout(4, 2));
		this.botoes = new JPanel(new FlowLayout()); 
	
		this.botoes.add(bCadastro);
		this.botoes.add(bLimpar);
		this.botoes.add(bSair);
		
		this.campos.add(lNome);
		this.campos.add(tNome);
		this.campos.add(lEmail);
		this.campos.add(tEmail);
		this.campos.add(lSenha);
		this.campos.add(tSenha);
		
		this.fundo.add(this.campos, BorderLayout.CENTER);
		this.fundo.add(this.botoes, BorderLayout.SOUTH);
		
		this.getContentPane().add(this.fundo);		
		this.setSize(415, 260);
		setResizable(false);
		this.setTitle("Home Accounts - Cadastro de usuario");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		this.setVisible(true);
	}

	private void acaoFechar() {
		this.fechaConexao();
		
		this.dispose();
	}
	
	private void acaoLimpar() {
		this.tNome.setText("");
		this.tEmail.setText("");
		this.tSenha.setText("");
	}
	
	private Usuarios daTela() {

		Usuarios i = new Usuarios();
		
		try {
	
			i.setNome(tNome.getText());
			i.setEmail(tEmail.getText());
			i.setSenha(tSenha.getText());
			
		} catch (NumberFormatException nfe) {
			i = null;
			System.err.println("Uncaught exception - " + nfe.getMessage());
	        nfe.printStackTrace(System.err);
		}

		return i;
	}
	
	private void acaoInserir() {

		Usuarios i = daTela();

		if (i != null) {
			JOptionPane.showMessageDialog(this, tNome.getText() + ", seu cadastro foi realizado com sucesso!");
			this.getPdb().insere(i);
		} else {
			JOptionPane.showMessageDialog(this, "Nao foi possivel inserir");	
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		 if (e.getSource().equals(bLimpar)) {
			acaoLimpar();
			

		} else if (e.getSource().equals(bCadastro)) {
			acaoInserir();
		}else if(e.getSource().equals(bSair)) {
			acaoFechar();
		}	
	}
}

