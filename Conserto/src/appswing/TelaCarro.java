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

import modelo.Carro;
import modelo.Conserto;
import modelo.Defeito;
import regras_negocio.FachadaConserto;

public class TelaCarro {
	private JDialog frame;
	private JTable table;
	private JScrollPane scrollPane;
	private JButton btnBuscarPorPlaca;
	private JButton btnCriar;
	private JButton btnLimpar;
	private JButton btnApagar;
	private JButton btnAtualizar;
	private JLabel label;
	private JLabel lblMensagemExibida;
	private JLabel lblPlaca;
	private JLabel lblProprietario;
	private JLabel lblDefeitos;
	private JLabel lblBuscarPorPlaca;
	private JLabel lblCPF;
	private JTextField textField_PlacaParaBuscar;
	private JTextField textField_Placa;
	private JTextField textField_Proprietario;
	private JTextField textField_Defeitos;
	private JTextField textField_CPF;

	/**
	 * Create the application.
	 */
	public TelaCarro() {
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
		frame.setTitle("Carros");
		frame.setBounds(300, 120, 744, 428);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(21, 63, 685, 155);
		frame.getContentPane().add(scrollPane);

		table = new JTable();
		// evento de selecao de uma linha da tabela
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					if (table.getSelectedRow() >= 0) {
						// pegar o nome, data e outros dados da linha selecionada
						String nome = (String) table.getValueAt(table.getSelectedRow(), 0);
						Carro carro = FachadaConserto.localizarCarro(nome);
						String placa = carro.getPlaca();
						String proprietario = carro.getProprietario();
						String cpf = carro.getCpf() + "";
						textField_Placa.setText(placa);
						textField_Proprietario.setText(proprietario);
						textField_Defeitos.setText(String.join(",", carro.getConsertos().toString()));
						textField_CPF.setText(cpf);
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
		btnApagar.setToolTipText("Apagar Carro e seus dados");
		btnApagar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (textField_Placa.getText().isEmpty()) {
						label.setText("Placa vazia");
						return;
					}
					// pegar o nome na linha selecionada
					String placa = textField_Placa.getText();
					Object[] options = { "Confirmar", "Cancelar" };
					int escolha = JOptionPane.showOptionDialog(null,
							"Esta operação apagará o Carro de placa: " + placa, "Alerta",
							JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[1]);
					if (escolha == 0) {
						FachadaConserto.excluirCarro(placa);
						label.setText("Carro Excluído!");
						listagem();
					} else
						label.setText("Exclusão Cancelada!");

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

		btnBuscarPorPlaca = new JButton("Buscar por Placa");
		btnBuscarPorPlaca.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listagem();
			}
		});
		btnBuscarPorPlaca.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnBuscarPorPlaca.setBounds(175, 27, 149, 23);
		frame.getContentPane().add(btnBuscarPorPlaca);

		textField_PlacaParaBuscar = new JTextField();
		textField_PlacaParaBuscar.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textField_PlacaParaBuscar.setColumns(10);
		textField_PlacaParaBuscar.setBounds(62, 28, 106, 20);
		frame.getContentPane().add(textField_PlacaParaBuscar);

		lblMensagemExibida = new JLabel("Selecione um Carro para editar");
		lblMensagemExibida.setBounds(21, 216, 394, 14);
		frame.getContentPane().add(lblMensagemExibida);

		lblPlaca = new JLabel("Placa:");
		lblPlaca.setHorizontalAlignment(SwingConstants.LEFT);
		lblPlaca.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblPlaca.setBounds(31, 239, 62, 14);
		frame.getContentPane().add(lblPlaca);

		textField_Placa = new JTextField();
		textField_Placa.setFont(new Font("Dialog", Font.PLAIN, 12));
		textField_Placa.setColumns(10);
		textField_Placa.setBackground(Color.WHITE);
		textField_Placa.setBounds(101, 235, 165, 20);
		frame.getContentPane().add(textField_Placa);

		lblProprietario = new JLabel("Propriet\u00E1rio:");
		lblProprietario.setHorizontalAlignment(SwingConstants.LEFT);
		lblProprietario.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblProprietario.setBounds(31, 264, 62, 14);
		frame.getContentPane().add(lblProprietario);

		textField_Proprietario = new JTextField();
		textField_Proprietario.setFont(new Font("Dialog", Font.PLAIN, 12));
		textField_Proprietario.setColumns(10);
		textField_Proprietario.setBounds(101, 260, 86, 20);
		frame.getContentPane().add(textField_Proprietario);

		lblDefeitos = new JLabel("Defeitos:");
		lblDefeitos.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblDefeitos.setHorizontalAlignment(SwingConstants.LEFT);
		lblDefeitos.setBounds(31, 289, 62, 14);
		frame.getContentPane().add(lblDefeitos);

		textField_Defeitos = new JTextField();
		textField_Defeitos.setFont(new Font("Dialog", Font.PLAIN, 12));
		textField_Defeitos.setColumns(10);
		textField_Defeitos.setBounds(101, 284, 264, 20);
		frame.getContentPane().add(textField_Defeitos);


		btnCriar = new JButton("Criar");
		btnCriar.setToolTipText("Cadastrar novo Carro");
		btnCriar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					// Campos obrigatórios
					if (textField_Placa.getText().isEmpty()) {
						label.setText("Erro: A Placa não pode ser vazia.");
						return;
					}
					if (textField_Proprietario.getText().isEmpty()) {
						label.setText("Erro: O Proprietário não pode ser vazio.");
						return;
					}
					if (textField_CPF.getText().isEmpty()) {
						label.setText("Erro: O CPF não pode ser vazio.");
						return;
					}
					// Formatação
					String placa = textField_Placa.getText().trim();
					String proprietario = textField_Proprietario.getText().trim();
					String cpf = textField_CPF.getText().trim();

					FachadaConserto.criarCarro(placa, cpf, proprietario);

					// Sucesso
					label.setText("Carro criado com sucesso!");

					// Atualização da listagem
					listagem();
				} catch (NumberFormatException nfe) {
					label.setText("Erro: Formato numérico inválido em algum campo.");
				} catch (Exception ex) {
					label.setText("Erro: " + ex.getMessage());
				}
			}
		});
		btnCriar.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnCriar.setBounds(21, 340, 62, 23);
		frame.getContentPane().add(btnCriar);

		btnAtualizar = new JButton("Atualizar");
		btnAtualizar.setToolTipText("Atualizar Carro ");
		btnAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					// Validação dos campos obrigatórios
					if (textField_Placa.getText().trim().isEmpty()) {
						label.setText("Erro: A Placa não pode ser vazia.");
						return;
					}
					if (textField_Proprietario.getText().trim().isEmpty()) {
						label.setText("Erro: O Proprietário não pode ser vazio.");
						return;
					}
					if (textField_CPF.getText().trim().isEmpty()) {
						label.setText("Erro: O CPF não pode ser vazio.");
						return;
					}

					// Captura dos dados
					String placa = textField_Placa.getText().trim();
					String proprietario = textField_Proprietario.getText().trim();
					String cpf = textField_CPF.getText().trim();

					// Método da Fachada
					FachadaConserto.alterarCarro(placa, cpf, proprietario);

					label.setText("Carro atualizado com sucesso!");

					// Atualização da listagem
					listagem();
				} catch (NumberFormatException nfe) {
					label.setText("Erro: Formato numérico inválido em algum campo.");
				} catch (Exception ex) {
					label.setText("Erro: " + ex.getMessage());
				}
			}


		});
		btnAtualizar.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnAtualizar.setBounds(82, 340, 87, 23);
		frame.getContentPane().add(btnAtualizar);


		lblBuscarPorPlaca = new JLabel("Placa:");
		lblBuscarPorPlaca.setBounds(21, 32, 46, 14);
		frame.getContentPane().add(lblBuscarPorPlaca);


		btnLimpar = new JButton("Limpar");
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField_Placa.setText("");
				textField_Proprietario.setText("");
				textField_Defeitos.setText("");
				textField_CPF.setText("");
				
				label.setText("Campos limpos com sucesso!");
			}
		});
		btnLimpar.setBounds(276, 234, 89, 23);
		frame.getContentPane().add(btnLimpar);

		lblCPF = new JLabel("CPF:");
		lblCPF.setHorizontalAlignment(SwingConstants.LEFT);
		lblCPF.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblCPF.setBounds(31, 313, 64, 14);
		frame.getContentPane().add(lblCPF);

		textField_CPF = new JTextField();
		textField_CPF.setFont(new Font("Dialog", Font.PLAIN, 12));
		textField_CPF.setColumns(10);
		textField_CPF.setBounds(101, 309, 86, 20);
		frame.getContentPane().add(textField_CPF);

		frame.setVisible(true);
	}


	public void listagem() {
		try {
			List<Carro> lista = FachadaConserto.listarCarros();

			// objeto model contem todas as linhas e colunas da tabela
			DefaultTableModel model = new DefaultTableModel();
			table.setModel(model);

			// criar as colunas (0,1,2) da tabela
			model.addColumn("ID");
			model.addColumn("Placa");
			model.addColumn("CPF");
			model.addColumn("Proprietário");
			model.addColumn("Consertos");

			// criar as linhas da tabela
			String texto1, texto2;
			for (Carro carro : lista) {
				texto1 = String.join(",", carro.getProprietario()); // concatena strings

				if (carro.getConsertos().size() > 0) {
					texto2 = "";
					for (Conserto conserto : carro.getConsertos())
						texto2 += conserto.getDefeitos() + " ";
				} else
					texto2 = "Sem Consertos";

				model.addRow( // Se adicionar carro.getId() na linha abaixo, dá erro ao clicar na linha da tabela, class java.lang.Integer cannot be cst to class !
						new Object[] { carro.getPlaca(), carro.getPlaca(), carro.getCpf(), texto1, texto2 });

			}
			lblMensagemExibida.setText("Resultados: " + lista.size() + " Carros   -  selecione uma linha para editar");

			// redimensionar a coluna 3 e 4
			table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); // desabilita
			table.getColumnModel().getColumn(3).setMinWidth(200); // Proprietário
			table.getColumnModel().getColumn(4).setMinWidth(200); // Consertos
			table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS); // desabilita

		} catch (Exception erro) {
			label.setText(erro.getMessage());
		}
	}
}

