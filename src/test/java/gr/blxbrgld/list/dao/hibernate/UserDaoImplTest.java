package gr.blxbrgld.list.dao.hibernate;

import gr.blxbrgld.list.model.Role;
import gr.blxbrgld.list.model.User;
import org.hibernate.SessionFactory;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * UserDaoImpl Tests
 * @author blxbrgld
 */
@Ignore
public class UserDaoImplTest { //TODO To Be Deleted

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private SessionFactory sessionFactory;

    @Test
    public void getAll() {
        List<User> userList = userDao.getAll(null, null); // There's Only One User, We Can Not Test Ordering Functionality
        assertEquals(1, userList.size());
    }

    @Test
    public void persist() {
        Optional<Role> role = roleDao.getByTitle("Viewer");
        assertTrue(role.isPresent());
        User user = new User();
        user.setUsername("username");
        user.setEmail("email@email.com");
        user.setRole(role.get());
        user.setEnabled(true);
        userDao.persist(user, "password");

        sessionFactory.getCurrentSession().flush(); // Ensure That Hibernate Session Is Flushed
        List<User> userList = userDao.findByRole(role.get());
        assertEquals(1, userList.size());
    }

    @Test
    public void merge() {
        Role role = roleDao.getByTitle("Viewer").get();
        User user = userDao.findByUsername("blixabargeld").get();
        user.setRole(role); //Change User's Role
        userDao.merge(user, "password");

        sessionFactory.getCurrentSession().flush(); // Ensure That Hibernate Session Is Flushed
        List<User> userList = userDao.findByRole(role);
        assertEquals(1, userList.size());
    }

    @Test
    public void findByUsername() {
        Optional<User> user = userDao.findByUsername("blixabargeld");
        assertTrue(user.isPresent());
        assertEquals("nikolaos.i.papadopoulos@gmail.com", user.get().getEmail());
    }

    @Test
    public void findByEmail() {
        Optional<User> user = userDao.findByEmail("nikolaos.i.papadopoulos@gmail.com");
        assertTrue(user.isPresent());
        assertEquals("blixabargeld", user.get().getUsername());
    }

    @Test
    public void findByRole() {
        List<User> userList = userDao.findByRole(roleDao.getByTitle("Administrator").get());
        assertEquals(1, userList.size());
    }

    @Test
    public void havingRoleExists() {
        Optional<Role> role = roleDao.getByTitle("Administrator");
        assertTrue(role.isPresent());
        assertTrue(userDao.havingRoleExists(role.get())); // We Have One Administrator
        role = roleDao.getByTitle("Viewer");
        assertFalse(userDao.havingRoleExists(role.get())); // No Viewers Exist
    }
}
