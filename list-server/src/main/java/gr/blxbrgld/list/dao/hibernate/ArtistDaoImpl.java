package gr.blxbrgld.list.dao.hibernate;

import gr.blxbrgld.list.model.Artist;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Artist's DAO Implementation
 * @author blxbrgld
 */
@Repository
public class ArtistDaoImpl extends AbstractDaoImpl<Artist> implements ArtistDao {

    /**
     * {@inheritDoc}
     */
	@Override
	public Artist findLast() {
		Query query = getSession().getNamedQuery("findLastArtist");
		query.setMaxResults(1);
		return (Artist) query.uniqueResult();
	}

    /**
     * {@inheritDoc}
     */
	@Override
	public Artist findRandom() {
		Query query = getSession().getNamedQuery("randomArtist");
		query.setMaxResults(1);
		return (Artist) query.uniqueResult();
	}

    /**
     * {@inheritDoc}
     */
	@SuppressWarnings("unchecked")
	@Override
	public List<Artist> findLike(String term) {
		Query query = getSession().getNamedQuery("findArtistsLike");
		query.setParameter("term", '%' + term + '%');
		query.setMaxResults(5);
		return (List<Artist>) query.list();
	}
}
