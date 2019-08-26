package gr.blxbrgld.list.dao.hibernate;

import gr.blxbrgld.list.enums.Order;
import gr.blxbrgld.list.model.Role;
import gr.blxbrgld.list.model.User;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * User's DAO Implementation
 * @author blxbrgld
 */
@Repository
@SuppressWarnings("JpaQueryApiInspection")
public class UserDaoImpl extends AbstractDaoImpl<User> implements UserDao {

	private static final String UPDATE_QUERY = "UPDATE Users SET Password = ? WHERE Username = ?";
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	//@Autowired
	//private BCryptPasswordEncoder bCryptPasswordEncoder;

	/**
	 * Override AbstractHibernateDao Method To Include Sorting Functionality According To User's Role Title
	 * @param attribute Attribute To Order Results By
	 * @param order Ascending or Descending Ordering
	 * @return List Of Users
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<User> getAll(String attribute, Order order) {
		//TODO We Have To Order NULLs First Here, Implement + Test
		return super.getAll(attribute, order);
	}

    /**
     * {@inheritDoc}
     */
	@Override
	public void persist(User user, String password) {
		persist(user); //With Hibernate
		String encodedPassword = password; //TODO Password Encoder
		//String encodedPassword = bCryptPasswordEncoder.encode(password);
		jdbcTemplate.update(UPDATE_QUERY, encodedPassword, user.getUsername()); // With JDBC
	}

    /**
     * {@inheritDoc}
     */
	@Override
	public void merge(User user, String password) {
		merge(user); //With Hibernate
		String encodedPassword = password; //TODO Password Encoder
		//String encodedPassword = bCryptPasswordEncoder.encode(password);
		jdbcTemplate.update(UPDATE_QUERY, encodedPassword, user.getUsername()); // With JDBC
	}

    /**
     * {@inheritDoc}
     */
	@Override
	public Optional<User> findByUsername(String username) {
		Query query = getSession().getNamedQuery("findUserByUsername");
		query.setParameter("username", username);
		return Optional.ofNullable((User) query.uniqueResult());
	}

    /**
     * {@inheritDoc}
     */
	@Override
	public Optional<User> findByEmail(String email) {
		Query query = getSession().getNamedQuery("findUserByEmail");
		query.setParameter("email", email);
		return Optional.ofNullable((User) query.uniqueResult());
	}

    /**
     * {@inheritDoc}
     */
	@SuppressWarnings("unchecked")
	@Override
	public List<User> findByRole(Role role) {
		Query query = getSession().getNamedQuery("findUsersByRole");
		query.setParameter("role", role);
		return (List<User>) query.list();
	}

    /**
     * {@inheritDoc}
     */
	@Override
	public boolean havingRoleExists(Role role) {
		Query query = getSession().getNamedQuery("findUsersByRole");
		query.setParameter("role", role);
		query.setMaxResults(1);
        return !query.list().isEmpty();
	}
}