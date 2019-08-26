package gr.blxbrgld.list.service;

import gr.blxbrgld.list.dao.hibernate.CommentDao;
import gr.blxbrgld.list.dao.hibernate.CommentItemDao;
import gr.blxbrgld.list.enums.Order;
import gr.blxbrgld.list.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * Comment's Service Implementation
 * @author blxbrgld
 */
@Service
@Transactional
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentDao commentDao;

	@Autowired
	private CommentItemDao commentItemDao;

    /**
     * {@inheritDoc}
     */
	@Override
	public void persistComment(Comment comment, Errors errors) {
		validateTitle(comment, errors);
		boolean valid = !errors.hasErrors();
		if(valid) {
            commentDao.persist(comment);
        }
	}

    /**
     * {@inheritDoc}
     */
	@Override
	public void mergeComment(Comment comment, Errors errors) {
		validateTitle(comment, errors);
		boolean valid = !errors.hasErrors();
		if(valid) {
            commentDao.merge(comment);
        }
	}

    /**
     * {@inheritDoc}
     */
	@Override
	public List<Comment> getComments(String attribute, Order order) {
		return commentDao.getAll(attribute, order);
	}

    /**
     * {@inheritDoc}
     */
	@Override
	public Optional<Comment> getComment(Integer id) {
		return commentDao.get(id);
	}

    /**
     * {@inheritDoc}
     */
	@Override
	public boolean deleteComment(Integer id) {
		Optional<Comment> comment = commentDao.get(id);
		if(comment.isPresent() && !commentItemDao.havingCommentExists(comment.get())) { // No CommentItems With This Comment Exist
			commentDao.deleteById(id);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Validate Uniqueness Of Comment's Title
	 * @param comment Comment Object
	 * @param errors BindingResult Errors Of Comment Form
	 */
	private void validateTitle(Comment comment, Errors errors) {
		Optional<Comment> existing = commentDao.getByTitle(comment.getTitle());
		if(existing.isPresent() && !existing.get().getId().equals(comment.getId()) ) {
			errors.rejectValue("title", "error.duplicate");
		}
	}
}