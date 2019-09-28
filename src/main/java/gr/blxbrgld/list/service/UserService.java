package gr.blxbrgld.list.service;

import gr.blxbrgld.list.enums.Order;
import gr.blxbrgld.list.model.User;

import java.util.List;
import java.util.Optional;

/**
 * User's Service Interface
 * @author blxbrgld
 */
public interface UserService {

	/**
	 * Persist User Object
	 * @param user User Object
	 */
	void persistOrMergeUser(User user);
	
	/**
	 * Get All User Objects
	 * @param attribute Attribute To Order Results By
	 * @param order Ascending or Descending Ordering
	 * @return List Of Users
	 */
	List<User> getUsers(String attribute, Order order);
	
	/**
	 * Get User By Id
	 * @param id The Id
	 * @return {@link User}
	 */
	Optional<User> getUser(Integer id);

	/**
	 * Get user by username
	 * @param username The username
	 * @return {@link User}
	 */
	Optional<User> getUser(String username);

	/**
	 * Delete User By Id
	 * @param id The Id
	 */
	void deleteUser(Integer id);
}
