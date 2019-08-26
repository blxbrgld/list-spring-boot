package gr.blxbrgld.list.service;

import gr.blxbrgld.list.enums.Order;
import gr.blxbrgld.list.model.Subtitles;

import java.util.List;
import java.util.Optional;

/**
 * Subtitles' Service Interface
 * @author blxbrgld
 */
public interface SubtitlesService {

	/**
	 * Persist Or Merge Subtitles Object
	 * @param subtitles Subtitles Object
	 */
	void persistOrMergeSubtitles(Subtitles subtitles);
	
	/**
	 * Get All Subtitles Objects
	 * @param attribute Property To Order Results By
	 * @param order Ascending or Descending Ordering
	 * @return List of Subtitles
	 */
	List<Subtitles> getSubtitles(String attribute, Order order);
	
	/**
	 * Get Subtitles Object Given It's Id
	 * @param id Subtitles' Id
	 * @return Subtitles Object
	 */
	Optional<Subtitles> getSubtitles(Integer id);
	
	/**
	 * Delete Subtitles By Id
	 * @param id The Id
	 */
	void deleteSubtitles(Integer id);
}
