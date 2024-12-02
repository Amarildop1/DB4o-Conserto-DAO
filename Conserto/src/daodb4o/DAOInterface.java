package daodb4o;
/**
 * IFPB - SI
 * Disciplina: Persistencia de Objetos
 */
import java.util.List;

public interface DAOInterface<T> {
	public void create(T obj);
	public T update(T obj);
	public void delete(T obj) ;
	public List<T> readAll();
}
