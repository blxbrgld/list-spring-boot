package gr.blxbrgld.list.service;

import gr.blxbrgld.list.enums.CategoryFilter;
import gr.blxbrgld.list.model.Category;

import java.util.List;
import java.util.Optional;

/**
 * Category's Service Interface
 * @author blxbrgld
 */
public interface CategoryService {

	/**
	 * Persist Or Merge Category
	 * @param category {@link Category}
	 */
	void persistOrMergeCategory(Category category);

	/**
	 * Get list of categories
	 * @param filter {@link CategoryFilter}
	 * @return List of {@link Category}
	 */
	List<Category> getCategories(CategoryFilter filter);

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
	 */
	void deleteCategory(Integer id);
}
