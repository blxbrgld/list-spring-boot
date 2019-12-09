package gr.blxbrgld.list.dao.hibernate;

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
