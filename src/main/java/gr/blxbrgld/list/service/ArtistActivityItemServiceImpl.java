package gr.blxbrgld.list.service;

import gr.blxbrgld.list.dao.hibernate.ArtistActivityItemDao;
import gr.blxbrgld.list.model.Artist;
import gr.blxbrgld.list.model.ArtistActivityItem;
import gr.blxbrgld.list.model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * ArtistActivityItem's Service Implementation
 * @author blxbrgld
 */
@Service
@Transactional
public class ArtistActivityItemServiceImpl implements ArtistActivityItemService {

	@Autowired
	private ArtistActivityItemDao artistActivityItemDao;

    /**
     * {@inheritDoc}
     */
	@Override
	public void persistArtistActivityItem(ArtistActivityItem artistActivityItem) {
		artistActivityItemDao.persist(artistActivityItem);
	}

    /**
     * {@inheritDoc}
     */
	@Override
	public void deleteArtistActivityItem(Item item) {
		artistActivityItemDao.deleteByItem(item);
	}

    /**
     * {@inheritDoc}
     */
	@Override
	public Long countArtistActivityItemsHavingArtist(Artist artist) {
		return artistActivityItemDao.countArtistActivityItems(artist);
	}
}
