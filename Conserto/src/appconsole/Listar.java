package appconsole;
/**
 * IFPB - SI
 * Disciplina: Persistência de Objetos
 */

import java.util.List;
import modelo.Carro;
import modelo.Conserto;
import modelo.Defeito;
import regras_negocio.FachadaConserto;

public class Listar {

	public Listar(){
		try {
			FachadaConserto.inicializar();
			
			System.out.println("\n.......... LISTAGENS ..........");


			System.out.println("\n***** Lista de Carros *****");
	        List<Carro> carros = FachadaConserto.listarCarros();
	        for (Carro carro : carros) {
	            System.out.println(carro);
	        }

	        System.out.println("\n***** Lista de Defeitos *****");
	        List<Defeito> defeitos = FachadaConserto.listarDefeitos();
	        for (Defeito defeito : defeitos) {
	            System.out.println(defeito);
	        }
	        
	        
	        System.out.println("\n***** Lista de Consertos *****");
	        List<Conserto> consertos = FachadaConserto.listarConsertos();
	        for (Conserto conserto : consertos) {
	            System.out.println(conserto);
	        }


		} catch (Exception e) {
			System.out.println(e.getMessage());
		}


		System.out.println("\n.......... FIM DAS LISTAGENS ..........");
		FachadaConserto.finalizar();
	}



	public static void main(String[] args) {
		new Listar();
	}

}//FIM class Listar

