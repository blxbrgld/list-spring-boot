package gr.blxbrgld.list.service;

import gr.blxbrgld.list.dao.hibernate.ActivityDao;
import gr.blxbrgld.list.enums.Order;
import gr.blxbrgld.list.model.Activity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * Activity's Service Implementation
 * @author blxbrgld
 */
@Service
@Transactional
public class ActivityServiceImpl implements ActivityService {

	@Autowired
	private ActivityDao activityDao;

    /**
     * {@inheritDoc}
     */
	@Override
	public void persistOrMergeActivity(Activity activity) {
		activityDao.persistOrMerge(activity);
	}

    /**
     * {@inheritDoc}
     */
	@Override
	public List<Activity> getActivities(String attribute, Order order) {
		return activityDao.getAll(attribute, order);
	}

    /**
     * {@inheritDoc}
     */
	@Override
	public Optional<Activity> getActivity(Integer id) {
		return activityDao.get(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Optional<Activity> getActivity(String title) {
		return activityDao.getByTitle(title);
	}

    /**
     * {@inheritDoc}
     */
	@Override
	public void deleteActivity(Integer id) {
		activityDao.deleteById(id);
	}

    /**
     * {@inheritDoc}
     */
	@Override
	public Long countActivities() {
		return activityDao.count();
	}
}
