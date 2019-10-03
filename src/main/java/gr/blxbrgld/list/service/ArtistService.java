package gr.blxbrgld.list.service;

import gr.blxbrgld.list.enums.Order;
import gr.blxbrgld.list.model.Artist;

import java.util.List;
import java.util.Optional;

/**
 * Artist's Service Interface
 * @author blxbrgld
 */
public interface ArtistService {

	/**
	 * Persist Or Merge Artist
	 * @param artist {@link Artist}
	 */
	void persistOrMergeArtist(Artist artist);
	
	/**
	 * Get Artist With Pagination
	 * @param attribute Attribute To Order Results By
	 * @param order {@link Order}
	 * @param first First Page Result
	 * @param size Results Per Page
	 * @return List Of {@link Artist}
	 */
	List<Artist> getArtists(String attribute, Order order, int first, int size);
	
	/**
	 * Get Artist Object Given It's Id
	 * @param id Artist's Id
	 * @return Artist Object
	 */
	Optional<Artist> getArtist(Integer id);
	
	/**
	 * Get Artist Object Given It's Title
	 * @param title Artist's Title
	 * @return Artist Object
	 */
	Optional<Artist> getArtist(String title);
	
	/**
	 * Delete Artist By Id
	 * @param id The Id
	 */
	void deleteArtist(Integer id);
	
	/**
	 * Count All Artist Objects
	 * @return Count Of Artists
	 */
	Long countArtists();
	
	/**
	 * Find Last Created Artist
	 * @return Artist Object
	 */
	Artist findLastArtist();
	
	/**
	 * Get A Random Artist
	 * @return {@link Artist}
	 */
	Artist randomArtist();
	
	/**
	 * Create A List<String> Of Artist Titles Containing The Given Term to Accomplish JQuery's Autocomplete Functionality 
	 * @param term Search Term
	 * @return List Of Artist Titles
	 */
	List<String> findArtistsLike(String term);

	/**
	 * Check if an artist with the given title exists and if not create it
	 * @param title The title
	 * @return The {@link Artist} existing or created
	 */
	Artist artistExists(String title);
}