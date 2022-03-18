package homeaccounts;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import homeaccounts.CadastraImpostos;
import homeaccounts.CadastraPessoas;
import homeaccounts.ConfirmaPagamentos;
import homeaccounts.ContratosAtivos;
import homeaccounts.ContratosPagos;
import homeaccounts.Lembretes;



public class Principal extends JFrame implements ActionListener{
	
	private JMenuBar barra;
	private JMenu mCadastrar;
	private JMenu mBusca;
	private JMenu mLembretes;
	private JMenuItem miAbrirJanela, miAbrirJanela2, miPagamento, miServicos, miLembretes, miContratosAtivos;
	private JDesktopPane central;
	private JLabel lmensagem;
	
	public Principal() {
		
		this.init();
	}
	
	
	public void init() {
		
		this.setTitle("Home Accounts");
		this.setSize(850,700);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.central = new JDesktopPane();
		
		this.lmensagem = new JLabel("");
		lmensagem.setIcon(new ImageIcon(Principal.class.getResource("/imagem/Capturar.JPG")));
		lmensagem.setBounds(200, 1, 1000, 600);
		this.central.add(lmensagem);

		this.barra = new JMenuBar();
		this.mCadastrar = new JMenu("Registros");
		
		this.miAbrirJanela = new JMenuItem("Cobrancas");
		this.miAbrirJanela.addActionListener(this);
		this.miAbrirJanela2 = new JMenuItem("Pessoas");
		this.miAbrirJanela2.addActionListener(this);
		this.miPagamento = new JMenuItem("Confirmar pagamentos");
		this.miPagamento.addActionListener(this);
		
		this.barra.add(mCadastrar);
		this.mCadastrar.add(miAbrirJanela2);
		this.mCadastrar.add(miAbrirJanela);
		this.mCadastrar.add(miPagamento);
		
		this.mBusca = new JMenu("Buscar");
		this.miServicos = new JMenuItem("Contratos pagos");
		this.miServicos.addActionListener(this);
		this.miContratosAtivos = new JMenuItem("Contratos ativos");
		this.miContratosAtivos.addActionListener(this);
		this.mBusca.add(miServicos);
		this.mBusca.add(miContratosAtivos);
		this.barra.add(mBusca);
		
		this.mLembretes = new JMenu("Lembretes");
		this.miLembretes = new JMenuItem("Revisar Venctos");
		this.mLembretes.add(miLembretes);
		this.miLembretes.addActionListener(this);
		this.barra.add(mLembretes);
		
		this.getContentPane().add(this.central);
		
		this.setJMenuBar(barra);
		setLocationRelativeTo(null);
		
		this.setVisible(true);
		ContratosAtivos jan5 = new ContratosAtivos();
		this.central.add(jan5);
		jan5.setVisible(true);
		jan5.acaoBuscar();
		jan5.setPosicao();

	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource().equals(miAbrirJanela)) {
			CadastraImpostos jan1 = new CadastraImpostos();
			this.central.add(jan1);
			jan1.setVisible(true);
			jan1.setPosicao();

			
		}
		if(e.getSource().equals(miAbrirJanela2)) {
			
			CadastraPessoas jan2 = new CadastraPessoas();
			this.central.add(jan2);
			jan2.setVisible(true);
			jan2.setPosicao();

		}
		if(e.getSource().equals(miServicos)) {
			ContratosPagos jan3 = new ContratosPagos();
			this.central.add(jan3);
			jan3.setVisible(true);
			jan3.setPosicao();
		}
		if(e.getSource().equals(miLembretes)) {
			System.out.println("WTF");
			Lembretes jan4 = new Lembretes();
			this.central.add(jan4);
			jan4.setVisible(true);
			jan4.setPosicao();
		}
		if(e.getSource().equals(miContratosAtivos)) {
			ContratosAtivos jan5 = new ContratosAtivos();
			this.central.add(jan5);
			jan5.setVisible(true);
			jan5.setPosicao();
		}
		if(e.getSource().equals(miPagamento)) {
			ConfirmaPagamentos jan6 = new ConfirmaPagamentos();
			this.central.add(jan6);
			jan6.setVisible(true);
			jan6.setPosicao();
			
			
		}
		 

	}
}
