package gr.blxbrgld.list.dao.hibernate;

import gr.blxbrgld.list.model.Category;

import java.util.List;

/**
 * Category's DAO Interface
 * @author blxbrgld
 */
public interface CategoryDao extends AbstractDao<Category> {

	/**
	 * Find Categories Having The Given Parent
	 * @param parent Parent {@link Category}
	 * @return List Of {@link Category}
	 */
	List<Category> findByParent(Category parent);
}
