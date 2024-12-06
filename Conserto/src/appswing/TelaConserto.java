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
import java.util.ArrayList;
import java.util.Arrays;
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

import modelo.Conserto;
import modelo.Defeito;
import modelo.Carro;
import regras_negocio.FachadaConserto;

public class TelaConserto {
	private JDialog frame;
	private JTable table;
	private JScrollPane scrollPane;
	private JButton btnBuscarPorID;
	private JButton btnCriar;
	private JButton btnLimpar;
	private JButton btnApagar;
	private JButton btnAtualizar;
	private JLabel label;
	private JLabel lblMensagemExibida;
	private JLabel lblData;
	private JLabel lblPlaca;
	private JLabel lblDefeitos;
	private JLabel lblIDConsertoBuscar;
	private JLabel lbl_IDConserto;
	private JTextField textField_IDBuscarConserto;
	private JTextField textField_DataConserto;
	private JTextField textField_PlacaConserto;
	private JTextField textField_DefeitosDoConserto;
	private JTextField textField_IDConserto;

	/**
	 * Create the application.
	 */
	public TelaConserto() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JDialog();
		frame.setModal(true); // janela modal

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
		frame.setTitle("Consertos");
		frame.setBounds(300, 120, 744, 428);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(21, 63, 685, 155);
		frame.getContentPane().add(scrollPane);

		table = new JTable() {
			// proibir alteracao de celulas
			public boolean isCellEditable(int rowIndex, int vColIndex) {
				return false;
			}
		};
		
