package gr.blxbrgld.dao.hibernate;

import gr.blxbrgld.list.dao.hibernate.CommentDao;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * CommentDaoImpl Tests
 * @author blxbrgld
 */
@Ignore
public class CommentDaoImplTest { //TODO To Be Deleted

    @Autowired
    private CommentDao commentDao;

    @Test
    public void getByTitleTest() {
        assertTrue(commentDao.getByTitle("Documentary").isPresent());
        assertFalse(commentDao.getByTitle("Poetry").isPresent()); //Not Implemented Yet, Maybe In The Future
    }
}
