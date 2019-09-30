package gr.blxbrgld.list.dao.hibernate;

import gr.blxbrgld.list.enums.Order;
import gr.blxbrgld.list.model.*;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.List;

/**
 * Item's DAO Interface
 * @author blxbrgld
 */
public interface ItemDao extends AbstractDao<Item> {

	/**
	 * Get All Category's Items. No Paging Is Required because The Method Is Only Used For .XLS Export
	 * @param category {@link Category}
	 * @return List Of {@link Item}
	 */
	List<Item> findByCategory(Category category);
	
	/**
	 * Find All Item's Related With The Given Artist
	 * @param artist {@link Artist}
	 * @param first First Page Result
	 * @param size Results Per Page
	 * @return List Of {@link Item}
	 */
	List<Item> findByArtist(Artist artist, int first, int size);
	
	/**
	 * Search For Items
	 * @param searchFor Search Term
	 * @param searchIn Limit Results Setting This Parameter to 'Music', 'Films' etc.
	 * @param attribute Property To Order Results By
	 * @param order Ascending or Descending Ordering
	 * @param first First Page Result
	 * @param size Results Per Page
	 * @return ImmutablePair Containing The Number Of Results As The Key And The Actual Results As The Value
	 */
	ImmutablePair<Integer, List<Item>> search(String searchFor, String searchIn, String attribute, Order order, int first, int size);
	
	/**
	 * Check If Items Having The Given Category Exist
	 * @param category {@link Category}
	 * @return TRUE or FALSE
	 */
	boolean havingCategoryExists(Category category);
	
	/**
	 * Check If Items Having The Given Subtitles Exist
	 * @param subtitles {@link Subtitles}
	 * @return TRUE or FALSE
	 */
	boolean havingSubtitlesExists(Subtitles subtitles);

	/**
	 * Check If Items Having The Given Publisher Exist
	 * @param publisher {@link Publisher}
	 * @return TRUE or FALSE
	 */
	boolean havingPublisherExists(Publisher publisher);

	/**
	 * Count All Items Existing In Database Having The Given Category
	 * @param title Category's Title
	 * @return Count Of Items
	 */
	Long countItems(String title);

	/**
	 * Delete an item given it's english title
	 * @param titleEng The english title
	 */
	void deleteByTitleEng(String titleEng);
}
