package appconsole;
/**
 * IFPB - SI
 * Disciplina: Persist�ncia de Objetos
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

            System.out.println("Cadastrando Carros:");
            cadastrarCarros();
            System.out.println("Carros cadastrados com sucesso!\n");


            System.out.println("Cadastrando Defeitos:");
            cadastrarDefeitos();
            System.out.println("Defeitos cadastrados com sucesso!\n");


            System.out.println("Cadastrando Consertos:");
            cadastrarConsertos();
            System.out.println("Consertos cadastrados com sucesso!\n");


            System.out.println("\nLista de carros ap�s cadastrar os defeitos:");
            exibirConsertosDeCadaCarro();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }//Fim do main


    private static void cadastrarCarros() throws Exception {

        FachadaConserto.criarCarro("AAA-1111", "12345678901", "Jo�o da Silva");
        FachadaConserto.criarCarro("BBB-2222", "98765432100", "Maria Oliveira");
        FachadaConserto.criarCarro("CCC-3333", "12312312312", "Carlos Souza");
        FachadaConserto.criarCarro("DDD-4444", "32132132132", "Ana Costa");
        FachadaConserto.criarCarro("EEE-5555", "55555555555", "Lucas Pereira");
        
        FachadaConserto.criarCarro("ZZZ-0000", "00000000000", "Carro para ser deletado");


        System.out.println("Lista de Carros Cadastrados agora:");
        List<Carro> carros = FachadaConserto.listarCarros();
        for (Carro carro : carros) {
            System.out.println(carro);
        }
    }


    private static void cadastrarDefeitos() throws Exception {
        // Criando 10 defeitos
        FachadaConserto.criarDefeito("Vazamento de �leo", 150.0);
        FachadaConserto.criarDefeito("Troca de Filtro de Ar", 50.0);
        FachadaConserto.criarDefeito("Substitui��o de Freios", 350.0);
        FachadaConserto.criarDefeito("Problema na Embreagem", 500.0);
        FachadaConserto.criarDefeito("Troca de Pastilha de Freio", 200.0);
        FachadaConserto.criarDefeito("Alinhamento de Dire��o", 100.0);
        FachadaConserto.criarDefeito("Balanceamento de Rodas", 120.0);
        FachadaConserto.criarDefeito("Troca de Correia Dentada", 250.0);
        FachadaConserto.criarDefeito("Revis�o de Suspens�o", 300.0);
        FachadaConserto.criarDefeito("Substitui��o de Amortecedor", 400.0);
        
        FachadaConserto.criarDefeito("Defeito para ser deletado", 1000.0);


        System.out.println("Lista de Defeitos Cadastrados agora:");
        List<Defeito> defeitos = FachadaConserto.listarDefeitos();
        for (Defeito defeito : defeitos) {
            System.out.println(defeito);
        }
    }


    private static void cadastrarConsertos() throws Exception {

        FachadaConserto.criarConserto("10/01/2023", "AAA-1111", Arrays.asList("Vazamento de �leo", "Troca de Filtro de Ar"));
        FachadaConserto.criarConserto("12/02/2023", "BBB-2222", Arrays.asList("Substitui��o de Freios", "Problema na Embreagem"));
        FachadaConserto.criarConserto("15/03/2023", "CCC-3333", Arrays.asList("Troca de Pastilha de Freio", "Alinhamento de Dire��o"));
        FachadaConserto.criarConserto("20/04/2023", "DDD-4444", Arrays.asList("Balanceamento de Rodas", "Troca de Correia Dentada"));
        FachadaConserto.criarConserto("25/05/2023", "EEE-5555", Arrays.asList("Revis�o de Suspens�o", "Substitui��o de Amortecedor"));
        
        FachadaConserto.criarConserto("06/06/2024", "BBB-2222", Arrays.asList("Balanceamento de Rodas", "Revis�o de Suspens�o"));
        FachadaConserto.criarConserto("06/06/2024", "DDD-4444", Arrays.asList("Revis�o de Suspens�o", "Troca de Correia Dentada"));
        FachadaConserto.criarConserto("06/06/2024", "EEE-5555", Arrays.asList("Substitui��o de Amortecedor", "Troca de Filtro de Ar"));
        FachadaConserto.criarConserto("06/06/2024", "EEE-5555", Arrays.asList("Troca de Filtro de Ar", "Balanceamento de Rodas"));
        FachadaConserto.criarConserto("06/06/2024", "EEE-5555", Arrays.asList("Troca de Correia Dentada", "Revis�o de Suspens�o"));
        FachadaConserto.criarConserto("11/11/2024", "BBB-2222", Arrays.asList("Troca de Filtro de Ar"));
        
        FachadaConserto.criarConserto("01/01/2025", "ZZZ-0000", Arrays.asList("Balanceamento de Rodas", "Revis�o de Suspens�o"));

        System.out.println("Lista de Consertos Cadastrados agora:");
        List<Conserto> consertos = FachadaConserto.listarConsertos();
        for (Conserto conserto : consertos) {
            System.out.println(conserto);
        }
    }


    private static void exibirConsertosDeCadaCarro() throws Exception{
        List<Carro> carros = FachadaConserto.listarCarros();
        
        System.out.println("LISTANDO CONSERTOS DE CADA CARRO:");
        for (Carro carro : carros) {
            System.out.println(carro.getConsertos());
        }
    }

}//Fim class Cadastrar

