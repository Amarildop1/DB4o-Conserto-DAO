package appconsole;
/**
 * IFPB - SI
 * Disciplina: Persistência de Objetos
 */

import modelo.Carro;
import modelo.Defeito;
import modelo.Conserto;
import regras_negocio.FachadaConserto;
import java.util.Arrays;
import java.util.List;

public class Cadastrar {
    
    public static void main(String[] args) {
        try {
			FachadaConserto.inicializar();

            System.out.println("Cadastrando Carros...");
            cadastrarCarros();
            System.out.println("Carros cadastrados com sucesso!\n");


            System.out.println("Cadastrando Defeitos...");
            cadastrarDefeitos();
            System.out.println("Defeitos cadastrados com sucesso!\n");


            System.out.println("Cadastrando Consertos...");
            cadastrarConsertos();
            System.out.println("Consertos cadastrados com sucesso!\n");


            //System.out.println("\nLista de carros após cadastrar os defeitos:");
            //exibirConsertosDeCadaCarro();

        }catch (Exception e) {
            e.printStackTrace();
        }

    } // Fim do main


    private static void cadastrarCarros() throws Exception {
    	try {
	        FachadaConserto.criarCarro("AAA-1111", "12345678901", "João da Silva");
	        FachadaConserto.criarCarro("BBB-2222", "98765432100", "Maria Oliveira");
	        FachadaConserto.criarCarro("CCC-3333", "12312312312", "Carlos Souza");
	        FachadaConserto.criarCarro("DDD-4444", "32132132132", "Ana Costa");
	        FachadaConserto.criarCarro("EEE-5555", "55555555555", "Lucas Pereira");
	        
	        FachadaConserto.criarCarro("ZZZ-0000", "00000000000", "Carro para ser deletado");
	    }catch (Exception e){
	    	System.err.println(e.getMessage());
	    }

        try {
        	System.out.println("\n----- Carro com Placa Errada: ");
        	FachadaConserto.criarCarro("ZZZ-123", "00000000000", "Carro com placa errada");
        }catch (Exception e){
        	System.err.println(e.getMessage());
        }

        try {
        	System.out.println("\n----- Tentando criar Carro com CPF de 10 dígitos: ");
        	FachadaConserto.criarCarro("YYY-0000", "0123456789", "Carro com CPF 10 dígitos");
        }catch (Exception e){
        	System.err.println(e.getMessage());
        }
        try {
        	System.out.println("\n----- Tentando criar Carro com Placa e CPF incompletos: ");
        	FachadaConserto.criarCarro("ZZZ-00", "9876543210", "Carro com placa errada e CPF 10 dígitos");
        }catch (Exception e){
        	System.err.println(e.getMessage());
        }



        System.out.println("\nLista de Carros Cadastrados agora:");
        List<Carro> carros = FachadaConserto.listarCarros();
        for (Carro carro : carros) {
            System.out.println(carro);
        }

    } // Fim cadastrarCarros()


    private static void cadastrarDefeitos() throws Exception {
        // Criando 10 defeitos
    	try {
	        FachadaConserto.criarDefeito("Vazamento de Óleo", 150.0);
	        FachadaConserto.criarDefeito("Troca de Filtro de Ar", 50.0);
	        FachadaConserto.criarDefeito("Substituição de Freios", 350.0);
	        FachadaConserto.criarDefeito("Problema na Embreagem", 500.0);
	        FachadaConserto.criarDefeito("Troca de Pastilha de Freio", 200.0);
	        FachadaConserto.criarDefeito("Alinhamento de Direção", 100.0);
	        FachadaConserto.criarDefeito("Balanceamento de Rodas", 120.0);
	        FachadaConserto.criarDefeito("Troca de Correia Dentada", 250.0);
	        FachadaConserto.criarDefeito("Revisão de Suspensão", 300.0);
	        FachadaConserto.criarDefeito("Substituição de Amortecedor", 400.0);
	        
	        FachadaConserto.criarDefeito("Defeito para ser deletado", 1000.0);
	        
	        FachadaConserto.criarDefeito("Defeito para ser deletado", 1000.0); //Tentativa de criar Defeito repetido
    	}catch(Exception e){
        	System.err.println(e.getMessage());
    	}

        System.out.println("\nLista de Defeitos Cadastrados agora:");
        List<Defeito> defeitos = FachadaConserto.listarDefeitos();
        for (Defeito defeito : defeitos) {
            System.out.println(defeito);
        }

    } // Fim cadastrarDefeitos()


