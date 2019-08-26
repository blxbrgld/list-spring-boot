package gr.blxbrgld.list.dao.hibernate;

import gr.blxbrgld.list.model.Role;
import gr.blxbrgld.list.model.User;

import java.util.List;
import java.util.Optional;

/**
 * User's DAO Interface
 * @author blxbrgld
 */
public interface UserDao extends AbstractDao<User> {

	/**
	 * Password-Aware persist Method For User
	 * @param user {@link User}
	 * @param password User's Password To Persist
	 */
	void persist(User user, String password);
	
	/**
	 * Password-Aware merge Method For User
	 * @param user {@link User}
	 * @param password User's Password To Merge
	 */
	void merge(User user, String password);
	
	/**
	 * Find User By Username
	 * @param username User's Username
	 * @return {@link User}
	 */
	Optional<User> findByUsername(String username);
	
	/**
	 * Find User By Email
	 * @param email User's Email
	 * @return {@link User}
	 */
	Optional<User> findByEmail(String email);
	
	/**
	 * Find Users By Role
	 * @param role {@link Role}
	 * @return List Of {@link User}
	 */
	List<User> findByRole(Role role);
	
	/**
	 * Check If Users Having The Given Role Exist
	 * @param role {@link Role}
	 * @return TRUE or FALSE
	 */
	boolean havingRoleExists(Role role);
}