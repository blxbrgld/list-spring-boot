package gr.blxbrgld.dao.hibernate;

import gr.blxbrgld.list.dao.hibernate.CommentDao;
import gr.blxbrgld.list.dao.hibernate.CommentItemDao;
import gr.blxbrgld.list.dao.hibernate.ItemDao;
import gr.blxbrgld.list.model.Comment;
import org.hibernate.SessionFactory;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * CommentItemDaoImpl Tests
 * @author blxbrgld
 */
@Ignore
public class CommentItemDaoImplTest { //TODO To Be Deleted

    @Autowired
    private CommentItemDao commentItemDao;

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private ItemDao itemDao;

    @Autowired
    private SessionFactory sessionFactory;

    @Test
    public void havingCommentExists() {
        Optional<Comment> comment = commentDao.getByTitle("Documentary");
        assertTrue(commentItemDao.havingCommentExists(comment.get()));
    }

    @Test
    public void deleteByItem() {
        Optional<Comment> comment = commentDao.getByTitle("Single");
        assertTrue(commentItemDao.havingCommentExists(comment.get())); // An Item Exist
        commentItemDao.deleteByItem(itemDao.get(5570).get()); // A Little Cheating Here To Delete The Item

        sessionFactory.getCurrentSession().flush(); // Ensure That Hibernate Session Is Flushed
        assertFalse(commentItemDao.havingCommentExists(comment.get())); // Deleted
    }
}