    private static void cadastrarConsertos() throws Exception {
		try {
			FachadaConserto.criarConserto("10/01/2023", "AAA-1111", Arrays.asList("Vazamento de Óleo", "Troca de Filtro de Ar"));
			FachadaConserto.criarConserto("12/02/2023", "BBB-2222", Arrays.asList("Substituição de Freios", "Problema na Embreagem"));
    		FachadaConserto.criarConserto("15/03/2023", "CCC-3333", Arrays.asList("Troca de Pastilha de Freio", "Alinhamento de Direção"));
    		FachadaConserto.criarConserto("20/04/2023", "DDD-4444", Arrays.asList("Balanceamento de Rodas", "Troca de Correia Dentada"));
    		FachadaConserto.criarConserto("25/05/2023", "EEE-5555", Arrays.asList("Revisão de Suspensão", "Substituição de Amortecedor"));
		        
		    FachadaConserto.criarConserto("06/06/2024", "BBB-2222", Arrays.asList("Balanceamento de Rodas", "Revisão de Suspensão"));
		    FachadaConserto.criarConserto("06/06/2024", "DDD-4444", Arrays.asList("Revisão de Suspensão", "Troca de Correia Dentada"));
		    FachadaConserto.criarConserto("06/06/2024", "EEE-5555", Arrays.asList("Substituição de Amortecedor", "Troca de Filtro de Ar"));
		    FachadaConserto.criarConserto("06/06/2024", "EEE-5555", Arrays.asList("Troca de Filtro de Ar", "Balanceamento de Rodas"));
		    FachadaConserto.criarConserto("06/06/2024", "EEE-5555", Arrays.asList("Troca de Correia Dentada", "Revisão de Suspensão"));
		    
		    FachadaConserto.criarConserto("11/11/2024", "BBB-2222", Arrays.asList("Troca de Filtro de Ar"));

		    FachadaConserto.criarConserto("01/01/2025", "ZZZ-0000", Arrays.asList("Balanceamento de Rodas", "Revisão de Suspensão"));
		}catch (Exception e){
	    	System.err.println(e.getMessage());
	    }


	        try {
	        	System.out.println("\n----- Tentando criar Conserto sem o Defeito cadastrado previamente: ");
	        	FachadaConserto.criarConserto("01/12/2024", "BBB-2222", Arrays.asList("Troca de Filtro"));
	    	}catch (Exception e){
	        	System.err.println(e.getMessage());
	        }
	        
	        try {
	        	System.out.println("\n----- Tentando criar Conserto sem a Placa correta: ");
	        	FachadaConserto.criarConserto("01/12/2024", "BBB-2233", Arrays.asList("Troca de Filtro de Ar"));
	    	}catch (Exception e){
	        	System.err.println(e.getMessage());
	        }
	        
	        try {
	        	System.out.println("\n----- Tentando criar Conserto sem existir a Data: ");
	        	FachadaConserto.criarConserto("01/12/202", "BBB-2222", Arrays.asList("Troca de Filtro de Ar"));
	    	}catch (Exception e){
	        	System.err.println(e.getMessage());
	        }

	
	        System.out.println("\nLista de Consertos Cadastrados agora:");
	        List<Conserto> consertos = FachadaConserto.listarConsertos();
	        for (Conserto conserto : consertos) {
	            System.out.println(conserto);
	        }


    } // Fim cadastrarConsertos()


    /* //FALTA AJUSTAR ESSA EXIBIÇÃO 
    private static void exibirConsertosDeCadaCarro() throws Exception{
        List<Carro> carros = FachadaConserto.listarCarros();
        
        System.out.println("LISTANDO CONSERTOS DE CADA CARRO:");
        for (Carro carro : carros) {
            System.out.println(carro.getConsertos());
        }
    }
    */

} // Fim class Cadastrar

