package homeaccounts;

import java.awt.BorderLayout;
import java.awt.Color;
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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import Banco.ImpostoDB;
import Banco.PostgresImpostoDB;
import Banco.PostgresPessoasDB;
import Modelo.Imposto;
import Modelo.Pessoas;
import Banco.PessoaDB;
import TableModels.ImpostosTableModel;

public class ContratosAtivos extends JInternalFrame implements ActionListener{
	
	private static final long serialVersionUID = 1L;
	private ImpostoDB pdb;
	private PessoaDB pdb2;
	private Connection conn;
	private JPanel fundo, botoes, campo, labels;
	private JButton bBusca, bBusca2, bSair;
	private JTextField tCpf;
	private String cpf;
	private JLabel lTotal, lTotal2, total, total2, lSalario, salario, lAviso, lAviso2;
	
	
	
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
		
		ContratosAtivos lista = new ContratosAtivos();
		lista.init();
		
	}
	
	public ImpostoDB getPdb() {
		return pdb;
	}

	public PessoaDB getPdb2() {
		return pdb2;
	}

	public void setPdb2(PessoaDB pdb2) {
		this.pdb2 = pdb2;
	}

	public void setPdb(ImpostoDB pdb) {
		this.pdb = pdb;
	}
	
	public ContratosAtivos() {
		
		
		this.init();
		this.conn = this.abreConexao();
		
		this.setPdb(new PostgresImpostoDB(this.conn));
		this.setPdb2(new PostgresPessoasDB(this.conn));
	}
	
	public void init() {
		
		this.setTitle("Contratos ativos");
		
		this.fundo = new JPanel(new BorderLayout());
		this.botoes = new JPanel(new FlowLayout());
		this.campo = new JPanel(new GridLayout(1,2));
		this.labels = new JPanel(new GridLayout(2,3));


		this.bBusca = new JButton("Buscar todos");
		this.bBusca.addActionListener(this);
		this.bBusca2 = new JButton("Buscar por Cpf");
		this.bBusca2.addActionListener(this);
		this.tCpf = new JTextField(								);
		this.lSalario = new JLabel("Renda: ");
		this.bSair = new JButton("Sair");
		this.bSair.addActionListener(this);
		this.lTotal = new JLabel("Divida: ");

		this.lAviso = new JLabel("                 ATENCAO            ");
		this.lAviso2 = new JLabel("DIVIDA MAIOR QUE A RENDA!");

		this.salario = new JLabel("0");
		this.total = new JLabel("0");
		this.total2 = new JLabel("0");

		this.botoes.add(this.bBusca);	
		this.campo.add(this.bBusca2);
		this.botoes.add(this.bSair);
		this.campo.add(this.tCpf);
		this.campo.add(this.botoes, BorderLayout.NORTH);
		
		this.labels.add(this.lTotal);
		this.labels.add(this.total);
		this.labels.add(this.lAviso);
		this.labels.add(this.lSalario);
		this.labels.add(this.salario);
		this.labels.add(this.lAviso2);
		
		this.lAviso.setVisible(false);
		this.lAviso2.setVisible(false);

		
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
			}
			
		});
		
		
		this.tImpostos.setDefaultRenderer(Object.class, new ColorContratoAtivo());
		
		this.fundo.add(this.scroll, BorderLayout.NORTH);
		this.fundo.add(this.labels, BorderLayout.CENTER);
		this.fundo.add(this.campo, BorderLayout.SOUTH);
		this.getContentPane().add(this.fundo);
		
		this.setSize(800,550);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.labels.setVisible(true);
		this.lSalario.setVisible(false);
		this.salario.setVisible(false);
		total.setForeground(Color.BLUE);

		
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
		List<Imposto> impostos = this.getPdb().buscaTodos();
		double totalImpostos = 0d;

		for (Imposto imp : impostos){
		  totalImpostos += imp.getTotal();
		}


		total.setText(Double.toString(totalImpostos));

		this.tmImpostos.setImpostos(impostos);
	}
	
	
	public void acaoBuscarCpf() {
		String sCpf = this.tCpf.getText();
		

		try {
			String cpf = sCpf;

			Imposto i = this.getPdb().buscaPorCpf(cpf);
			Pessoas p = this.getPdb2().buscaPorCpf(cpf);
			
			
			if (i != null) {
				
				List<Imposto> impostos = this.getPdb().buscaTodosCpf(cpf);
				double totalImpostos = 0d;

				for (Imposto imp : impostos){
				  totalImpostos += imp.getTotal();
				}


				total.setText(Double.toString(totalImpostos));
				
				double s = p.getSalario();
				
				salario.setText(Double.toString(s));
				if (totalImpostos > s) {
					total.setForeground(Color.RED);
					salario.setForeground(Color.RED);
					this.lAviso.setVisible(true);
					this.lAviso2.setVisible(true);


				}else if (totalImpostos <= s) {
					total.setForeground(Color.BLUE);
					salario.setForeground(Color.BLUE);
					this.lAviso.setVisible(false);
					this.lAviso2.setVisible(false);
				}
				
				
				if (i.getCpf().equals(cpf)) {
					this.tmImpostos.setImpostos(impostos);
				} else {
					this.tmImpostos.setImpostos(null);	
				}
			} else {
				JOptionPane.showMessageDialog(this, " Nao foi encontrado !");
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
			this.labels.setVisible(true);
			this.salario.setVisible(false);
			this.lSalario.setVisible(false);
			this.lAviso.setVisible(false);
			this.lAviso2.setVisible(false);
			total.setForeground(Color.BLUE);



			
		}else if(e.getSource().equals(bBusca2)) {
			acaoBuscarCpf();
			this.labels.setVisible(true);
			this.lSalario.setVisible(true);
			this.salario.setVisible(true);
		}else if(e.getSource().equals(bSair)) {
			acaoFechar();
		}
	}


	public void setPosicao() {
		Dimension d = this.getDesktopPane().getSize();
	    this.setLocation((d.width - this.getSize().width) / 2, (d.height - this.getSize().height) / 2);
		
	}
	

}

