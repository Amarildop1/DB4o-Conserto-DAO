package appconsole;
/**
 * IFPB - SI
 * Disciplina: Persistência de Objetos
 */

import regras_negocio.FachadaConserto;

public class Alterar {		//AS EXCEÇÕES AINDA ESTÃO GENÉRICAS. | Os prints estão genéricos e não confiáveis.

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
			System.out.println("Dono do veículo BBB-2222 alterado!");
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		try {
			FachadaConserto.alterarPlaca("EEE-5555", "ZZZ-1010");
			System.out.println("Placa do veículo EEE-5555 alterado para ZZZ-1010!");
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
			FachadaConserto.alterarNomeDefeito("Problema na Embreagem", "Problema na Elétrica");
			System.out.println("Alterado de Problema na Embreagem para Problema na Elética!");
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		try {
			FachadaConserto.alterarPrecoDefeito("Problema na Elétrica", 700.00);
			System.out.println("Preço de Problema na Elétrica atualizado para 700,00");
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

