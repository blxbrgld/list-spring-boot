package gr.blxbrgld.list.service;

import gr.blxbrgld.list.enums.Order;
import gr.blxbrgld.list.model.Artist;
import gr.blxbrgld.list.model.Category;
import gr.blxbrgld.list.model.Item;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.List;
import java.util.Optional;

/**
 * Item's Service Interface
 * @author blxbrgld
 */
public interface ItemService {

	/**
	 * Persist Item Object
	 * @param item Item Object
	 */
	void persistOrMergeItem(Item item);

	/**
	 * Get Item Object Given It's Id
	 * @param id Item's Id
	 * @return Item Object
	 */
	Optional<Item> getItem(Integer id);

	/**
	 * Get item by english title. The method is used only for the E2E tests
	 * @param titleEng The english title
	 * @return {@link Item}
	 */
	Optional<Item> getItem(String titleEng);

	/**
	 * Get All Item Objects
	 * @param attribute Attribute To Order Results By
	 * @param order Ascending or Descending Ordering
	 * @param first First Page Result
	 * @param size Results Per Page
	 * @return List Of Items
	 */
	List<Item> getItems(String attribute, Order order, int first, int size);

	/**
	 * Get All Items Having The Given Category
	 * @param category Category Object
	 * @return List Of Items
	 */
	List<Item> getItemsHavingCategory(Category category);
	
	/**
	 * Get All Items Related To The Given Artist
	 * @param artist Artist Object
	 * @param first First Page Result
	 * @param size Results Per Page
	 * @return List Of Items
	 */
	List<Item> getItemsHavingArtist(Artist artist, int first, int size);
	
	/**
	 * Search For Items
	 * @param searchFor Search Term
	 * @param searchIn Limit Results Setting This Parameter to 'Music', 'Films' etc.
	 * @param attribute Attribute To Order Results By
	 * @param order Ascending or Descending Ordering
	 * @param first First Page Result
	 * @param size Results Per Page
	 * @return ImmutablePair Containing The Number Of Results As The Key And The Actual Results As The Value
	 */
	ImmutablePair<Integer, List<Item>> searchItems(String searchFor, String searchIn, String attribute, Order order, int first, int size);
	
	/**
	 * Delete item by id
	 * @param id The id
	 */
	void deleteItem(Integer id);
	
	/**
	 * Count All Item Objects
	 * @return Count Of Items
	 */
	Long countItems();
	
	/**
	 * Count All Items Having The Given Category
	 * @param title Category's Title
	 * @return Count Of Items
	 */
	Long countItemsHavingCategory(String title);
}
