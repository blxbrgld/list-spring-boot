package gr.blxbrgld.list.dao.hibernate;

import gr.blxbrgld.list.model.Comment;
import gr.blxbrgld.list.model.CommentItem;
import gr.blxbrgld.list.model.Item;

/**
 * CommentItem's DAO Interface
 * @author blxbrgld
 */
public interface CommentItemDao extends AbstractDao<CommentItem> {

	/**
	 * Check If CommentItem Having The Given Comment Exist
	 * @param comment {@link Comment}
	 * @return TRUE or FALSE
	 */
	boolean havingCommentExists(Comment comment);
	
	/**
	 * Delete CommentItem By Item
	 * @param item {@link Item}
	 */
	void deleteByItem(Item item);
}
