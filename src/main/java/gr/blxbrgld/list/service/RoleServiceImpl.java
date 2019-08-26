package gr.blxbrgld.list.service;

import gr.blxbrgld.list.dao.hibernate.RoleDao;
import gr.blxbrgld.list.dao.hibernate.UserDao;
import gr.blxbrgld.list.enums.Order;
import gr.blxbrgld.list.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * Role's Service Implementation
 * @author blxbrgld
 */
@Service
@Transactional
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleDao roleDao;

	@Autowired
	private UserDao userDao;

    /**
     * {@inheritDoc}
     */
	@Override
	public void persistRole(Role role, Errors errors) {
		validateRole(role, errors);
		boolean valid = !errors.hasErrors();
		if(valid) {
            roleDao.persist(role);
        }
	}

    /**
     * {@inheritDoc}
     */
	@Override
	public void mergeRole(Role role, Errors errors) {
		validateRole(role, errors);
		boolean valid = !errors.hasErrors();
		if(valid) {
            roleDao.merge(role);
        }
	}

    /**
     * {@inheritDoc}
     */
	@Override
	public List<Role> getRoles(String attribute, Order order) {
		return roleDao.getAll(attribute, order);
	}

    /**
     * {@inheritDoc}
     */
	@Override
	public Optional<Role> getRole(Integer id) {
		return roleDao.get(id);
	}

    /**
     * {@inheritDoc}
     */
	@Override
	public boolean deleteRole(Integer id) {
		Optional<Role> role = roleDao.get(id);
		if(role.isPresent() && !userDao.havingRoleExists(role.get())) { // No Users With This Role Exist
			roleDao.deleteById(id);
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Validate Uniqueness Of Role's Title
	 * @param role Role Object
	 * @param errors BindingResult Errors Of Role Form
	 */
	private void validateRole(Role role, Errors errors) {
		Optional<Role> existing = roleDao.getByTitle(role.getTitle());
		if(existing.isPresent() && !existing.get().getId().equals(role.getId()) ) {
			errors.rejectValue("title", "error.duplicate");
		}
	}	
}