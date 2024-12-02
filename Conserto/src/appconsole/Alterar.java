package appconsole;
/**
 * IFPB - SI
 * Disciplina: Persistencia de Objetos
 */

import regras_negocio.FachadaConserto;

public class Alterar {

	public Alterar(){
		FachadaConserto.inicializar();
		
		System.out.println("\n.......... ALTERAR ..........");

		//altera��o 1
		try {
			FachadaConserto.alterarNomeDefeito("nomeAntigo","novoNome");
			System.out.println("alterado nome de defeito");
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		//altera��o 2
		/*
		 * alterarCarro(String placa, String cpf, String proprietario)
		 * alterarPlaca(String placa, String novaplaca)
		 * alterarConserto(int id, String data, String placa)
		 * alterarData(int id, String data)
		 * alterarNomeDefeito(String nome, String novoNome)
		 * alterarPrecoDefeito(String nome, double preco)
		 * 
		 * */



		FachadaConserto.finalizar();
		System.out.println("\n.......... FIM ALTERAR ..........");
	}



	public static void main(String[] args) {
		new Alterar();
	}

}//Fim class Alterar

