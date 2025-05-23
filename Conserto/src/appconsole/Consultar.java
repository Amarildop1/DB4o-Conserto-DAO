package appconsole;
/**
 * IFPB - SI
 * Disciplina: Persist�ncia de Objetos
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
			//System.out.println(FachadaConserto.localizarCarro("AZZ-9999")); // Se pesquisar primeiro o inexistente, n�o continua os de baixo.
	        System.out.println(FachadaConserto.localizarCarro("BBB-2222"));
	        System.out.println(FachadaConserto.localizarCarro("EEE-5555"));
			System.out.println(FachadaConserto.localizarCarro("CCC-3333"));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}


		try {
	        System.out.println("\nDefeitos Localizados: ");
	        System.out.println(FachadaConserto.localizarDefeito("Revis�o de Suspens�o"));
	        System.out.println(FachadaConserto.localizarDefeito("Troca de Pastilha de Freio"));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}


		try {
	        System.out.println("\nConsertos Localizados: ");
	        //System.out.println(FachadaConserto.localizarConserto(7)); // Se pesquisar primeiro o inexistente, n�o continua os de baixo.
	        System.out.println(FachadaConserto.localizarConserto(3));
	        System.out.println(FachadaConserto.localizarConserto(2));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}


		try {
	        System.out.println("\nConsertos na data: 06/06/2024");        
	        List<Conserto> consertosNaData = FachadaConserto.consultarDataConserto("06/06/2024");
	        for (Conserto consertos : consertosNaData) {
	        	/*
	        	System.out.println("id: " + consertos.getId());
	            System.out.println("Carro: " + consertos.getCarro().getPlaca());
	            System.out.println("Propriet�rio: " + consertos.getCarro().getProprietario());
	            System.out.println("Pre�o Final= " + consertos.getPrecoFinal());
	            */
	            System.out.println(consertos);
	            //System.out.println();
	        }
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}


		try {
	        System.out.println("\n\nCarros com o seguinte Defeito: Revis�o de Suspens�o");
	        List<Carro> carrosComDefeitoX = FachadaConserto.consultarCarrosComDefeitoX("Revis�o de Suspens�o");
	        for (Carro carro : carrosComDefeitoX) {
	        	/*
	        	System.out.println("id: " + carro.getId());
	            System.out.println("Placa: " + carro.getPlaca());
	            System.out.println("Propriet�rio: " + carro.getProprietario());
	            */
	            System.out.println(carro);
	            System.out.println();
	        }
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}


		try {
	        System.out.println("\n\nCarros com mais de 2 Consertos:");
	        List<Carro> carrosComMaisQueNConsertos = FachadaConserto.consultarCarrosComMaisQueNConserto(2);
	        for (Carro carro : carrosComMaisQueNConsertos) {
	        	/*
	        	System.out.println("id: " + carro.getId());
	            System.out.println("Placa: " + carro.getPlaca());
	            System.out.println("Propriet�rio: " + carro.getProprietario());
	            */
	            System.out.println(carro);
	            System.out.println();
	        }

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}


		// Tentando com inexistentes e(ou) errados.
		try {
			System.out.println("\nTentando buscar carro informando placas erradas: ");
	        System.out.println(FachadaConserto.localizarCarro("BBB-222"));
	        //System.out.println(FachadaConserto.localizarCarro("EE-5555"));
			//System.out.println(FachadaConserto.localizarCarro("CC-333"));
		}catch (Exception e) {
			System.err.println(e.getMessage());
		}

		try {
	        System.out.println("\nTentando buscar Defeitos informando nomes que n�o est�o cadastrados: ");
	        System.out.println(FachadaConserto.localizarDefeito("Revis�o Suspens�o"));
	        //System.out.println(FachadaConserto.localizarDefeito("Troca de Pastilha de FreioSSS"));
		}catch (Exception e) {
			System.err.println(e.getMessage());
		}

	    try {
	        System.out.println("\nTentando buscar Consertos que n�o existem: ");
	        System.out.println(FachadaConserto.localizarConserto(345));
		}catch (Exception e) {
			System.err.println(e.getMessage());
		}



		FachadaConserto.finalizar();
		System.out.println("\n.......... FIM DAS CONSULTAS ..........");

	} // Fim de Consultar()


	public static void main(String[] args) {
		new Consultar();
	}


} // Fim class Consultar

