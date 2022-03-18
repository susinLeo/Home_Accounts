package homeaccounts;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import Banco.PessoaDB;
import Banco.PostgresPessoasDB;
import Modelo.Pessoas;
import Banco.ImpostoDB;
import Banco.PostgresImpostoDB;
import Modelo.Imposto;
import TableModels.ImpostosTableModel;

public class ContratosPagos extends JInternalFrame implements ActionListener{
	
	private static final long serialVersionUID = 1L;
	private ImpostoDB pdb;
	private Connection conn;
	private JPanel fundo, botoes, campo;
	private JButton bBusca, bBusca2, bSair;
	private JTextField tCpf;
	private String cpf;
	private static final int STATUS_COL = 1;
	
	

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}


	private JTable tImpostos;
	private ImpostosTableModel tmImpostos;
	private JScrollPane scroll;
	
	public static void main(String[] args) {
		
		ContratosPagos lista = new ContratosPagos();
		lista.init();
		
	}
	
	public ImpostoDB getPdb() {
		return pdb;
	}

	public void setPdb(ImpostoDB pdb) {
		this.pdb = pdb;
	}
	
	public ContratosPagos() {
		
		
		this.init();
		this.conn = this.abreConexao();
	
		this.setPdb(new PostgresImpostoDB(this.conn));
	}
	
	public void init() {
		
		this.setTitle("Contratos pagos");
		
		this.fundo = new JPanel(new BorderLayout());
		this.botoes = new JPanel(new FlowLayout());
		this.campo = new JPanel(new GridLayout(1,2));
		this.bSair = new JButton("Sair");
		this.bSair.addActionListener(this);
		
		this.bBusca = new JButton("Buscar todos");
		this.bBusca.addActionListener(this);
		this.bBusca2 = new JButton("Buscar por Cpf");
		this.bBusca2.addActionListener(this);
		this.tCpf = new JTextField(								);
		
		this.botoes.add(this.bBusca);
		this.botoes.add(this.bSair);
		
		this.campo.add(this.bBusca2);
		this.campo.add(this.tCpf);
		this.campo.add(this.botoes, BorderLayout.NORTH);
		
		
		this.tmImpostos = new ImpostosTableModel();
		this.tImpostos = new JTable(this.tmImpostos);
		this.scroll = new JScrollPane(this.tImpostos);
		
		this.tImpostos.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				
				if(e.getValueIsAdjusting()) {
					return;
				}
				
				ImpostosTableModel model = (ImpostosTableModel) tImpostos.getModel();
				int indice = e.getLastIndex();
				Imposto selecionado = model.getImpostos().get(indice);
				//System.out.println(selecionado);
				
			}
		});
		
		this.tImpostos.setDefaultRenderer(Object.class, new ColorContratoPago());

		
		this.fundo.add(this.scroll, BorderLayout.CENTER);
		this.fundo.add(this.campo, BorderLayout.SOUTH);
		

		this.getContentPane().add(this.fundo);
		
		this.setSize(800,550);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.toFront();
		this.repaint();
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
	private void acaoFechar() {
		this.fechaConexao();
		
		this.dispose();
	}
	
	public void acaoBuscar() {
		List<Imposto> impostos = this.getPdb().buscaTodos2();
		this.tmImpostos.setImpostos(impostos);
	}
	
	
	public void acaoBuscarCpf2() {
		String sCpf = this.tCpf.getText();

		try {
			String cpf = sCpf;

			Imposto i = this.getPdb().buscaPorCpf2(cpf);
			
			if (i != null) {
				
				List<Imposto> impostos = this.getPdb().buscaTodosCpf2(cpf);
				
				if (i.getCpf().equals(cpf)) {
					this.tmImpostos.setImpostos(impostos);
				} else {
					this.tmImpostos.setImpostos(null);	
				}
			} else {
				JOptionPane.showMessageDialog(this, "Nao foi encontrado!");
			}

			
		} catch (NumberFormatException nfe) {
			JOptionPane.showMessageDialog(this, "Nao foi possivel buscar");
			nfe.printStackTrace();
		}

	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(bBusca)) {
			this.acaoBuscar();
		
		}else if(e.getSource().equals(bBusca2)) {
			acaoBuscarCpf2();
		}else if(e.getSource().equals(bSair)) {
			acaoFechar();
		}
	}
	public void setPosicao() {
	    Dimension d = this.getDesktopPane().getSize();
	    this.setLocation((d.width - this.getSize().width) / 2, (d.height - this.getSize().height) / 2); 
	}

	
	

}

