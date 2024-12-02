package modelo;
/**
 * IFPB - SI
 * Disciplina: Persistencia de Objetos
 */

import java.util.ArrayList;
import java.util.List;

public class Conserto {
	private int id;
	private String data;
	private Carro carro;
	private List<Defeito> defeitos = new ArrayList<>();
	private double precoFinal;
	
	public Conserto (String data) {
		this.data = data;
	}

	@Override
	public String toString() {
		String texto = "\nID Conserto: " + id + " \nData: " +  data;
		
		texto +=  (carro!=null ? " \nCarro: " + carro.getPlaca() : " \nCarro: ");

		texto += "  \nDefeitos: ";
		for(Defeito d : defeitos)
			texto += d.getNome() + ", ";

		texto += " \nPreço final = R$ " + precoFinal + "\n";

		return texto;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
	
//	-------------------- Relacionamento --------------------------------
	public Carro getCarro() {
		return carro;
	}
	
	public void setCarro(Carro carro) {
		this.carro = carro;
	}

	public List<Defeito> getDefeitos() {
		return defeitos;
	}

	/*public void setDefeitos(List<Defeito> defeitos) {
		this.defeitos = defeitos;
	}*/
	
	public void adicionar (Defeito d) {
		this.defeitos.add(d);
	}
	
	public void remover (Defeito d) {
		this.defeitos.remove(d);
	}
	
	public void calcularPrecoFinal() {
		double resultadoPrecoFinal = 0;
		for (Defeito defeito : defeitos) {
			resultadoPrecoFinal += defeito.getPreco();
		}
		setPrecoFinal(resultadoPrecoFinal);
	}

	public double getPrecoFinal() {
		return precoFinal;
	}

	public void setPrecoFinal(double precoFinal) {
		this.precoFinal = precoFinal;
	}
	
}//Fim class Conserto

