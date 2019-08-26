package gr.blxbrgld.list.service;

import gr.blxbrgld.list.enums.Order;
import gr.blxbrgld.list.model.Comment;
import org.springframework.validation.Errors;

import java.util.List;
import java.util.Optional;

/**
 * Comment's Service Interface
 * @author blxbrgld
 */
public interface CommentService {

	/**
	 * Persist Comment Object
	 * @param comment Comment Object
	 * @param errors BindingResult Errors Of Comment Form
	 */
	void persistComment(Comment comment, Errors errors);
	
	/**
	 * Merge Comment Object
	 * @param comment Comment Object
	 * @param errors BindingResult Errors Of Comment Form
	 */
	void mergeComment(Comment comment, Errors errors);
	
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
	 * Delete Comment With The Given Id If There Are No Related Item Objects
	 * @param id Comment's Id
	 * @return TRUE or FALSE
	 */
	boolean deleteComment(Integer id);
}
