package homeaccounts;

import java.awt.BorderLayout;
import java.awt.Dimension;
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
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import Banco.ImpostoDB;
import Banco.PostgresImpostoDB;
import Banco.PostgresUsuariosDB;
import Banco.UsuariosDB;
import Modelo.Imposto;
import Modelo.Usuarios;

public class Lembretes extends JInternalFrame implements ActionListener{
	
	private static final long serialVersionUID = 1L;
	private JPanel fundo, botoes, campos;
	private JButton bSair, bReenvio;
	private JLabel lEmail;
	private JTextField tEmail;
	private UsuariosDB pdb;
	private Connection conn;
	
	
	
	public UsuariosDB getPdb() {
		return pdb;
	}

	public void setPdb(UsuariosDB pdb) {
		this.pdb = pdb;
	}
	
	public Lembretes() {
		this.conn = this.abreConexao();
		this.setPdb(new PostgresUsuariosDB(this.conn));
		this.init();
	}
	
	public void init() {

		this.setTitle("Lembretes");
		this.setSize(370,110);
		System.out.println("WTF Lembretes Init");
		
		this.fundo = new JPanel(new BorderLayout());
		this.campos = new JPanel(new GridLayout(1, 2));
		this.botoes = new JPanel(new FlowLayout());
		
		this.lEmail = new JLabel("E-mail");
		this.tEmail = new JTextField();
		
		this.bSair = new JButton("Sair");
		this.bSair.addActionListener(this);
		
		
		this.campos.add(lEmail);
		this.campos.add(tEmail);
		
		this.bReenvio = new JButton("Reenvio");
		this.bReenvio.addActionListener(this);
		this.botoes.add(bReenvio);
		this.botoes.add(bSair);
		
		this.fundo.add(campos, BorderLayout.CENTER);
		this.fundo.add(botoes, BorderLayout.SOUTH);
		
		this.getContentPane().add(this.fundo);
		
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
	
	public void acaoReenvio() {
		String email = this.tEmail.getText();
		
		try {
			
			Usuarios c = this.getPdb().buscaPorEmail(email);
			
			if (c != null) {
				JavaMailApp sendmail = new JavaMailApp();
				
				sendmail.init(email);
				
				JOptionPane.showMessageDialog(this, "Dados enviados!");
			}else {
				JOptionPane.showMessageDialog(this, "Dados invalidos");
			}
		} catch (NumberFormatException nfe) {
			nfe.printStackTrace();
		}
		
	}
	private void acaoFechar() {
		this.fechaConexao();
		
		this.dispose();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(bSair)) {
			this.acaoFechar();
		}
		
		if(e.getSource().equals(bReenvio)) {
			this.acaoReenvio();
		}
			
		
	}
	public void setPosicao() {
	    Dimension d = this.getDesktopPane().getSize();
	    this.setLocation((d.width - this.getSize().width) / 2, (d.height - this.getSize().height) / 2); 
	}

}