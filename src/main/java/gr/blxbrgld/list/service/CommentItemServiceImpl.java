package gr.blxbrgld.list.service;

import gr.blxbrgld.list.dao.hibernate.CommentItemDao;
import gr.blxbrgld.list.model.CommentItem;
import gr.blxbrgld.list.model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * CommentItem's Service Implementation
 * @author blxbrgld
 */
@Service
@Transactional
public class CommentItemServiceImpl implements CommentItemService {

	@Autowired
	private CommentItemDao commentItemDao;

    /**
     * {@inheritDoc}
     */
	@Override
	public void persistCommentItem(CommentItem commentItem) {
		commentItemDao.persist(commentItem);
	}

    /**
     * {@inheritDoc}
     */
	@Override
	public void deleteCommentItem(Item item) {
		commentItemDao.deleteByItem(item);
	}
}
