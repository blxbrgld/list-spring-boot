package gr.blxbrgld.list.service;

import gr.blxbrgld.list.dao.hibernate.CategoryDao;
import gr.blxbrgld.list.enums.CategoryFilter;
import gr.blxbrgld.list.enums.Order;
import gr.blxbrgld.list.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Category's Service Implementation
 * @author blxbrgld
 */
@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryDao categoryDao;

	private static final String DEFAULT_ORDER = "title";

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void persistOrMergeCategory(Category category) {
		Category parent = category.getParent(); // If present, it's a transient object created from the deserializer and needs to be fetched from the database
		if(parent!=null && parent.getTitle()!=null) {
			category.setParent(categoryDao.getByTitle(parent.getTitle()).get());
		}
		categoryDao.persistOrMerge(category);
	}

    /**
     * {@inheritDoc}
     */
	@Override
	public List<Category> getCategories(CategoryFilter filter) {
		List<Category> categories = categoryDao.getAll(DEFAULT_ORDER, Order.ASC);
		switch (filter) {
			case MENU:
				return categories.stream().filter(c -> c.getParent() == null).collect(Collectors.toList());
			case DROPDOWN:
				// For the dropdown menu, we return categories that have parent or categories that do not have parent but either have children
				return categories
					.stream()
					.filter(c -> c.getParent() != null || (c.getParent() == null && c.getCategories().size() == 0))
					.sorted(
						Comparator.comparing(
							c -> c.getParent()!=null ? c.getParent().getTitle() : c.getTitle()
						)
					)
					.collect(Collectors.toList());
			case NONE:
			default:
				return categories;
		}
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
	public void deleteCategory(Integer id) {
		categoryDao.deleteById(id);
	}
}