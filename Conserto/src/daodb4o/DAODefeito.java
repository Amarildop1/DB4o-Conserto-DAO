package daodb4o;
/**
 * IFPB - SI
 * Disciplina: Persistência de Objetos
 */

import java.util.List;
import com.db4o.query.Query;
import modelo.Defeito;

public class DAODefeito  extends DAO<Defeito>{
	
	public Defeito read (String nome) {
		Query q = manager.query();
		q.constrain(Defeito.class);
		q.descend("nome").constrain(nome);
		List<Defeito> resultados = q.execute();
		if (resultados.size()>0)
			return resultados.get(0);
		else
			return null;
	}
	
	public void create(Defeito obj){
		int novoid = super.gerarId(Defeito.class);  	//gerar novo id da classe
		obj.setId(novoid);								//atualizar id do objeto antes de grava-lo no banco
		manager.store( obj );
	}
	
	/**********************************************************
	 * 
	 * TODAS AS CONSULTAS DE DEFEITO
	 * 
	 **********************************************************/
	
	public List<Defeito> readAll(String nome) {
		Query q = manager.query();
		q.constrain(Defeito.class);
		q.descend("nome").constrain(nome);
		return q.execute();
	}
	
	public List<Defeito> readByPreco(double preco) {
		Query q = manager.query();
		q.constrain(Defeito.class);
		q.descend("preco").constrain(preco);
		return q.execute();
	}
	
}//Fim da class DAODefeito

