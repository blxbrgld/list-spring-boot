package gr.blxbrgld.list.service;

import gr.blxbrgld.list.enums.Order;
import gr.blxbrgld.list.model.Category;
import org.springframework.validation.Errors;

import java.util.List;
import java.util.Optional;

/**
 * Category's Service Interface
 * @author blxbrgld
 */
public interface CategoryService {

	/**
	 * Persist Category Object
	 * @param category Category Object
	 * @param errors BindingResult Errors Of Category Form
	 */
	void persistCategory(Category category, Errors errors);
	
	/**
	 * Merge Category Object
	 * @param category Category Object
	 * @param errors BindingResult Errors Of Category Form
	 */
	void mergeCategory(Category category, Errors errors);
	
	/**
	 * Get All Category Objects
	 * @param attribute Attribute To Order Results By
	 * @param order Ascending or Descending Ordering
	 * @return List Of Categories
	 */
	List<Category> getCategories(String attribute, Order order);

	/**
	 * Get Category Object Given It's Id
	 * @param id Category's Id
	 * @return Category Object
	 */
	Optional<Category> getCategory(Integer id);
	
	/**
	 * Get Category Object Given It's Title
	 * @param title Category's Title
	 * @return Category Object
	 */
	Optional<Category> getCategory(String title);
	
	/**
	 * Delete Category With The Given Id If There Are No Related Item Objects
	 * @param id Category's Id
	 * @return TRUE or FALSE
	 */
	boolean deleteCategory(Integer id);
}
