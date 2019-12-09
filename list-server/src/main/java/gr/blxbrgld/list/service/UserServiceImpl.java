package gr.blxbrgld.list.service;

import gr.blxbrgld.list.dao.hibernate.RoleDao;
import gr.blxbrgld.list.dao.hibernate.UserDao;
import gr.blxbrgld.list.enums.Order;
import gr.blxbrgld.list.model.User;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * User's Service Implementation
 * @author blxbrgld
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private RoleDao roleDao;

    /**
     * {@inheritDoc}
     */
	@Override
	public void persistOrMergeUser(User user) {
		String encodedPassword = user.getPassword(); //TODO bCryptPasswordEncoder.encode
		user.setPassword(encodedPassword);
		//noinspection ConstantConditions
		user.setRole(roleDao.getByTitle(user.getRole().getTitle()).get());
		userDao.persistOrMerge(user);
	}

    /**
     * {@inheritDoc}
     */
	@Override
	public List<User> getUsers(String attribute, Order order) {
		return userDao.getAll(attribute, order);
	}

    /**
     * {@inheritDoc}
     */
	@Override
	public Optional<User> getUser(Integer id) {
		return userDao.get(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Optional<User> getUser(String username) {
		return userDao.findByUsername(username);
	}

	/**
     * {@inheritDoc}
     */
	@Override
	public void deleteUser(Integer id) {
		userDao.deleteById(id);
	}
	
	/**
	 * Validate Uniqueness Of User's Username
	 * @param user User Object
	 * @param errors BindingResult Errors Of User Form
	 */
	private void validateUsername(User user, Errors errors) {
		Optional<User> existing = userDao.findByUsername(user.getUsername());
		if(existing.isPresent() && !existing.get().getId().equals(user.getId())) { // Duplicate Username
			errors.rejectValue("username", "error.duplicate");
		}
	}
	
	/**
	 * Validate Uniqueness Of User's Email
	 * @param user User Object
	 * @param errors BindingResult Errors Of User Form
	 */
	private void validateEmail(User user, Errors errors) {
		Optional<User> existing = userDao.findByEmail(user.getEmail());
		if(existing.isPresent() && !existing.get().getId().equals(user.getId())) { // Duplicate Email
			errors.rejectValue("email", "error.duplicate");
		}
	}
}