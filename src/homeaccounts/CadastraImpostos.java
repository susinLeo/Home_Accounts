package homeaccounts;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Banco.ImpostoDB;
import Banco.PostgresImpostoDB;
import Modelo.Imposto;


public class CadastraImpostos extends JInternalFrame implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	
	private ImpostoDB pdb;
	private Connection conn;
	private JPanel fundo, botoes, campos;
	private JButton bCadastro, bLimpar, bExcluir, bBuscar, bAlterar, bSair; 
	private JLabel lCodigo, lNome, lTipo, lVenci, lTotal, lCpf, a, b, c, d, e, f;
	private JTextField tCodigo, tNome, tTipo, tVenci, tTotal, tCpf;
	
	public ImpostoDB getPdb() {
		return pdb;
	}
	public void setPdb(ImpostoDB pdb) {
		this.pdb = pdb;
	}
	
	
	
	public CadastraImpostos() {
		
		this.init();
		
		this.conn = this.abreConexao();
		
		this.setPdb(new PostgresImpostoDB(this.conn));
	
		
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
	
		this.lCodigo = new JLabel("Codigo                                 ");
		this.lNome = new JLabel("Empresa Cobradora");
		this.lTipo = new JLabel("Tipo de cobranca");
		this.lVenci = new JLabel("Data de Vencimento");
		this.lCpf = new JLabel("Cpf do responsavel");
		
		this.lTotal = new JLabel("Total a pagar");
		
		this.a = new JLabel();
		this.b = new JLabel();
		this.c = new JLabel();
		this.d = new JLabel();
		this.e = new JLabel();
		this.f = new JLabel();
		
		this.tCodigo = new JTextField();
		this.tNome = new JTextField();
		this.tTipo = new JTextField();
		this.tVenci = new JTextField();
		this.tTotal = new JTextField();
		this.tCpf = new JTextField();
		
		
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
		
		this.bSair = new JButton("Sair");
		this.bSair.addActionListener(this);
		
		this.fundo = new JPanel(new BorderLayout());
		this.campos = new JPanel(new GridLayout(6, 2));
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
		this.botoes.add(f);
		this.botoes.add(bSair);
		
		this.campos.add(lCodigo);
		this.campos.add(tCodigo);
		this.campos.add(lNome);
		this.campos.add(tNome);
		this.campos.add(lTipo);
		this.campos.add(tTipo);
		this.campos.add(lVenci);
		this.campos.add(tVenci);
		this.campos.add(lTotal);
		this.campos.add(tTotal);
		this.campos.add(lCpf);
		this.campos.add(tCpf);
		
		
		this.fundo.add(this.campos, BorderLayout.EAST);
		this.fundo.add(this.botoes, BorderLayout.WEST);		
		this.getContentPane().add(this.fundo);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		
		
		
		this.setSize(500, 280);
		this.setTitle("Home Accounts - Cobrancas");
		this.setFocusable(true);
		this.requestFocus();
		this.toFront();
		this.repaint();

	}
	private void acaoFechar() {
		this.fechaConexao();
		
		this.dispose();
	}
	
	
	
	private void acaoLimpar() {
		this.tCodigo.setText("");
		this.tNome.setText("");
		this.tTipo.setText("");
		this.tVenci.setText("");
		this.tTotal.setText("");
		this.tCpf.setText("");

	}
	private Imposto daTela() {

		Imposto i = new Imposto();

		String sCodigo = this.tCodigo.getText();
		String sTotal = this.tTotal.getText();
		try {
			int codigo = Integer.parseInt(sCodigo);
			i.setCodigo(codigo);
			i.setNome(tNome.getText());
			i.setTipo(tTipo.getText());
			i.setDv(tVenci.getText());
			double total = Double.parseDouble(sTotal);
			i.setTotal(total);
			i.setCpf(tCpf.getText());
			
		} catch (NumberFormatException nfe) {
			i = null;
			System.err.println("Uncaught exception - " + nfe.getMessage());
	        nfe.printStackTrace(System.err);
		}

		return i;

	}
	private void paraTela(Imposto i) {

		if (i != null) {
			this.tCodigo.setText(Integer.toString(i.getCodigo()));
			this.tNome.setText(i.getNome());
			this.tTipo.setText(i.getTipo());
			this.tVenci.setText(i.getDv());
			this.tTotal.setText(Double.toString(i.getTotal()));
			this.tCpf.setText(i.getCpf());
			
		}

	}
	
	private void acaoInserir() {

		Imposto i = daTela();

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
		
		Imposto i = daTela();

		if (i != null) {
			this.getPdb().altera(i);
			sucesso();
		} else {
			JOptionPane.showMessageDialog(this, "Nao foi possivel alterar");
		}
		
	}
	
	private void acaoRemover() {
		
		Imposto i = daTela();

		if (i != null) {
			this.getPdb().remove(i);
			acaoLimpar();
		} else {
			JOptionPane.showMessageDialog(this, "Nao foi possivel remover");
		}
	}
	private void acaoBuscar() {

		String sCodigo = this.tCodigo.getText();

		try {
			int codigo = Integer.parseInt(sCodigo);

			Imposto i = this.getPdb().buscaPorCodigo(codigo);
			
			if (i != null) {
				paraTela(i);
			} else {
				JOptionPane.showMessageDialog(this, "Contrato " + sCodigo + " Nao foi encontrado !");
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










