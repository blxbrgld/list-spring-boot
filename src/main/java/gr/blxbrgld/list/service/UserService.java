package gr.blxbrgld.list.service;

import gr.blxbrgld.list.enums.Order;
import gr.blxbrgld.list.model.User;
import org.springframework.validation.Errors;

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
	 * @param password User's Password
	 * @param errors BindingResult Errors Of User Form
	 */
	void persistUser(User user, String password, Errors errors);
	
	/**
	 * Merge User Object
	 * @param user User Object
	 * @param password User's Password
	 * @param errors BindingResult Errors Of User Form
	 */
	void mergeUser(User user, String password, Errors errors);
	
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
	 * Delete User By Id
	 * @param id The Id
	 */
	void deleteUser(Integer id);
	
	/**
	 * Get User Object Given It's Username
	 * @param username User's Username
	 * @return User Object
	 */
	Optional<User> getUserByUsername(String username);
}
