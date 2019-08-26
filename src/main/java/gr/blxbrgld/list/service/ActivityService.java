package gr.blxbrgld.list.service;

import gr.blxbrgld.list.enums.Order;
import gr.blxbrgld.list.model.Activity;

import java.util.List;
import java.util.Optional;

/**
 * Activity's Service Interface
 * @author blxbrgld
 */
public interface ActivityService {

	/**
	 * Persist Or Merge Activity Object
	 * @param activity {@link Activity}
	 */
	void persistOrMergeActivity(Activity activity);
		
	/**
	 * Get All Activity Objects
	 * @param attribute Attribute To Order Results By
	 * @param order Ascending or Descending Ordering
	 * @return List Of Activities
	 */
	List<Activity> getActivities(String attribute, Order order);
	
	/**
	 * Get Activity Object Given It's Id
	 * @param id Activity's Id
	 * @return Activity Object
	 */
	Optional<Activity> getActivity(Integer id);
	
	/**
	 * Delete Activity By Id
	 * @param id The Id
	 */
	void deleteActivity(Integer id);
	
	/**
	 * Count All Activity Objects
	 * @return Count Of Activities
	 */
	Long countActivities();
}