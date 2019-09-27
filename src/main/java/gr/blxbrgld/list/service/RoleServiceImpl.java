package gr.blxbrgld.list.service;

import gr.blxbrgld.list.dao.hibernate.RoleDao;
import gr.blxbrgld.list.dao.hibernate.UserDao;
import gr.blxbrgld.list.enums.Order;
import gr.blxbrgld.list.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	public void persistOrMergeRole(Role role) {
		roleDao.persistOrMerge(role);
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
	public Optional<Role> getRole(String title) {
		return roleDao.getByTitle(title);
	}

    /**
     * {@inheritDoc}
     */
	@Override
	public void deleteRole(Integer id) {
		roleDao.deleteById(id);
	}
}