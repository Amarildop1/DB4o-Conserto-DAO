package daodb4o;
/**
 * IFPB - SI
 * Disciplina: Persistência de Objetos
 */

import java.util.List;
import com.db4o.query.Candidate;
import com.db4o.query.Evaluation;
import com.db4o.query.Query;

import modelo.Carro;

public class DAOCarro  extends DAO<Carro>{

	//placa é usada como campo unico 
	public Carro read (String placa) {
		Query q = manager.query();
		q.constrain(Carro.class);
		q.descend("placa").constrain(placa);
		List<Carro> resultados = q.execute();
		if (resultados.size()>0)
			return resultados.get(0);
		else
			return null;
	}

	public void create(Carro obj){
		int novoid = super.gerarId(Carro.class);  	//gerar novo id da classe
		obj.setId(novoid);							//atualizar id do objeto antes de grava-lo no banco
		manager.store( obj );
	}
	/**********************************************************
	 * 
	 * TODAS AS CONSULTAS DE CARRO
	 * 
	 **********************************************************/

	public  List<Carro> readAll(String caracteres) {
		Query q = manager.query();
		q.constrain(Carro.class);
		q.descend("placa").constrain(caracteres).like();		//insensitive
		List<Carro> result = q.execute(); 
		return result;
	}
	
	public Carro readById(int id){
		Query q = manager.query();
		q.constrain(Carro.class);
		q.descend("consertos").descend("id").constrain(id);
		List<Carro> resultados = q.execute();
		if(resultados.isEmpty())
			return null;
		else
			//return resultados.getFirst();
			return resultados.get(0); //CORREÇÃO DO ERRO DA LINHA ACIMA
	}
	
	public List<Carro> readByDefeito(String defeito) {
		Query q = manager.query();
		q.constrain(Carro.class);
		q.descend("consertos").descend("defeitos").descend("nome").constrain(defeito).like();
		return q.execute();
	}

	public List<Carro> readByMaisQueNConsertos(int n) {
		Query q = manager.query();
		q.constrain(Carro.class);
		q.constrain(new Filtro(n));
		return q.execute(); 
	}
}//Fim class DAOCarro


/*-------------------------------------------------*/
@SuppressWarnings("serial")
class Filtro  implements Evaluation {
	private int n;
	public Filtro (int n) {
		this.n=n;
	}
	public void evaluate(Candidate candidate) {
		Carro c = (Carro) candidate.getObject();
		candidate.include( c.getConsertos().size() > n );
	}
}


