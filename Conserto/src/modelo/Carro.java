package modelo;
/**
 * IFPB - SI
 * Disciplina: Persistencia de Objetos
 */

import java.util.ArrayList;
import java.util.List;

/**
 * Classe Carro 
 * 
 * */
public class Carro {
	private int id;
	private String placa;
	private String cpf;
	private String proprietario;
	private List<Conserto> consertos = new ArrayList<>();
	
	public Carro (String placa, String cpf, String proprietario) {
		this.placa = placa;
		this.cpf = cpf;
		this.proprietario = proprietario;
	}

	@Override
	public String toString() {
		String texto = "Id: " + id + ", Placa: " +  placa + " CPF: " + cpf + ", Proprietario: " + proprietario;
		
		//texto += "  IDConserto: ";
		//for(Conserto c : consertos)
		//	texto += c.getId() + ",";

		return texto;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getProprietario() {
		return proprietario;
	}

	public void setProprietario(String proprietario) {
		this.proprietario = proprietario;
	}

	public List<Conserto> getConsertos() {
		return consertos;
	}

	/*public void setConsertos(List<Conserto> consertos) {
		this.consertos = consertos;
	}*/
	
	public void adicionar(Conserto c){
		consertos.add(c);
		c.setCarro(this);
	}
	
	public void remover(Conserto c){
		consertos.remove(c);
		c.setCarro(null);
	}
	
	public Conserto localizar(int id){
		for(Conserto c : consertos)
			if (c.getId() == id)
				return c;
		return null;
	}
	
	
}//Fim da class Carro

