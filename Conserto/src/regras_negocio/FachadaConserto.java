package regras_negocio;
/**
 * IFPB - SI
 * Disciplina: Persistencia de Objetos
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
 * */
	public static void criarCarro(String placa, String cpf, String proprietario) throws Exception {

		DAO.begin();
		
		placa = placa.trim();
		cpf = cpf.trim();
		proprietario = proprietario.trim();
		
		Carro c = daocarro.read(placa);
		if (c != null) {
			DAO.rollback();
			throw new Exception("Criar Carro - carro com placa " + placa + " já existe");
		}
		
		if (!cpf.matches("\\d{11}")) {
			DAO.rollback();
			throw new Exception("Criar carro - o cpf deve ser numérico e ter exatamente 11 dígitos");
		}
		
		c = new Carro(placa, cpf, proprietario);
		daocarro.create(c);
		
		DAO.commit();
	}//Final método criarCarro();



	public static Carro localizarCarro(String placa) throws Exception {
		placa = placa.trim();
		Carro c = daocarro.read(placa);
		if (c == null) {
			throw new Exception("Carro com placa " + placa + " inexistente");
		}
		return c;
	}



	//altera o cpf e nome do proprietário
	public static void alterarCarro(String placa, String cpf, String proprietario) throws Exception {
		DAO.begin();
		
		placa = placa.trim();
		cpf = cpf.trim();
		proprietario = proprietario.trim();
		
		Carro c = daocarro.read(placa);
		if (c == null) {
			DAO.rollback();
			throw new Exception("Alterar carro - carro com placa " + placa + " inexistente");
		}
		
		if (!cpf.matches("\\d{11}")) {
			DAO.rollback();
			throw new Exception("Alterar carro - o cpf deve ser numérico e ter exatamente 11 dígitos");
		}
		
		c.setCpf(cpf);
		c.setProprietario(proprietario);
		daocarro.update(c);
		
		DAO.commit();
	}

	//carro
	public static void alterarPlaca(String placa, String novaplaca) throws Exception {
		DAO.begin();
		
		placa = placa.trim();
		novaplaca = novaplaca.trim();
		Carro c = daocarro.read(placa); // usando chave primaria
		
		if (c == null) {
			DAO.rollback();
			throw new Exception("Alterar placa - placa inexistente: " + placa);
		}
		
		c.setPlaca(novaplaca);
		daocarro.update(c);
		
		DAO.commit();
	}


	public static void excluirCarro(String placa) throws Exception {
		DAO.begin();
		
		placa = placa.trim();
		
		Carro c = daocarro.read(placa);
		if (c == null) {
			DAO.rollback();
			throw new Exception("Excluir carro - carro com placa " + placa + " inexistente");
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
	public static void criarConserto(String data, String placa, List<String> defeitos) throws Exception {
		DAO.begin();
		
		data = data.trim();
		placa = placa.trim();
		
		try {
			LocalDate.parse(data, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		} catch (DateTimeParseException e) {
			DAO.rollback();
			throw new Exception("Criar conserto - formato data inválido: " + data);
		}
		
		Carro c = daocarro.read(placa);
		if (c == null) {
			DAO.rollback();
			throw new Exception("Criar conserto - carro com placa" + placa + " inexistente");
		}
		
	    Conserto conserto = new Conserto(data);
	    conserto.setCarro(c);

	    for (String defeito : defeitos) {
	    	defeito = defeito.trim();
	        Defeito d = daodefeito.read(defeito);
	        
	        if (d == null) {
	            DAO.rollback();
	            throw new Exception("Criar conserto - defeito não encontrado: " + defeito);
	        }
	        conserto.adicionar(d);
	    }
	    
	    conserto.calcularPrecoFinal();
	    c.adicionar(conserto);
	    daoconserto.create(conserto);
	    
		DAO.commit();
	}//Final do método criarConserto()


/**
 * Método Localizar Conserto
 * 
 * @param id do conserto para ser localizado
 * 
 * @return Um conserto do id
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
	public static void alterarConserto(int id, String data, String placa) throws Exception {
		DAO.begin();
		
		data = data.trim();
		placa = placa.trim();
		
		Conserto conserto = daoconserto.read(id);
		if (conserto == null) {
			DAO.rollback();
			throw new Exception("Alterar conserto - conserto inexistente: " + id);
		}

		if (data != null) {
			try {
				LocalDate.parse(data, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
				conserto.setData(data);
			} catch (DateTimeParseException e) {
				DAO.rollback();
				throw new Exception("Alterar conserto - formato data invalido: " + data);
			}
		}
		
		Carro c = daocarro.read(placa);
		if (c == null) {
			DAO.rollback();
			throw new Exception("Alterar conserto - carro com placa" + placa + " inexistente");
		}
		
		conserto.setCarro(c);

		daoconserto.update(conserto);
		
		DAO.commit();
	}//Final do método alterarConserto()


	//conserto
	public static void alterarData(int id, String data) throws Exception {
		DAO.begin();
		
		data = data.trim();
		
		Conserto conserto = daoconserto.read(id);
		if (conserto == null) {
			DAO.rollback();
			throw new Exception("Alterar data - conserto inexistente: " + id);
		}

		if (data != null) {
			try {
				LocalDate.parse(data, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
				conserto.setData(data);
			} catch (DateTimeParseException e) {
				DAO.rollback();
				throw new Exception("Alterar data - formato data invalido: " + data);
			}
		}
		daoconserto.update(conserto);
		DAO.commit();
	}


	
	public static void excluirConserto(int id) throws Exception {
		DAO.begin();
		
		Conserto conserto = daoconserto.read(id);
		if (conserto == null) {
			DAO.rollback();
			throw new Exception("Excluir conserto - conserto com id " + id + " inexistente");
		}

		Carro c = conserto.getCarro();
		c.remover(conserto);
		conserto.setCarro(null);
		daocarro.update(c);

		daoconserto.delete(conserto);
		
		DAO.commit();
	}
	
	
	
	//conserto
	public static void adicionarDefeitos(int id, List<String> defeitos) throws Exception {
		DAO.begin();
		
		Conserto conserto = daoconserto.read(id);
		if (conserto == null) {
			DAO.rollback();
			throw new Exception("Adicionar defeito - conserto com id " + id + " inexistente");
		}
		
		for (String defeito : defeitos) {
	    	defeito = defeito.trim();
	        Defeito d = daodefeito.read(defeito);
	        
	        if (d == null) {
	            DAO.rollback();
	            throw new Exception("Adicionar defeito - defeito não encontrado: " + defeito);
	        }
	        conserto.adicionar(d);
	    }
		
		conserto.calcularPrecoFinal();
		daoconserto.update(conserto);
		DAO.commit();
	}

	
	//conserto
	public static void removerDefeito(int id, List<String> defeitos) throws Exception {
		DAO.begin();
		
		Conserto conserto = daoconserto.read(id);
		if (conserto == null) {
			DAO.rollback();
			throw new Exception("Remover defeito - conserto com id " + id + " inexistente");
		}
		
		for (String defeito : defeitos) {
	    	defeito = defeito.trim();
	        Defeito d = daodefeito.read(defeito);
	        
	        if (d == null) {
	            DAO.rollback();
	            throw new Exception("Remover defeito - defeito não encontrado: " + defeito);
	        }
	        conserto.remover(d);
	    }
		
		conserto.calcularPrecoFinal();
		daoconserto.update(conserto);
		
		DAO.commit();
	}
						// ********** TUDO SOBRE CONSERTO ACIMA **********
	


	
						// ********** TUDO SOBRE DEFEITO ABAIXO **********
	public static void criarDefeito(String nome, double preco) throws Exception {
		DAO.begin();
		
		nome = nome.trim();
		
		Defeito d = new Defeito (nome, preco);
		daodefeito.create(d);
		
		DAO.commit();
	}


/**
 * Localizar Defeito
 * 
 * @param String nome do defeito para ser localizado
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


	public static void alterarNomeDefeito(String nome, String novoNome) throws Exception {
		DAO.begin();
		
		nome = nome.trim();
		novoNome = novoNome.trim();
		
		Defeito d = daodefeito.read(nome);
		if (d == null) {
			DAO.rollback();
			throw new Exception("Alterar nome defeito - defeito inexistente:" + nome);
		}
		
		d.setNome(novoNome);
		daodefeito.update(d);
		
		DAO.commit();
	}


	public static void alterarPrecoDefeito(String nome, double preco) throws Exception {
		DAO.begin();
		
		nome = nome.trim();
		
		Defeito d = daodefeito.read(nome);
		if (d == null) {
			DAO.rollback();
			throw new Exception("Alterar preÃ§o defeito - defeito inexistente:" + nome);
		}
		
		d.setPreco(preco);
		daodefeito.update(d);
		
		DAO.commit();
	}


	public static void excluirDefeito(String nome) throws Exception {
		DAO.begin();
		
		nome = nome.trim();
		
		Defeito d = daodefeito.read(nome);
		if (d == null) {
			DAO.rollback();
			throw new Exception("Excluir defeito - defeito inexistente:" + nome);
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
/* Se não quisermos utilizar o outro método lá em cima que retorna uma unidade específica
	public static List<Carro> consultarCarros(String caracteres) {
		List<Carro> result;
		if (caracteres.isEmpty())
			result = daocarro.readAll();
		else
			result = daocarro.readAll(caracteres);
		return result;
	}
*/

/* Se não quisermos utilizar o outro método lá em cima que retorna uma unidade específica
	public static List<Conserto> consultarConsertos(int digitos) {
		List<Conserto> result;
		result = daoconserto.readAll(digitos);
		return result;
	}
*/

/* Se não quisermos utilizar o outro método lá em cima que retorna uma unidade específica
	public static List<Defeito> consultarDefeito(String caracteres) {
		List<Defeito> result;
		if (caracteres.isEmpty())
			result = daodefeito.readAll();
		else
			result = daodefeito.readAll(caracteres);
		return result;
	}
*/
	public static List<Conserto> consultarDataConserto(String data) {
		List<Conserto> result;
		result = daoconserto.readByData(data);
		return result;
	}
	
	public static List<Carro> consultarCarrosComDefeitoX(String caracteres) {
		List<Carro> result;
		result = daocarro.readByDefeito(caracteres);
		return result;
	}

	public static List<Carro> consultarCarrosComMaisQueNConserto(int n) {
		List<Carro> result;
		DAO.begin();
		result = daocarro.readByMaisQueNConsertos(n);
		DAO.commit();
		return result;
	}

}//Final da class FachadaConserto

