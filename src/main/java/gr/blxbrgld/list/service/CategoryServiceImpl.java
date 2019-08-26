package gr.blxbrgld.list.service;

import gr.blxbrgld.list.dao.hibernate.CategoryDao;
import gr.blxbrgld.list.dao.hibernate.ItemDao;
import gr.blxbrgld.list.enums.Order;
import gr.blxbrgld.list.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * Category's Service Implementation
 * @author blxbrgld
 */
@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryDao categoryDao;

	@Autowired
	private ItemDao itemDao;

    /**
     * {@inheritDoc}
     */
	@Override
	public void persistCategory(Category category, Errors errors) {
		validateTitle(category, errors);
		//validateParent() Is Not Needed On persistCategory()
		boolean valid = !errors.hasErrors();
		if(valid) {
            categoryDao.persist(category);
        }
	}

    /**
     * {@inheritDoc}
     */
	@Override
	public void mergeCategory(Category category, Errors errors) {
		validateTitle(category, errors);
		validateParent(category, errors);
		boolean valid = !errors.hasErrors();
		if(valid) {
            categoryDao.merge(category);
        }
	}

    /**
     * {@inheritDoc}
     */
	@Override
	public List<Category> getCategories(String attribute, Order order) {
		return categoryDao.getAll(attribute, order);
	}

    /**
     * {@inheritDoc}
     */
	@Override
	public Optional<Category> getCategory(Integer id) {
		return categoryDao.get(id);
	}

    /**
     * {@inheritDoc}
     */
	@Override
	public Optional<Category> getCategory(String title) {
		return categoryDao.getByTitle(title);
	}

    /**
     * {@inheritDoc}
     */
	@Override
	public boolean deleteCategory(Integer id) {
		Optional<Category> category = categoryDao.get(id);
		if(category.isPresent() && categoryDao.findByParent(category.get()).isEmpty() && !itemDao.havingCategoryExists(category.get())) { // No Categories With This Parent or Items With This Category Exist
			categoryDao.deleteById(id);
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Validate Uniqueness Of Category's Title
	 * @param category Category Object
	 * @param errors BindingResult Errors Of Category Form
	 */
	private void validateTitle(Category category, Errors errors) {
		Optional<Category> existing = categoryDao.getByTitle(category.getTitle());
		if(existing.isPresent() && !existing.get().getId().equals(category.getId()) ) { // Duplicate Title
			errors.rejectValue("title", "error.duplicate");
		}
	}
	
	/**
	 * Ensure That Given Category Title and Parent Category Are Not The Same
	 * @param category Category Object
	 * @param errors BindingResult Errors Of Category Form
	 */
	private void validateParent(Category category, Errors errors) {
		if(category.getParent() != null && category.getTitle().equals(category.getParent().getTitle()))
			errors.rejectValue("parent", "error.inheritance");		
	}
}