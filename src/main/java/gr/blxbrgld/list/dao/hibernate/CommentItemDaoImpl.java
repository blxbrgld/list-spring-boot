package gr.blxbrgld.list.dao.hibernate;

import gr.blxbrgld.list.model.Comment;
import gr.blxbrgld.list.model.CommentItem;
import gr.blxbrgld.list.model.Item;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

/**
 * CommentItem's DAO Implementation
 * @author blxbrgld
 */
@Repository
public class CommentItemDaoImpl extends AbstractDaoImpl<CommentItem> implements CommentItemDao {

    /**
     * {@inheritDoc}
     */
	@Override
	public boolean havingCommentExists(Comment comment) {
		Query query = getSession().getNamedQuery("findCommentItemsByComment");
		query.setParameter("comment", comment);
		query.setMaxResults(1);
        return !query.list().isEmpty();
	}

    /**
     * {@inheritDoc}
     */
	@Override
	public void deleteByItem(Item item) {
		Query query = getSession().getNamedQuery("deleteCommentItemByItem");
		query.setParameter("item", item);
		query.executeUpdate();
	}
}
