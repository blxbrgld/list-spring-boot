package gr.blxbrgld.list.service;

import gr.blxbrgld.list.enums.Order;
import gr.blxbrgld.list.model.Comment;

import java.util.List;
import java.util.Optional;

/**
 * Comment's Service Interface
 * @author blxbrgld
 */
public interface CommentService {

	/**
	 * Persist Or Merge Comment Object
	 * @param comment Comment Object
	 */
	void persistOrMergeComment(Comment comment);

	/**
	 * Get All Comment Objects
	 * @param attribute Attribute To Order Results By
	 * @param order Ascending or Descending Ordering
	 * @return List Of Comments
	 */
	List<Comment> getComments(String attribute, Order order);
	
	/**
	 * Get Comment Object Given It's Id
	 * @param id Comment's Id
	 * @return Comment Object
	 */
	Optional<Comment> getComment(Integer id);

	/**
	 * Get comment by title
	 * @param title The title
	 * @return {@link Comment}
	 */
	Optional<Comment> getComment(String title);
	
	/**
	 * Delete Comment With The Given Id If There Are No Related Item Objects
	 * @param id Comment's Id
	 */
	void deleteComment(Integer id);
}
