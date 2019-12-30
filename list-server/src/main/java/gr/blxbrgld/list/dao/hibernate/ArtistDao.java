package gr.blxbrgld.list.dao.hibernate;

import gr.blxbrgld.list.model.Artist;

import java.util.List;

/**
 * Artist's DAO Interface
 * @author blxbrgld
 */
public interface ArtistDao extends AbstractDao<Artist> {

	/**
	 * Find Last Created Artist
	 * @return {@link Artist}
	 */
	Artist findLast();
	
	/**
	 * Find A Random Artist
	 * @return {@link Artist}
	 */
	Artist findRandom();
	
	/**
	 * Find Artists With Title Containing The Given Search Term
	 * @param term The Search Term
	 * @return List Of {@link Artist}
	 */
	List<Artist> findLike(String term);
}
