package gr.blxbrgld.list.service;

import gr.blxbrgld.list.enums.Order;
import gr.blxbrgld.list.model.Role;

import java.util.List;
import java.util.Optional;

/**
 * Role's Service Interface
 * @author blxbrgld
 */
public interface RoleService {

	/**
	 * Persist Or Merge Role Object
	 * @param role Role Object
	 */
	void persistOrMergeRole(Role role);
	
	/**
	 * Get All Role Objects
	 * @param attribute Attribute To Order Results By
	 * @param order Ascending or Descending Ordering
	 * @return List Of Roles
	 */
	List<Role> getRoles(String attribute, Order order);
	
	/**
	 * Get Role Object Given It's Id
	 * @param id Role's Id
	 * @return Role Object
	 */
	Optional<Role> getRole(Integer id);

	/**
	 * Get Role Object Given It's Title
	 * @param title Role's Title
	 * @return Role Object
	 */
	Optional<Role> getRole(String title);

	/**
	 * Delete Role With The Given Id If There Are No Related User Objects
	 * @param id Role's Id
	 */
	void deleteRole(Integer id);
}
