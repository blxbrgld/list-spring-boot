package gr.blxbrgld.list.dao.hibernate;

import gr.blxbrgld.list.model.Role;
import gr.blxbrgld.list.model.User;
import org.hibernate.query.Query;
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
	public void deleteByUsername(String username) {
		findByUsername(username).ifPresent(this::delete);
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