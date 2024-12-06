package regras_negocio;
/**
 * IFPB - SI
 * Disciplina: Persistência de Objetos
 */

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import daodb4o.DAO;
import daodb4o.DAOCarro;
import daodb4o.DAOConserto;
import daodb4o.DAODefeito;

import modelo.Carro;
import modelo.Conserto;
import modelo.Defeito;

public class FachadaConserto {
	private FachadaConserto() {}

	private static DAOCarro daocarro = new DAOCarro();
	private static DAOConserto daoconserto = new DAOConserto();
	private static DAODefeito daodefeito = new DAODefeito();

	public static void inicializar() {
		DAO.open();
	}

	public static void finalizar() {
		DAO.close();
	}

						// ********** TUDO SOBRE CARRO ABAIXO **********
	/**
	 * Método para criar Carro
	 * 
	 * @param placa, cpf, proprietario
	 * 
	 * @return void
	 * 
	 * */
	public static void criarCarro(String placa, String cpf, String proprietario) throws Exception {

		DAO.begin();
		
		placa = placa.trim();
		cpf = cpf.trim();
		proprietario = proprietario.trim();
		
		Carro c = daocarro.read(placa);
		if (c != null) {
			DAO.rollback();
			throw new Exception("Problema ao tentar Criar Carro. Placa " + placa + " já existe.");
		}
		
		String regex = "^[A-Z]{3}-\\d{4}$";
		if (!placa.matches(regex)) {
			DAO.rollback();
			throw new Exception("Problema ao tentar Criar Carro. Placa " + placa + " fora do padrão permitido.");
		}

		if (!cpf.matches("\\d{11}")) {
			DAO.rollback();
			throw new Exception("Problema ao tentar Criar Carro. O CPF deve ser numérico e ter exatamente 11 dígitos.");
		}
		
		c = new Carro(placa, cpf, proprietario);
		daocarro.create(c);
		
		DAO.commit();

	} // Fim método criarCarro();


	/**
	 * Método para localizar Carro
	 * 
	 * @param placa
	 * 
	 * @return Carro
	 * 
	 * */
	public static Carro localizarCarro(String placa) throws Exception {
		placa = placa.trim();
		Carro c = daocarro.read(placa);
		if (c == null) {
			throw new Exception("Carro com placa " + placa + " inexistente.");
		}
		return c;
	}



	//altera o cpf e nome do proprietário
	/**
	 * Método alterarCarro(String placa, String cpf, String proprietario): Altera o CPF e o Proprietário.
	 * 
	 * @param placa, cpf, proprietario
	 * 
	 * @return void
	 * 
	 * */
	public static void alterarCarro(String placa, String cpf, String proprietario) throws Exception {
		DAO.begin();
		
		placa = placa.trim();
		cpf = cpf.trim();
		proprietario = proprietario.trim();
		
		Carro c = daocarro.read(placa);
		if (c == null) {
			DAO.rollback();
			throw new Exception("Problema ao tentar alterar Carro. Carro com placa " + placa + " inexistente.");
		}
		
		if (!cpf.matches("\\d{11}")) {
			DAO.rollback();
			throw new Exception("Problema ao tentar alterar Carro. O CPF deve ser numérico e ter exatamente 11 dígitos.");
		}
		
		c.setCpf(cpf);
		c.setProprietario(proprietario);
		daocarro.update(c);
		
		DAO.commit();

	} // Fim método alterarCarro()


	//carro
	/**
	 * 
	 * Método alterarPlaca: Altera para placa nova após pesquisar pela placa atual.
	 * 
	 * @param placa, novaplaca
	 * 
	 * @return void
	 * 
	 * */
	public static void alterarPlaca(String placa, String novaplaca) throws Exception {
		DAO.begin();
		
		placa = placa.trim();
		novaplaca = novaplaca.trim();
		Carro c = daocarro.read(placa); // usando chave primaria
		
		if (c == null) {
			DAO.rollback();
			throw new Exception("Problema ao tentar alterar Placa. Placa inexistente: " + placa);
		}
		
		c.setPlaca(novaplaca);
		daocarro.update(c);
		
		DAO.commit();
	}


