package homeaccounts;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Banco.PessoaDB;
import Banco.PostgresPessoasDB;
import Modelo.Pessoas;

public class CadastraPessoas extends JInternalFrame implements ActionListener{
	
	private static final long serialVersionUID = 1L;
	private PessoaDB pdb;
	private Connection conn;
	private JPanel fundo, botoes, campos;
	private JButton bCadastro, bLimpar, bExcluir, bBuscar, bAlterar, bSair; 
	private JLabel lCpf, lNome, lSalario, a, b, c, d, e, f, g, h, i, j;
	private JTextField tCpf, tNome, tSalario;
	
	
	
	public PessoaDB getPdb() {
		return pdb;
	}

	public void setPdb(PessoaDB pdb) {
		this.pdb = pdb;
	}
	
	
	public CadastraPessoas() {
		
		
		this.init();
		this.conn = this.abreConexao();
		
		this.setPdb(new PostgresPessoasDB(this.conn));
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
		this.lCpf  = new JLabel("Cpf 	                                 ");
		this.lNome = new JLabel("Nome");
		this.lSalario = new JLabel("Salario");
		this.a = new JLabel();
		this.b = new JLabel();
		this.c = new JLabel();
		this.d = new JLabel();
		this.e = new JLabel();
		this.f = new JLabel();
		this.g = new JLabel();
		this.h = new JLabel();
		this.i = new JLabel();
		this.j = new JLabel();




		
		this.tCpf = new JTextField(                                              );
		this.tNome = new JTextField();
		this.tSalario = new JTextField();
		
		this.bSair = new JButton("Sair");
		this.bSair.addActionListener(this);
		
		this.bCadastro = new JButton("Cadastrar");
		this.bCadastro.addActionListener(this);
		this.bLimpar = new JButton("Limpar");
		this.bLimpar.addActionListener(this);
		this.bExcluir = new JButton("Excluir");
		this.bExcluir.addActionListener(this);
		this.bBuscar = new JButton("Buscar");
		this.bBuscar.addActionListener(this);
		this.bAlterar = new JButton("Alterar");
		this.bAlterar.addActionListener(this);
		
		this.fundo = new JPanel(new BorderLayout());
		this.campos = new JPanel(new GridLayout(5, 2));
		this.botoes = new JPanel(new GridLayout(13,1)); 		
		this.botoes.add(e);
		this.botoes.add(bCadastro);
		this.botoes.add(a);
		this.botoes.add(bLimpar);
		this.botoes.add(b);
		this.botoes.add(bExcluir);
		this.botoes.add(c);
		this.botoes.add(bBuscar);
		this.botoes.add(d);
		this.botoes.add(bAlterar);
		this.botoes.add(j);
		this.botoes.add(bSair);
		
		this.campos.add(lCpf);
		this.campos.add(tCpf);
		this.campos.add(f);
		this.campos.add(g);
		this.campos.add(lNome);
		this.campos.add(tNome);
		this.campos.add(h);
		this.campos.add(i);
		this.campos.add(lSalario);
		this.campos.add(tSalario);
		
		
		this.fundo.add(this.campos, BorderLayout.EAST);
		this.fundo.add(this.botoes, BorderLayout.WEST);
		
		this.getContentPane().add(this.fundo);

		
		this.setSize(500, 280);
		setResizable(true);
		this.setTitle("Home Accounts - Cadastro de usuario");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.toFront();
		this.repaint();
	}

	private void acaoFechar() {
		this.fechaConexao();
		this.dispose();
		
	}
	
	private void acaoLimpar() {
		this.tCpf .setText("");
		this.tNome.setText("");
		this.tSalario.setText("");
	}
	
	private Pessoas daTela() {

		Pessoas i = new Pessoas();
		String sSalario = this.tSalario.getText();
		
		try {
			i.setCpf(tCpf.getText());
			double salario = Double.parseDouble(sSalario);
			i.setSalario(salario);
			
			i.setNome(tNome.getText());
			
			
		} catch (NumberFormatException nfe) {
			i = null;
			System.err.println("Uncaught exception - " + nfe.getMessage());
	        nfe.printStackTrace(System.err);
		}

		return i;

	}
	
	private void paraTela(Pessoas i) {

		if (i != null) {
			this.tCpf.setText(i.getCpf());
			this.tNome.setText(i.getNome());
			this.tSalario.setText(Double.toString(i.getSalario()));
			
			
		}

	}
	
	private void acaoInserir() {

		Pessoas i = daTela();

		if (i != null) {
			this.getPdb().insere(i);
			
		} else {
			JOptionPane.showMessageDialog(this, "Nao foi possivel inserir");
		}

	}
	private void sucesso() {
		JOptionPane.showMessageDialog(this, "Operacao realizada com sucesso!");
	}
	
	private void acaoAlterar() {
		
		Pessoas i = daTela();

		if (i != null) {
			this.getPdb().altera(i);
			sucesso();
		} else {
			JOptionPane.showMessageDialog(this, "Nao foi possivel alterar");
		}
		
	}
	
	private void acaoRemover() {
		
		Pessoas i = daTela();

		if (i != null) {
			this.getPdb().remove(i);
			acaoLimpar();
		} else {
			JOptionPane.showMessageDialog(this, "Nao foi possivel remover");
		}
	}
	private void acaoBuscar() {

		String sCpf = this.tCpf.getText();

		try {
			String cpf = sCpf;

			Pessoas i = this.getPdb().buscaPorCpf(cpf);
			
			if (i != null) {
				paraTela(i);
			} else {
				JOptionPane.showMessageDialog(this," Cpf nao foi encontrado !");
			}

			
		} catch (NumberFormatException nfe) {
			JOptionPane.showMessageDialog(this, "Nao foi possivel buscar");
			nfe.printStackTrace();
		}

	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(bBuscar)) {
			acaoBuscar();
		} else if (e.getSource().equals(bLimpar)) {
			acaoLimpar();
		} else if (e.getSource().equals(bCadastro)) {
			acaoInserir();
		} else if (e.getSource().equals(bAlterar)) {
			acaoAlterar();
		} else if (e.getSource().equals(bExcluir)) {
			acaoRemover();
		} else if (e.getSource().equals(bSair)) {
			acaoFechar();
		}
	}
	public void setPosicao() {
	    Dimension d = this.getDesktopPane().getSize();
	    this.setLocation((d.width - this.getSize().width) / 2, (d.height - this.getSize().height) / 2); 
	}
	


}