		// evento de selecao de uma linha da tabela
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					if (table.getSelectedRow() >= 0) {
						// pegar o nome, data e outros dados da linha selecionada
						//String nome = (String) table.getValueAt(table.getSelectedRow(), 1);
						int id = (int) table.getValueAt(table.getSelectedRow(), 1);
						Conserto conserto = FachadaConserto.localizarConserto(id);
						String data = conserto.getData();
						String placa = conserto.getCarro().getPlaca();
						//String defeitos = conserto.getDefeitos().toString();
						textField_DataConserto.setText(data);
						textField_PlacaConserto.setText(placa);
						textField_DefeitosDoConserto.setText(String.join(",", conserto.getDefeitos().toString()));
						textField_IDConserto.setText("");
						label.setText("");
					}
				} catch (Exception erro) {
					label.setText(erro.getMessage());
				}

			}
		});

		table.setGridColor(Color.BLACK);
		table.setRequestFocusEnabled(false);
		table.setFocusable(false);
		table.setBackground(Color.WHITE);
		table.setFillsViewportHeight(true);
		table.setRowSelectionAllowed(true);
		table.setFont(new Font("Tahoma", Font.PLAIN, 14));
		scrollPane.setViewportView(table);
		table.setBorder(new LineBorder(new Color(0, 0, 0)));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setShowGrid(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		btnApagar = new JButton("Apagar");
		btnApagar.setToolTipText("Apagar Conserto e seus dados");
		btnApagar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (textField_DataConserto.getText().isEmpty()) {
						label.setText("Data vazia");
						return;
					}
					// pegar o nome na linha selecionada
					String idConserto = textField_IDConserto.getText();
					Object[] options = { "Confirmar", "Cancelar" };
					int escolha = JOptionPane.showOptionDialog(null,
							"Esta operação apagará o Conserto de id: " + idConserto, "Alerta",
							JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[1]);
					if (escolha == 0) {
						int idParaExcluir = Integer.parseInt(idConserto);
						FachadaConserto.excluirConserto(idParaExcluir);
						label.setText("Conserto Excluído!");
						listagem();
					} else
						label.setText("Exclusão Cancelada");

				} catch (Exception erro) {
					label.setText(erro.getMessage());
				}
			}
		});
		btnApagar.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnApagar.setBounds(169, 340, 74, 23);
		frame.getContentPane().add(btnApagar);

		label = new JLabel("");
		label.setForeground(Color.RED);
		label.setBounds(21, 372, 607, 14);
		frame.getContentPane().add(label);

		btnBuscarPorID = new JButton("Buscar por ID");
		btnBuscarPorID.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listagem();
			}
		});
		btnBuscarPorID.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnBuscarPorID.setBounds(282, 29, 149, 23);
		frame.getContentPane().add(btnBuscarPorID);

		textField_IDBuscarConserto = new JTextField();
		textField_IDBuscarConserto.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textField_IDBuscarConserto.setColumns(10);
		textField_IDBuscarConserto.setBounds(169, 30, 106, 20);
		frame.getContentPane().add(textField_IDBuscarConserto);

		lblMensagemExibida = new JLabel("Selecione para editar");
		lblMensagemExibida.setBounds(21, 216, 394, 14);
		frame.getContentPane().add(lblMensagemExibida);

		lblData = new JLabel("Data:");
		lblData.setHorizontalAlignment(SwingConstants.LEFT);
		lblData.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblData.setBounds(31, 239, 62, 14);
		frame.getContentPane().add(lblData);

		textField_DataConserto = new JTextField();
		textField_DataConserto.setFont(new Font("Dialog", Font.PLAIN, 12));
		textField_DataConserto.setColumns(10);
		textField_DataConserto.setBackground(Color.WHITE);
		textField_DataConserto.setBounds(101, 235, 165, 20);
		frame.getContentPane().add(textField_DataConserto);

		lblPlaca = new JLabel("Placa");
		lblPlaca.setHorizontalAlignment(SwingConstants.LEFT);
		lblPlaca.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblPlaca.setBounds(31, 264, 62, 14);
		frame.getContentPane().add(lblPlaca);

		textField_PlacaConserto = new JTextField();
		textField_PlacaConserto.setFont(new Font("Dialog", Font.PLAIN, 12));
		textField_PlacaConserto.setColumns(10);
		textField_PlacaConserto.setBounds(101, 260, 86, 20);
		frame.getContentPane().add(textField_PlacaConserto);

		lblDefeitos = new JLabel("Defeitos");
		lblDefeitos.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblDefeitos.setHorizontalAlignment(SwingConstants.LEFT);
		lblDefeitos.setBounds(31, 289, 62, 14);
		frame.getContentPane().add(lblDefeitos);

		textField_DefeitosDoConserto = new JTextField();
		textField_DefeitosDoConserto.setFont(new Font("Dialog", Font.PLAIN, 12));
		textField_DefeitosDoConserto.setColumns(10);
		textField_DefeitosDoConserto.setBounds(101, 284, 264, 20);
		frame.getContentPane().add(textField_DefeitosDoConserto);

		btnCriar = new JButton("Criar");
		btnCriar.setToolTipText("Cadastrar novo Conserto");
		btnCriar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (textField_DataConserto.getText().isEmpty()) {
						label.setText("Data vazia");
						return;
					}
					String data = textField_DataConserto.getText().trim();
					String placa = textField_PlacaConserto.getText().trim();
					//String[] defeitos = textField_DefeitosDoConserto.getText().trim().split(",");
					
					// TESTANDO, SE FUNCIONAR, APAGAR A LINHA ACIMA!
		            String defeitosText = textField_DefeitosDoConserto.getText().trim();
		            if (defeitosText.isEmpty()) {
		                label.setText("Defeitos vazios");
		                return;
		            }
		            
		            // Dividindo por vírgula
		            String[] defeitos = defeitosText.split(",");


					//FachadaConserto.criarConserto(data, placa, new ArrayList<>(Arrays.asList(defeitos)));
					String numero = textField_IDConserto.getText();
					if (!numero.isEmpty())
						FachadaConserto.criarConserto(data, placa, new ArrayList<>(Arrays.asList(defeitos)));
						//FachadaConserto.criarConserto(data, placa, defeitos);

					label.setText("Conserto criado com sucesso!");
					listagem();
				} catch (Exception ex) {
					label.setText("Erro: " + ex.getMessage());
				}
			}
		});
		btnCriar.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnCriar.setBounds(21, 340, 62, 23);
		frame.getContentPane().add(btnCriar);


		btnAtualizar = new JButton("Atualizar");
		btnAtualizar.setToolTipText("Atualizar Conserto");
		btnAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (textField_DataConserto.getText().trim().isEmpty()) {
						label.setText("Data vazia");
						return;
					}
					
					String data = textField_DataConserto.getText().trim();
					String placa = textField_PlacaConserto.getText().trim();
					String[] defeitos = textField_DefeitosDoConserto.getText().trim().split(",");

					//FachadaConserto.alterarConserto(data, placa, new ArrayList<>(Arrays.asList(defeitos)));
					String numero = textField_IDConserto.getText();
					if (!numero.isEmpty())
						FachadaConserto.criarConserto(data, placa, new ArrayList<>(Arrays.asList(defeitos)));
						//FachadaConserto.criarTelefone(data, numero);
					label.setText("Conserto alterado");
					listagem();
				} catch (Exception ex2) {
					label.setText(ex2.getMessage());
				}
			}
		});
		btnAtualizar.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnAtualizar.setBounds(82, 340, 87, 23);
		frame.getContentPane().add(btnAtualizar);

		lblIDConsertoBuscar = new JLabel("ID Conserto:");
		lblIDConsertoBuscar.setBounds(21, 32, 98, 14);
		frame.getContentPane().add(lblIDConsertoBuscar);

		btnLimpar = new JButton("Limpar");
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField_DataConserto.setText("");
				textField_PlacaConserto.setText("");
				textField_DefeitosDoConserto.setText("");
				textField_IDConserto.setText("");
			}
		});
		btnLimpar.setBounds(276, 234, 89, 23);
		frame.getContentPane().add(btnLimpar);

		lbl_IDConserto = new JLabel("ID Conserto");
		lbl_IDConserto.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_IDConserto.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lbl_IDConserto.setBounds(21, 313, 74, 14);
		frame.getContentPane().add(lbl_IDConserto);

		textField_IDConserto = new JTextField();
		textField_IDConserto.setFont(new Font("Dialog", Font.PLAIN, 12));
		textField_IDConserto.setColumns(10);
		textField_IDConserto.setBounds(101, 309, 86, 20);
		frame.getContentPane().add(textField_IDConserto);

		frame.setVisible(true);
	}


	public void listagem() {
		try {
			//String digitos = textField_IDBuscarConserto.getText();
			String digitos = textField_IDBuscarConserto.getText().trim();
			List<Conserto> lista = FachadaConserto.consultarConsertos(Integer.parseInt(textField_IDBuscarConserto.getText()));

			// objeto model contem todas as linhas e colunas da tabela
			DefaultTableModel model = new DefaultTableModel();
			table.setModel(model);

			// criar as colunas (0,1,2) da tabela
			model.addColumn("Id");
			model.addColumn("Carro");
			model.addColumn("Data");
			model.addColumn("Preço Final");
			model.addColumn("Defeitos");

			// criar as linhas da tabela
			String texto1, texto2;
			for (Conserto conserto : lista) {
				texto1 = String.join(",", String.valueOf(conserto.getPrecoFinal())); // concatena strings
				if (conserto.getDefeitos().size() > 0) {
					texto2 = "";
					for (Defeito defeito : conserto.getDefeitos())
						texto2 += defeito.getNome() + " ";
				} else
					texto2 = "Sem Defeitos";
				//adicionar linha no table
				model.addRow(new Object[] { conserto.getId(), conserto.getCarro().getPlaca(), conserto.getData(), texto1, texto2 });

			}
			lblMensagemExibida.setText("Resultados: " + lista.size() + " Consertos   -  Selecione uma linha para editar");

			// redimensionar a coluna 0,3 e 4
			table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); // desabilita
			table.getColumnModel().getColumn(0).setMaxWidth(40); // coluna id
			table.getColumnModel().getColumn(3).setMinWidth(100); // Preço
			table.getColumnModel().getColumn(4).setMinWidth(300); // Defeitos
			table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS); // desabilita

		} catch (Exception erro) {
			label.setText(erro.getMessage());
		}
	}

}

