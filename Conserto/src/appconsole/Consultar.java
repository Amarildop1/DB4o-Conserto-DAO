package appconsole;
/**
 * IFPB - SI
 * Disciplina: Persistência de Objetos
 */

import java.util.List;
import modelo.Carro;
import modelo.Conserto;
import regras_negocio.FachadaConserto;


public class Consultar {

	public Consultar(){

		try {
			FachadaConserto.inicializar();
			
			System.out.println("\n.......... CONSULTAS ..........");
			
			System.out.println("\nCarros Localizados: ");
	        //System.out.println(FachadaConserto.localizarCarro("AZZ-9999")); SE PESQUISAR PRIMEIRO O INEXISTENTE NÃO CONTINUA OS DE BAIXO
	        System.out.println(FachadaConserto.localizarCarro("BBB-2222"));
	        System.out.println(FachadaConserto.localizarCarro("EEE-5555"));
			System.out.println(FachadaConserto.localizarCarro("CCC-3333"));


	        System.out.println("\nDefeitos Localizados: ");
	        System.out.println(FachadaConserto.localizarDefeito("Revisão de Suspensão"));
	        System.out.println(FachadaConserto.localizarDefeito("Troca de Pastilha de Freio"));


	        System.out.println("\nConsertos Localizados: ");
	        //System.out.println(FachadaConserto.localizarConserto(7)); SE PESQUISAR PRIMEIRO O INEXISTENTE NÃO CONTINUA OS DE BAIXO
	        System.out.println(FachadaConserto.localizarConserto(3));
	        System.out.println(FachadaConserto.localizarConserto(2));
	        
	        
	        System.out.println("\nConsertos na data: 06/06/2024");        
	        List<Conserto> consertosNaData = FachadaConserto.consultarDataConserto("06/06/2024");
	        for (Conserto consertos : consertosNaData) {
	        	System.out.println("id: " + consertos.getId());
	            System.out.println("Carro: " + consertos.getCarro().getPlaca());
	            System.out.println("Proprietário: " + consertos.getCarro().getProprietario());
	            System.out.println("Preço Final= " + consertos.getPrecoFinal());
	            System.out.println();
	        }


	        System.out.println("\n\nCarros com o seguinte Defeito: Revisão de Suspensão");
	        List<Carro> carrosComDefeitoX = FachadaConserto.consultarCarrosComDefeitoX("Revisão de Suspensão");
	        for (Carro carro : carrosComDefeitoX) {
	        	System.out.println("id: " + carro.getId());
	            System.out.println("Placa: " + carro.getPlaca());
	            System.out.println("Proprietário: " + carro.getProprietario());
	            System.out.println();
	        }

	        
	        System.out.println("\n\nCarros com mais de 2 Consertos:");
	        List<Carro> carrosComMaisQueNConsertos = FachadaConserto.consultarCarrosComMaisQueNConserto(2);
	        for (Carro carro : carrosComMaisQueNConsertos) {
	        	System.out.println("id: " + carro.getId());
	            System.out.println("Placa: " + carro.getPlaca());
	            System.out.println("Proprietário: " + carro.getProprietario());
	            System.out.println();
	        }


		} catch (Exception e) {
			System.out.println(e.getMessage());
		}


		FachadaConserto.finalizar();
		System.out.println("\n.......... FIM DAS CONSULTAS ..........");
	}


	public static void main(String[] args) {
		new Consultar();
	}

}//Fim class Consultar

