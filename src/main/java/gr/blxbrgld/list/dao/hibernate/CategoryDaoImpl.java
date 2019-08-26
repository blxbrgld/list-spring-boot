package gr.blxbrgld.list.dao.hibernate;

import gr.blxbrgld.list.enums.Order;
import gr.blxbrgld.list.model.Category;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Category's DAO Implementation
 * @author blxbrgld
 */
@Repository
public class CategoryDaoImpl extends AbstractDaoImpl<Category> implements CategoryDao {

	/**
	 * Override AbstractHibernateDao Method To Include Sorting Functionality According To Category's Parent Title
	 * @param attribute Attribute To Order Results By
	 * @param order Ascending or Descending Ordering
	 * @return List Of Categories
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<Category> getAll(String attribute, Order order) {
		//TODO We Have To Order NULLs First Here, Implement + Test
		return super.getAll(attribute, order);
	}

    /**
     * {@inheritDoc}
     */
	@SuppressWarnings("unchecked")
	@Override
	public List<Category> findByParent(Category parent) {
		Query query = getSession().getNamedQuery("findCategoriesByParent");
		query.setParameter("parent", parent);
		return (List<Category>) query.list();
	}
}
