package gr.blxbrgld.list.dao.hibernate;

import gr.blxbrgld.list.model.Activity;
import gr.blxbrgld.list.model.Artist;
import gr.blxbrgld.list.model.ArtistActivityItem;
import gr.blxbrgld.list.model.Item;

/**
 * ArtistActivityItem's DAO Interface
 * @author blxbrgld
 */
public interface ArtistActivityItemDao extends AbstractDao<ArtistActivityItem> {

	/**
	 * Count All ArtistActivityItem Having The Given Artist
	 * @param artist {@link Artist}
	 * @return Count Of {@link ArtistActivityItem}
	 */
	Long countArtistActivityItems(Artist artist);
	
	/**
	 * Check If ArtistActivityItem Having The Given Activity Exist
	 * @param activity {@link Activity}
	 * @return TRUE or FALSE
	 */
	boolean havingActivityExists(Activity activity);
	
	/**
	 * Check If ArtistActivityItem Having The Given Artist Exist
	 * @param artist {@link Artist}
	 * @return TRUE or FALSE
	 */
	boolean havingArtistExists(Artist artist);
	
	/**
	 * Delete ArtistActivityItem By Item
	 * @param item {@link Item}
	 */
	void deleteByItem(Item item);
}
