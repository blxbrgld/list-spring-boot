package gr.blxbrgld.list.service;

import gr.blxbrgld.list.model.CommentItem;
import gr.blxbrgld.list.model.Item;

/**
 * CommentItem's Service Interface
 * @author blxbrgld
 */
public interface CommentItemService {

	/**
	 * Persist CommentItem Object
	 * @param commentItem CommentItem Object
	 */
	void persistCommentItem(CommentItem commentItem);

	/**
	 * Delete CommentItem Objects Given The Related Item Object
	 * @param item Item Object
	 */
	void deleteCommentItem(Item item);
}
