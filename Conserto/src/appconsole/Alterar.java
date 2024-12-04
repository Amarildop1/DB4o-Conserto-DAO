package appconsole;
/**
 * IFPB - SI
 * Disciplina: Persist�ncia de Objetos
 */

import regras_negocio.FachadaConserto;

public class Alterar {		//AS EXCE��ES AINDA EST�O GEN�RICAS. | Os prints est�o gen�ricos e n�o confi�veis.

	public Alterar(){
		FachadaConserto.inicializar();
		
		System.out.println("\n.......... ALTERAR ..........");

		try {
			FachadaConserto.alterarNomeDefeito("nomeAntigo","novoNome");
			System.out.println("alterado nome de defeito");
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		try {
			FachadaConserto.alterarCarro("BBB-2222", "01020304050", "Dono alterado");
			System.out.println("Dono do ve�culo BBB-2222 alterado!");
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		try {
			FachadaConserto.alterarPlaca("EEE-5555", "ZZZ-1010");
			System.out.println("Placa do ve�culo EEE-5555 alterado para ZZZ-1010!");
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		try {
			FachadaConserto.alterarConserto(9, "07/07/2024", "ZZZ-1010");
			System.out.println("Data do conserto 9 era 06/06/2024 e foi alterada para 07/07/2024");
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		try {
			FachadaConserto.alterarData(9, "08/08/2024");
			System.out.println("Data do conserto 9 era 07/07/2024 e foi alterada para 08/08/2024");
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		try {
			FachadaConserto.alterarNomeDefeito("Problema na Embreagem", "Problema na El�trica");
			System.out.println("Alterado de Problema na Embreagem para Problema na El�tica!");
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		try {
			FachadaConserto.alterarPrecoDefeito("Problema na El�trica", 700.00);
			System.out.println("Pre�o de Problema na El�trica atualizado para 700,00");
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}


		FachadaConserto.finalizar();
		System.out.println("\n.......... FIM ALTERAR ..........");
	}



	public static void main(String[] args) {
		new Alterar();
	}

}//Fim class Alterar

