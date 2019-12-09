package gr.blxbrgld.list.service;

import gr.blxbrgld.list.dao.hibernate.CommentDao;
import gr.blxbrgld.list.enums.Order;
import gr.blxbrgld.list.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    /**
     * {@inheritDoc}
     */
	@Override
	public void persistOrMergeComment(Comment comment) {
		commentDao.persistOrMerge(comment);
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
	public Optional<Comment> getComment(String title) {
		return commentDao.getByTitle(title);
	}

    /**
     * {@inheritDoc}
     */
	@Override
	public void deleteComment(Integer id) {
		commentDao.deleteById(id);
	}
}