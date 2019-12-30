package gr.blxbrgld.list.dao.hibernate;

import gr.blxbrgld.list.enums.Order;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * Abstract DAO Interface
 * @param <T> Generic Class
 * @author blxbrgld
 */
public interface AbstractDao<T> {

	/**
	 * Persist Object
	 * @param t Object To Be Persisted
	 */
	void persist(T t);
	
	/**
	 * Merge Object
	 * @param t Object To Be Merged
	 */
	void merge(T t);

	/**
	 * Persist or Merge
	 * @param t Object To Be Persisted Or Merged
	 */
	void persistOrMerge(T t);

	/**
	 * Get Object
	 * @param id The Id
	 * @return The Object
	 */
	Optional<T> get(Serializable id);

	/**
	 * Get Object By It's Title
	 * @param title The Title
	 * @return The Object
	 */
	Optional<T> getByTitle(String title);

	/**
	 * Get all objects
	 * @return List of objects
	 */
	List<T> getAll();

	/**
	 * Get All Objects
	 * @param attribute Attribute To Order Results By
	 * @param order Ascending or Descending Ordering
	 * @return List Of Objects
	 */
	List<T> getAll(String attribute, Order order);

	/**
	 * Get All Objects With Pagination
	 * @param attribute Attribute To Order Results By
	 * @param order Ascending or Descending Ordering
	 * @param first First Page Result
	 * @param size Results Per Page
	 * @return List Of Objects
	 */
	List<T> getAll(String attribute, Order order, int first, int size);

	/**
	 * Delete Object
	 * @param t Object To Be Deleted
	 */
	void delete(T t);
	
	/**
	 * Delete Object By Id
	 * @param id The Id
	 */
	void deleteById(Serializable id);

	/**
	 * Delete object by title
	 * @param title The title
	 */
	void deleteByTitle(String title);

	/**
	 * Count Objects
	 * @return The Count Of Objects
	 */
	Long count();
}