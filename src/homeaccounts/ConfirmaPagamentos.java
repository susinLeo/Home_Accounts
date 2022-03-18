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

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane.CloseAction;

import Banco.ImpostoDB;
import Banco.PostgresImpostoDB;
import Modelo.Imposto;

public class ConfirmaPagamentos extends JInternalFrame implements ActionListener  {
	
private static final long serialVersionUID = 1L;
	
	private ImpostoDB pdb;
	private Connection conn;
	private JPanel fundo, botoes, campos;
	private JButton bBuscar, bConfirmar, bLimpar, bSair;
	private JLabel lCodigo, lNome, lTipo, lVenci, lTotal, lCpf, tNome, tTipo; 
	private JLabel tVenci, tTotal, tCpf;
	private JTextField tCodigo;

	
	public ImpostoDB getPdb() {
		return pdb;
	}
	public void setPdb(ImpostoDB pdb) {
		this.pdb = pdb;
	}
	
	public ConfirmaPagamentos() {
		
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
		this.lCodigo = new JLabel("Codigo");
		this.lNome = new JLabel("Empresa cobradora");
		this.lTipo = new JLabel("Tipo de cobranca");
		this.lVenci = new JLabel("Data de Vencimento");
		this.lCpf = new JLabel("Cpf do responsavel");
		this.lTotal = new JLabel("Total a pagar");
		
		this.bConfirmar = new JButton("Pagamento realizado");
		this.bConfirmar.addActionListener(this);
		this.bLimpar = new JButton("Limpar tela");
		this.bLimpar.addActionListener(this);
		this.bBuscar = new JButton("Buscar");
		this.bBuscar.addActionListener(this);
		this.bSair = new JButton("Sair");
		this.bSair.addActionListener(this);


		
		this.tCodigo = new JTextField();
		this.tNome = new JLabel("");
		this.tTipo = new JLabel("");
		this.tVenci = new JLabel("");
		this.tCpf = new JLabel("");
		this.tTotal = new JLabel("");
		
		this.fundo = new JPanel(new BorderLayout());
		this.campos = new JPanel(new GridLayout(6, 2));
		this.botoes = new JPanel(new FlowLayout()); 
		
		this.botoes.add(bBuscar);
		this.botoes.add(bLimpar);
		this.botoes.add(bConfirmar);
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
		
		this.fundo.add(this.campos, BorderLayout.CENTER);
		this.fundo.add(this.botoes, BorderLayout.SOUTH);
		
		this.getContentPane().add(this.fundo);
		
		this.setSize(500, 280);
		this.setTitle("Home Accounts - Confirmar pagamento");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
	private void acaoInserir() {

		Imposto i = daTela();

		if (i != null) {
			this.getPdb().insere2(i);
			this.getPdb().remove(i);
			acaoLimpar();
		} else {
			JOptionPane.showMessageDialog(this, "Nao foi possivel registrar");
		}

	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(bBuscar)) {
			acaoBuscar();
		} else if (e.getSource().equals(bLimpar)) {
			acaoLimpar();
		
		} else if (e.getSource().equals(bConfirmar)) {
			acaoInserir();
		} else if (e.getSource().equals(bSair)) {
			acaoFechar();
		}
	}
	public void setPosicao() {
	    Dimension d = this.getDesktopPane().getSize();
	    this.setLocation((d.width - this.getSize().width) / 2, (d.height - this.getSize().height) / 2); 
	}

}
