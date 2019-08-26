package gr.blxbrgld.list.service;

import gr.blxbrgld.list.enums.Order;
import gr.blxbrgld.list.model.Role;
import org.springframework.validation.Errors;

import java.util.List;
import java.util.Optional;

/**
 * Role's Service Interface
 * @author blxbrgld
 */
public interface RoleService {

	/**
	 * Persist Role Object
	 * @param role Role Object
	 * @param errors BindingResult Errors Of Role Form
	 */
	void persistRole(Role role, Errors errors);
	
	/**
	 * Merge Role Object
	 * @param role Role Object
	 * @param errors BindingResult Errors Of Role Form
	 */
	void mergeRole(Role role, Errors errors);
	
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
	 * Delete Role With The Given Id If There Are No Related User Objects
	 * @param id Role's Id
	 * @return TRUE or FALSE
	 */
	boolean deleteRole(Integer id);
}
