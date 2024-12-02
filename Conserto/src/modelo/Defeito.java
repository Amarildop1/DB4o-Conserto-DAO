package modelo;
/**
 * IFPB - SI
 * Disciplina: Persistencia de Objetos
 */

public class Defeito {
	private int id;
	private String nome;
	private double preco;
	
	public Defeito (String nome, double preco) {
		this.nome = nome;
		this.preco = preco;
	}

	@Override
	public String toString() {
		return "id: " + id + ", Nome: " + nome + ", Preco = R$ " + preco;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public double getPreco() {
		return preco;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}

}//Fim class Defeito

