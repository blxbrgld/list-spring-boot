package gr.blxbrgld.dao.hibernate;

import gr.blxbrgld.list.dao.hibernate.ActivityDao;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * ActivityDaoImpl Tests
 * @author blxbrgld
 */
@Ignore
public class ActivityDaoImplTest { //TODO To Be Deleted

    @Autowired
    private ActivityDao activityDao;

    @Test
    public void getByTitleTest() {
        assertTrue(activityDao.getByTitle("Musician").isPresent());
        assertFalse(activityDao.getByTitle("Author").isPresent()); // Not Implemented Yet, Maybe In The Future
    }
}
