package gr.blxbrgld.list.service;

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

    /**
     * {@inheritDoc}
     */
	@Override
	public void persistUser(User user, String password, Errors errors) {
		validateUsername(user, errors);
		validateEmail(user, errors);
		boolean valid = !errors.hasErrors();
		if(valid) {
            userDao.persist(user, password);
        }
	}

    /**
     * {@inheritDoc}
     */
	@Override
	public void mergeUser(User user, String password, Errors errors) {
		validateUsername(user, errors);
		validateEmail(user, errors);
		boolean valid = !errors.hasErrors();
		if(valid) {
            userDao.merge(user, password);
        }
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
	public void deleteUser(Integer id) {
		userDao.deleteById(id);
	}
	
	/**
     * {@inheritDoc}
	 */
    @Override
	public Optional<User> getUserByUsername(String username) {
		return userDao.findByUsername(username);		
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