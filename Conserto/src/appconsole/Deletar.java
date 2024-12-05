package appconsole;
/**
 * IFPB - SI
 * Disciplina: Persistência de Objetos
 */

import regras_negocio.FachadaConserto;


public class Deletar {

	public Deletar(){
		FachadaConserto.inicializar();
		
		System.out.println("\n.......... DELETANDO ..........");
		
		try {
			FachadaConserto.excluirCarro("ZZZ-000");
			System.out.println("Apagou o Carro ZZZ-0000");
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}


		try {
			FachadaConserto.excluirConserto(145656);
			System.out.println("Apagou o conserto de id 1");
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}


		try {
			FachadaConserto.excluirDefeito("Defeito para ser deletado");
			System.out.println("Apagou o Defeito para ser deletado");

			//FachadaConserto.excluirConserto(7); // NÃO apaga esse por causa da relação com a exclusão do carro ZZZ-0000 ali no início do try
			//System.out.println("Apagou o Conserto de id 7");
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}


		// Tentando apagar os inexistentes e(ou) errados.
		try {
			FachadaConserto.excluirCarro("ZZZ-000");
			System.out.println("Tentando apagar Carro com placa errada ZZZ-000");
		} 
		catch (Exception e) {
			System.err.println(e.getMessage());
		}


		try {		
			FachadaConserto.excluirConserto(123);
			System.out.println("Tentando apagar o conserto de id 123 que não existe");
		} 
		catch (Exception e) {
			System.err.println(e.getMessage());
		}


		try {
			FachadaConserto.excluirDefeito("Defeito que não existe para ser deletado");
		}catch (Exception e) {
			System.err.println(e.getMessage());
		}


		FachadaConserto.finalizar();
		System.out.println("\n.......... FIM DELETANDO ..........");

	} // Fim de Deletar()


	public static void main(String[] args) {
		new Deletar();
	}


} // Fim class Deletar

