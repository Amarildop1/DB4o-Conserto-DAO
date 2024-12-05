package daodb4o;
/**
 * IFPB - SI
 * Disciplina: Persistência de Objetos
 */

import java.util.List;
import com.db4o.query.Query;
import modelo.Conserto;

public class DAOConserto  extends DAO<Conserto>{
	
	//id é usado como campo único 
	public Conserto read (int id) {
		Query q = manager.query();
		q.constrain(Conserto.class);
		q.descend("id").constrain(id);
		List<Conserto> resultados = q.execute();
		if (resultados.size()>0)
			return resultados.get(0);
		else
			return null;
	}
	
	public void create(Conserto obj){
		int novoid = super.gerarId(Conserto.class);  	//gerar novo id da classe
		obj.setId(novoid);								//atualizar id do objeto antes de grava-lo no banco
		manager.store( obj );
	}
	
	/**********************************************************
	 * 
	 * TODAS AS CONSULTAS DE CONSERTO
	 * 
	 **********************************************************/

	public List<Conserto> readAll(int id) {
		Query q = manager.query();
		q.constrain(Conserto.class);
		q.descend("id").constrain(id);
		return q.execute(); 
	}

	public List<Conserto> readByPlaca(String placa) {
		Query q = manager.query();
		q.constrain(Conserto.class);
		q.descend("carro").descend("placa").constrain(placa).like();
		return q.execute();
	}
	
	public List<Conserto> readByData(String data) {
		Query q = manager.query();
		q.constrain(Conserto.class);  
		q.descend("data").constrain(data).contains();
		return q.execute();
	}
	
	public List<Conserto> readDefeito(String defeito) {
		Query q = manager.query();
		q.constrain(Conserto.class);
		q.descend("defeitos").descend("nome").constrain(defeito).like();
		return q.execute(); 
	}
} // Fim class DAOConserto

