package gr.blxbrgld.dao.hibernate;

import gr.blxbrgld.list.dao.hibernate.RoleDao;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertTrue;

/**
 * RoleDaoImpl Tests
 * @author blxbrgld
 */
@Ignore
public class RoleDaoImplTest { //TODO To Be Deleted

    @Autowired
    private RoleDao roleDao;

    @Test
    public void getByTitleTest() {
        assertTrue(roleDao.getByTitle("Administrator").isPresent());
        assertTrue(roleDao.getByTitle("administrator").isPresent()); // Query Is Case Sensitive, No Results
    }
}
