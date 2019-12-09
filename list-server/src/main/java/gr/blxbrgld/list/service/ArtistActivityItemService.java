package gr.blxbrgld.list.service;

import gr.blxbrgld.list.model.Artist;
import gr.blxbrgld.list.model.ArtistActivityItem;
import gr.blxbrgld.list.model.Item;

/**
 * ArtistActivityItem's Service Interface
 * @author blxbrgld
 */
public interface ArtistActivityItemService {

	/**
	 * Persist ArtistActivityItem Object
	 * @param artistActivityItem ArtistActivityItem Object
	 */
	void persistArtistActivityItem(ArtistActivityItem artistActivityItem);
	
	/**
	 * Delete ArtistActivityItem Objects Given The Related Item Object
	 * @param item Item Object
	 */
	void deleteArtistActivityItem(Item item);
	
	/**
	 * Count All ArtistActivityItem Objects Having The Given Artist Object
	 * @param artist Artist Object
	 * @return Count Of ArtistActivityItems
	 */
	Long countArtistActivityItemsHavingArtist(Artist artist);
}
