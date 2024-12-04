package appconsole;
/**
 * IFPB - SI
 * Disciplina: Persist�ncia de Objetos
 */

import regras_negocio.FachadaConserto;


public class Deletar {

	public Deletar(){
		FachadaConserto.inicializar();
		
		System.out.println("\n.......... DELETANDO ..........");
		
		try {
			FachadaConserto.excluirCarro("ZZZ-0000");
			System.out.println("Apagou o Carro ZZZ-0000");
			
			FachadaConserto.excluirConserto(1);
			System.out.println("Apagou o conserto de id 1");

			FachadaConserto.excluirDefeito("Defeito para ser deletado");
			System.out.println("Apagou o Defeito para ser deletado");
			
			//FachadaConserto.excluirConserto(17); //Teste com Conserto inexistente - Ap�s esse, n�o executa a linha abaixo
			
			FachadaConserto.excluirConserto(7); // N�O apaga esse por causa da rela��o com a exclus�o do carro ZZZ-0000 ali no in�cio do try
			System.out.println("Apagou o Conserto de id 7");
		} 
		catch (Exception e) {
			System.out.println(e.getMessage());
		}


		FachadaConserto.finalizar();
		System.out.println("\n.......... FIM DELETANDO ..........");
	}


	public static void main(String[] args) {
		new Deletar();
	}

}//Fim class Deletar