	/**
	 * 
	 * Método excluirCarro(String placa)
	 * 
	 * @param placa
	 *
	 * @return void
	 * 
	 * */
	public static void excluirCarro(String placa) throws Exception {
		DAO.begin();
		
		placa = placa.trim();
		
		Carro c = daocarro.read(placa);
		if (c == null) {
			DAO.rollback();
			throw new Exception("Problema ao tentar excluir Carro. Carro com Placa " + placa + " inexistente");
		}

		// desligar o carro de seus consertos orfaos e apaga-los do banco
		for (Conserto conserto : c.getConsertos()) {
			daoconserto.delete(conserto); // deletar o conserto orfao
		}

		daocarro.delete(c);
		
		DAO.commit();
	}
						// ********** TUDO SOBRE CARRO ACIMA **********




						// ********** TUDO SOBRE CONSERTO ABAIXO **********
	/**
	 * 
	 * Método criarConserto(String data, String placa, List<String> defeitos)
	 * 
	 * @param data, placa, defeitos
	 * 
	 * @return void
	 * 
	 * */
	public static void criarConserto(String data, String placa, List<String> defeitos) throws Exception {
		DAO.begin();
		
		data = data.trim();
		placa = placa.trim();
		
		try {
			LocalDate.parse(data, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		} catch (DateTimeParseException e) {
			DAO.rollback();
			throw new Exception("Problema ao tentar criar Conserto. Formato de data inválido: " + data);
		}
		
		Carro c = daocarro.read(placa);
		if (c == null) {
			DAO.rollback();
			throw new Exception("Problema ao tentar criar Conserto. Carro com placa " + placa + " inexistente");
		}
		
	    Conserto conserto = new Conserto(data);
	    conserto.setCarro(c);

	    for (String defeito : defeitos) {
	    	defeito = defeito.trim();
	        Defeito d = daodefeito.read(defeito);
	        
	        if (d == null) {
	            DAO.rollback();
	            throw new Exception("Problema ao tentar criar Conserto. Defeito não encontrado: " + defeito);
	        }
	        conserto.adicionar(d);
	    }
	    
	    conserto.calcularPrecoFinal();
	    c.adicionar(conserto);
	    daoconserto.create(conserto);
	    
		DAO.commit();

	} // Fim método criarConserto()


	/**
	 * Método localizarConserto(int id): Localiza o conserto pelo id informado.
	 * 
	 * @param id
	 * 
	 * @return Conserto
	 * 
	 * */
	public static Conserto localizarConserto(int id) throws Exception {
		Conserto c = daoconserto.read(id);
		if (c == null) {
			throw new Exception("Conserto inexistente: " + id);
		}
		return c;
	}



	//altera a data do conserto e a placa do carro correspondente
	/**
	 * Método alterarConserto(int id, String data, String placa): altera o conserto localizado pelo id informado.
	 * 
	 * @param id, data, placa
	 * 
	 * @return void
	 * 
	 * */
	public static void alterarConserto(int id, String data, String placa) throws Exception {
		DAO.begin();
		
		data = data.trim();
		placa = placa.trim();
		
		Conserto conserto = daoconserto.read(id);
		if (conserto == null) {
			DAO.rollback();
			throw new Exception("Problema ao tentar alterar Conserto. Conserto inexistente: " + id);
		}

		if (data != null) {
			try {
				LocalDate.parse(data, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
				conserto.setData(data);
			} catch (DateTimeParseException e) {
				DAO.rollback();
				throw new Exception("Problema ao tentar alterar Conserto. Formato de Data inválido: " + data);
			}
		}
		
		Carro c = daocarro.read(placa);
		if (c == null) {
			DAO.rollback();
			throw new Exception("Problema ao tentar alterar Conserto. Carro com placa " + placa + " inexistente");
		}
		
		conserto.setCarro(c);

		daoconserto.update(conserto);
		
		DAO.commit();

	} // Fim método alterarConserto()


	//conserto
	/**
	 * Método alterarData(int id, String data): altera a data do conserto localizado pelo id informado.
	 * 
	 * @param id, data
	 * 
	 * @return void
	 * 
	 * */
	public static void alterarData(int id, String data) throws Exception {
		DAO.begin();
		
		data = data.trim();
		
		Conserto conserto = daoconserto.read(id);
		if (conserto == null) {
			DAO.rollback();
			throw new Exception("Problema ao tentar alterar a Data. Conserto inexistente: " + id);
		}

		if (data != null) {
			try {
				LocalDate.parse(data, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
				conserto.setData(data);
			} catch (DateTimeParseException e) {
				DAO.rollback();
				throw new Exception("Problema ao tentar alterar a Data. Formato de Data inválido: " + data);
			}
		}
		daoconserto.update(conserto);
		DAO.commit();
	}


	/**
	 * Método excluirConserto(int id)
	 * 
	 * @param id
	 * 
	 * @return void
	 * 
	 * */
	public static void excluirConserto(int id) throws Exception {
		DAO.begin();
		
		Conserto conserto = daoconserto.read(id);
		if (conserto == null) {
			DAO.rollback();
			throw new Exception("Problema ao tentar excluir Conserto. Conserto com id " + id + " inexistente");
		}

		Carro c = conserto.getCarro();
		c.remover(conserto);
		conserto.setCarro(null);
		daocarro.update(c);

		daoconserto.delete(conserto);
		
		DAO.commit();
	}
	
	
	
	//conserto
	/**
	 * Método adicionarDefeitos(int id, List<String> defeitos): Adiciona defeitos ao conserto localizado pelo id informado.
	 * 
	 * @param id, defeitos
	 * 
	 * @return void
	 * 
	 * */
	public static void adicionarDefeitos(int id, List<String> defeitos) throws Exception {
		DAO.begin();
		
		Conserto conserto = daoconserto.read(id);
		if (conserto == null) {
			DAO.rollback();
			throw new Exception("Problema ao tentar adicionar Defeito. Conserto com id " + id + " inexistente");
		}
		
		for (String defeito : defeitos) {
	    	defeito = defeito.trim();
	        Defeito d = daodefeito.read(defeito);
	        
	        if (d == null) {
	            DAO.rollback();
	            throw new Exception("Problema ao tentar adicionar Defeito. Defeito não encontrado: " + defeito);
	        }
	        conserto.adicionar(d);
	    }
		
		conserto.calcularPrecoFinal();
		daoconserto.update(conserto);
		DAO.commit();

	} // Fim método adicionarDefeitos()

	
	//conserto
	/**
	 * Método removerDefeito(int id, List<String> defeitos): remove defeitos do conserto localizado pelo id informado.
	 * 
	 * @param id, defeitos
	 * 
	 * @return void
	 * 
	 * */
	public static void removerDefeito(int id, List<String> defeitos) throws Exception {
		DAO.begin();
		
		Conserto conserto = daoconserto.read(id);
		if (conserto == null) {
			DAO.rollback();
			throw new Exception("Problema ao tentar remover Defeito. Conserto com id " + id + " inexistente");
		}
		
		for (String defeito : defeitos) {
	    	defeito = defeito.trim();
	        Defeito d = daodefeito.read(defeito);
	        
	        if (d == null) {
	            DAO.rollback();
	            throw new Exception("Problema ao tentar remover Defeito. Defeito não encontrado: " + defeito);
	        }
	        conserto.remover(d);
	    }
		
		conserto.calcularPrecoFinal();
		daoconserto.update(conserto);
		
		DAO.commit();

	} // Fim método removerDefeito()
						// ********** TUDO SOBRE CONSERTO ACIMA **********
	


	
						// ********** TUDO SOBRE DEFEITO ABAIXO **********
	/**
	 * Método criarDefeito(String nome, double preco)
	 * 
	 * @param nome, preco
	 * 
	 * @return void
	 * 
	 * */
	public static void criarDefeito(String nome, double preco) throws Exception {
		DAO.begin();
		
		nome = nome.trim();
		
		Defeito d = daodefeito.read(nome);
		if (d != null) {
			DAO.rollback();
			throw new Exception("Problema ao tentar criar Defeito. Defeito \'" + nome + "\' já existe.");
		}
		
		d = new Defeito(nome, preco);
		daodefeito.create(d);
		
		DAO.commit();
	}


	/**
	 * Método localizarDefeito(String nome): Localiza um defeito pelo nome exato.
	 * 
	 * @param nome
	 * 
	 * @return Defeito
	 * 
	 * */
	public static Defeito localizarDefeito(String nome) throws Exception {
		nome = nome.trim();
		Defeito d = daodefeito.read(nome);
		if (d == null) {
			throw new Exception("Defeito inexistente: " + nome);
		}
		return d;
	}


	/**
	 * Método alterarNomeDefeito(String nome, String novoNome): Busca o Defeito pelo nome e altera para o novoNome.
	 * 
	 * @param nome, novoNome
	 * 
	 * @return void
	 * 
	 * */
	public static void alterarNomeDefeito(String nome, String novoNome) throws Exception {
		DAO.begin();
		
		nome = nome.trim();
		novoNome = novoNome.trim();
		
		Defeito d = daodefeito.read(nome);
		if (d == null) {
			DAO.rollback();
			throw new Exception("Problema ao tentar alterar nome do Defeito. Defeito inexistente: " + nome);
		}
		
		d.setNome(novoNome);
		daodefeito.update(d);
		
		DAO.commit();
	}


	/**
	 * Método alterarPrecoDefeito(String nome, double preco): Localiza um defeito pelo nome e altera seu preço.
	 * 
	 * @param nome, preco
	 * 
	 * @return void
	 * 
	 * */
	public static void alterarPrecoDefeito(String nome, double preco) throws Exception {
		DAO.begin();
		
		nome = nome.trim();
		
		Defeito d = daodefeito.read(nome);
		if (d == null) {
			DAO.rollback();
			throw new Exception("Problema ao tentar alterar Preço do Defeito. Defeito inexistente:" + nome);
		}
		
		d.setPreco(preco);
		daodefeito.update(d);
		
		DAO.commit();
	}


	/**
	 * Método excluirDefeito(String nome): Localiza um defeito pelo nome e o exclui.
	 * 
	 * @param nome
	 * 
	 * @return void
	 * 
	 * */
	public static void excluirDefeito(String nome) throws Exception {
		DAO.begin();
		
		nome = nome.trim();
		
		Defeito d = daodefeito.read(nome);
		if (d == null) {
			DAO.rollback();
			throw new Exception("Problema ao tentar excluir Defeito. Defeito inexistente: " + nome);
		}

		//remove a associação do defeito aos consertos
		 List<Conserto> consertosAssociadosAoDefeito = daoconserto.readDefeito(nome);
		    for (Conserto conserto : consertosAssociadosAoDefeito) {
		        conserto.remover(d);
		        conserto.calcularPrecoFinal();
		        daoconserto.update(conserto);
		    }
		
		daodefeito.delete(d);
		
		DAO.commit();
	}
						// ********** TUDO SOBRE DEFEITO ACIMA **********

	

	public static List<Carro> listarCarros() {
		List<Carro> result = daocarro.readAll();
		return result;
	}

	public static List<Conserto> listarConsertos() {
		List<Conserto> result = daoconserto.readAll();
		return result;
	}

	public static List<Defeito> listarDefeitos() {
		List<Defeito> result = daodefeito.readAll();
		return result;
	}

	/**********************************************************
	 * 
	 * CONSULTAS IMPLEMENTADAS NOS DAO
	 * 
	 **********************************************************/

	public static List<Carro> consultarCarros(String caracteres) {
		List<Carro> result;
		if (caracteres.isEmpty())
			result = daocarro.readAll();
		else
			result = daocarro.readAll(caracteres);
		return result;
	}


	public static List<Conserto> consultarConsertos(int digitos) {
		List<Conserto> result;
		result = daoconserto.readAll(digitos);
		return result;
	}


	public static List<Defeito> consultarDefeito(String caracteres) {
		List<Defeito> result;
		if (caracteres.isEmpty())
			result = daodefeito.readAll();
		else
			result = daodefeito.readAll(caracteres);
		return result;
	}

	/**
	 * Método consultarDataConserto(String data): Para exibir todos os Consertos da data informada.
	 * 
	 * @param data
	 * 
	 * @return List<Conserto>
	 * 
	 * */
	public static List<Conserto> consultarDataConserto(String data) {
		List<Conserto> result;
		result = daoconserto.readByData(data);
		return result;
	}


	/**
	 * Método consultarCarrosComDefeitoX(String caracteres): Para exibir os Carros com o defeito informado.
	 * 
	 * @param caracteres
	 * 
	 * @return List<Carro>
	 * 
	 * */
	public static List<Carro> consultarCarrosComDefeitoX(String caracteres) {
		List<Carro> result;
		result = daocarro.readByDefeito(caracteres);
		return result;
	}


	/**
	 * Método consultarCarrosComMaisQueNConserto(int n): Para exibir Carros com mais que "n" Consertos.
	 * 
	 * @param n
	 * 
	 * @return List<Carro>
	 * 
	 * */
	public static List<Carro> consultarCarrosComMaisQueNConserto(int n) {
		List<Carro> result;
		DAO.begin();
		result = daocarro.readByMaisQueNConsertos(n);
		DAO.commit();
		return result;
	}


} // Fim class FachadaConserto

