package appswing;
/**
 * IFPB - SI
 * Disciplina: Persistência de Objetos
 */

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import modelo.Defeito;
import regras_negocio.FachadaConserto;

public class TelaDefeito {
	private JDialog frame;
	private JTable table;
	private JScrollPane scrollPane;
	private JButton btnListar;
	private JButton btnApagar;
	private JLabel label;
	private JTextField textField;
	private JLabel lblIDDefeito;
	private JLabel lblNomeDefeito;
	private JTextField textField_NomeDefeito;
	private JTextField textField_IDDefeito;
	private JButton btnCriar;

	private JLabel label_3;
	private JLabel lblMensagemExibicao;
	private JButton btnAtualizar;
	private JLabel lblPrecoDefeito;
	private JTextField textField_PrecoDefeito;

	
	/**
	 * Create the application.
	 */
	public TelaDefeito() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JDialog();
		frame.setModal(true);		//janela modal
		
		frame.setTitle("Defeito");
		frame.setBounds(410, 120, 485, 476);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				FachadaConserto.inicializar();
				listagem();
			}
			@Override
			public void windowClosing(WindowEvent e) {
				FachadaConserto.finalizar();
			}
		});
		frame.setResizable(false);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(26, 44, 356, 152);
		frame.getContentPane().add(scrollPane);

		table = new JTable() {
			//proibir alteracao de celulas
			public boolean isCellEditable(int rowIndex, int vColIndex) {
				return false;
			}
		};
		
		//evento de selecao de uma linha da tabela
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					if (table.getSelectedRow() >= 0) {
						//pegar o nome selecionado
						String nome = (String) table.getValueAt( table.getSelectedRow(), 1);
						String preco = (String) table.getValueAt( table.getSelectedRow(), 2);
						textField_NomeDefeito.setText(nome);
						textField_IDDefeito.setText(preco);
						label.setText("");
					}
				}
				catch(Exception erro) {
					label.setText(erro.getMessage());
				}
			}
		});
		table.setGridColor(Color.BLACK);
		table.setRequestFocusEnabled(false);
		table.setFocusable(false);
		table.setBackground(new Color(211, 211, 211));
		table.setFillsViewportHeight(true);
		table.setRowSelectionAllowed(true);
		table.setFont(new Font("Tahoma", Font.PLAIN, 12));
		scrollPane.setViewportView(table);
		table.setBorder(new LineBorder(new Color(0, 0, 0)));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setShowGrid(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		btnApagar = new JButton("Apagar");
		btnApagar.setToolTipText("remover o defeito");
		btnApagar.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnApagar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(textField_IDDefeito.getText().isEmpty()) {
						label.setText("Id vazio");
						return;
					}
					//String numero = textField_IDDefeito.getText();
					String nomeDefeito = textField_NomeDefeito.getText();
					//confirmação
					Object[] options = { "Confirmar", "Cancelar" };
					int escolha = JOptionPane.showOptionDialog(null, "Confirma exclusão do defeito "+ nomeDefeito, "Alerta",
							JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[1]);
					if(escolha == 0) {
						FachadaConserto.excluirDefeito(nomeDefeito);
						label.setText("exclusão realizada");
						listagem();
					}
				}
				catch(Exception erro) {
					label.setText(erro.getMessage());
				}
			}
		});
		btnApagar.setBounds(221, 351, 95, 23);
		frame.getContentPane().add(btnApagar);

		label = new JLabel("");
		label.setForeground(Color.RED);
		label.setBounds(26, 309, 326, 14);
		frame.getContentPane().add(label);

		btnListar = new JButton("Listar");
		btnListar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listagem();
			}
		});
		btnListar.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnListar.setBounds(204, 10, 112, 23);
		frame.getContentPane().add(btnListar);

		textField = new JTextField();
		textField.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textField.setColumns(10);
		textField.setBounds(78, 11, 86, 20);
		frame.getContentPane().add(textField);

		lblIDDefeito = new JLabel("ID Defeito:");
		lblIDDefeito.setHorizontalAlignment(SwingConstants.LEFT);
		lblIDDefeito.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblIDDefeito.setBounds(26, 225, 71, 14);
		frame.getContentPane().add(lblIDDefeito);

		lblNomeDefeito = new JLabel("Nome:");
		lblNomeDefeito.setHorizontalAlignment(SwingConstants.LEFT);
		lblNomeDefeito.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNomeDefeito.setBounds(26, 255, 71, 14);
		frame.getContentPane().add(lblNomeDefeito);

		label_3 = new JLabel("Numero:");
		label_3.setBounds(26, 15, 57, 14);
		frame.getContentPane().add(label_3);

		lblMensagemExibicao = new JLabel("Selecione um defeito");
		lblMensagemExibicao.setBounds(26, 194, 336, 23);
		frame.getContentPane().add(lblMensagemExibicao);

		textField_NomeDefeito = new JTextField();
		textField_NomeDefeito.setColumns(10);
		textField_NomeDefeito.setBounds(97, 253, 244, 20);
		frame.getContentPane().add(textField_NomeDefeito);

		textField_IDDefeito = new JTextField();
		textField_IDDefeito.setColumns(10);
		textField_IDDefeito.setBounds(97, 223, 47, 20);
		frame.getContentPane().add(textField_IDDefeito);

		btnCriar = new JButton("Criar");
		btnCriar.setToolTipText("adicionar novo defeito");
		btnCriar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(textField_NomeDefeito.getText().isEmpty()) {
						label.setText("nome vazio");
						return;
					}
					String nome = textField_NomeDefeito.getText();
					double preco = Double.parseDouble(textField_PrecoDefeito.getText());
					FachadaConserto.criarDefeito(nome, preco);
					label.setText("telefone criado");
					listagem();
				}
				catch(Exception ex) {
					label.setText(ex.getMessage());
				}
			}
		});
		btnCriar.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnCriar.setBounds(26, 351, 77, 23);
		frame.getContentPane().add(btnCriar);

		btnAtualizar = new JButton("Atualizar");
		btnAtualizar.setToolTipText("alterar o defeito");
		btnAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(textField_NomeDefeito.getText().isEmpty()) {
						label.setText("nome vazio");
						return;
					}
					String nomeDefeito = textField_NomeDefeito.getText();
					String precoDefeito = textField_PrecoDefeito.getText();
					FachadaConserto.alterarNomeDefeito(nomeDefeito, precoDefeito);
					label.setText("Defeito atualizado");
					listagem();
				}
				catch(Exception ex) {
					label.setText(ex.getMessage());
				}
			}
		});
		btnAtualizar.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnAtualizar.setBounds(113, 351, 95, 23);
		frame.getContentPane().add(btnAtualizar);

		lblPrecoDefeito = new JLabel("Pre\u00E7o:");
		lblPrecoDefeito.setHorizontalAlignment(SwingConstants.LEFT);
		lblPrecoDefeito.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblPrecoDefeito.setBounds(26, 284, 57, 14);
		frame.getContentPane().add(lblPrecoDefeito);

		textField_PrecoDefeito = new JTextField();
		textField_PrecoDefeito.setColumns(10);
		textField_PrecoDefeito.setBounds(97, 280, 86, 20);
		frame.getContentPane().add(textField_PrecoDefeito);

		frame.setVisible(true);
	}

	public void listagem () {
		try{
			List<Defeito> lista = FachadaConserto.consultarDefeito(textField.getText());
			DefaultTableModel model = new DefaultTableModel();
			table.setModel(model);
			
			model.addColumn("Id");
			model.addColumn("Nome");
			model.addColumn("Preço");

			for(Defeito defeito : lista) {
				if(defeito.getNome() != null)
					model.addRow(new Object[]{defeito.getId(),  defeito.getNome(), defeito.getPreco() });
				else
					model.addRow(new Object[]{defeito.getId(),  defeito.getNome(),  "desconhecido"});
			}
			lblMensagemExibicao.setText("Resultados: "+lista.size()+ " Defeitos  -  Selecione uma linha para editar");
			
			// redimensionar a coluna 0
			table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); // desabilita
			table.getColumnModel().getColumn(0).setMaxWidth(40); // coluna id
			table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS); // desabilita
		}
		catch(Exception erro){
			label.setText(erro.getMessage());
		}
	}
}

