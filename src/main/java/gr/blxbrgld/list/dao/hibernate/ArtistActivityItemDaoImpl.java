package gr.blxbrgld.list.dao.hibernate;

import gr.blxbrgld.list.model.Activity;
import gr.blxbrgld.list.model.Artist;
import gr.blxbrgld.list.model.ArtistActivityItem;
import gr.blxbrgld.list.model.Item;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

/**
 * ArtistActivityItem's DAO Implementation
 * @author blxbrgld
 */
@Repository
public class ArtistActivityItemDaoImpl extends AbstractDaoImpl<ArtistActivityItem> implements ArtistActivityItemDao {

    /**
     * {@inheritDoc}
     */
	@Override
	public Long countArtistActivityItems(Artist artist) {
		Query query = getSession().getNamedQuery("countItemsHavingArtist");
		query.setParameter("artist", artist);
		return (Long) query.uniqueResult();
	}

    /**
     * {@inheritDoc}
     */
	@Override
	public boolean havingArtistExists(Artist artist) {
		Query query = getSession().getNamedQuery("findArtistActivityItemsByArtist");
		query.setParameter("artist", artist);
		query.setMaxResults(1);
        return !query.list().isEmpty();
	}

    /**
     * {@inheritDoc}
     */
	@Override
	public boolean havingActivityExists(Activity activity) {
		Query query = getSession().getNamedQuery("findArtistActivityItemsByActivity");
		query.setParameter("activity", activity);
		query.setMaxResults(1);
        return !query.list().isEmpty();
	}

    /**
     * {@inheritDoc}
     */
	@Override
	public void deleteByItem(Item item) {
		Query query = getSession().getNamedQuery("deleteArtistActivityItemByItem");
		query.setParameter("item", item);
		query.executeUpdate();
	}
}
