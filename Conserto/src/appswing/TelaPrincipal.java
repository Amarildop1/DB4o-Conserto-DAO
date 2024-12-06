package appswing;
/**
 * IFPB - SI
 * Disciplina: Persistência de Objetos
 */

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.SwingConstants;

public class TelaPrincipal {
	private JFrame frame;
	private JMenu mnConserto;
	private JMenu mnCarro;
	private JMenu mnDefeito;
	private JMenu mnConsultas;
	private JLabel label;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaPrincipal window = new TelaPrincipal();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TelaPrincipal() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Oficina Conserto - Tema 08");
		frame.setBounds(350, 60, 650, 450);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);
		
		label = new JLabel("");
		label.setFont(new Font("Tahoma", Font.PLAIN, 26));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(0, 0, 444, 249);
		label.setText("Inicializando...");
		label.setBounds(0, 0, frame.getWidth(), frame.getHeight());
		ImageIcon imagem = new ImageIcon(getClass().getResource("/imagens/background.jpg"));
		imagem = new ImageIcon(imagem.getImage().getScaledInstance(label.getWidth(),label.getHeight(), Image.SCALE_DEFAULT));
		label.setIcon(imagem);
		frame.getContentPane().add(label);

		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		mnConserto = new JMenu("Conserto");
		mnConserto.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new TelaConserto();
			}
		});
		menuBar.add(mnConserto);
		
		mnCarro = new JMenu("Carro");
		mnCarro.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new TelaCarro();
			}
		});
		menuBar.add(mnCarro);

		mnDefeito = new JMenu("Defeito");
		mnDefeito.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new TelaDefeito();
			}
		});
		menuBar.add(mnDefeito);
		
		mnConsultas = new JMenu("Consultas");
		mnConsultas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new TelaConsulta();
			}
		});
		menuBar.add(mnConsultas);
	}
}

