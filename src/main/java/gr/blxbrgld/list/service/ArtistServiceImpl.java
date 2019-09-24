package gr.blxbrgld.list.service;

import gr.blxbrgld.list.dao.hibernate.ArtistDao;
import gr.blxbrgld.list.enums.Order;
import gr.blxbrgld.list.model.Artist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Artist's Service Implementation
 * @author blxbrgld
 */
@Service
@Transactional
public class ArtistServiceImpl implements ArtistService {

	@Autowired
	private ArtistDao artistDao;

    /**
     * {@inheritDoc}
     */
	@Override
	public void persistOrMergeArtist(Artist artist) {
		artistDao.persistOrMerge(artist);
	}

    /**
     * {@inheritDoc}
     */
	@Override
	public List<Artist> getArtists(String attribute, Order order, int first, int size) {
		return artistDao.getAll(attribute, order, first, size);
	}

    /**
     * {@inheritDoc}
     */
	@Override
	public Optional<Artist> getArtist(Integer id) {
		return artistDao.get(id);
	}

    /**
     * {@inheritDoc}
     */
	@Override
	public Optional<Artist> getArtist(String title) {
		return artistDao.getByTitle(title);
	}

    /**
     * {@inheritDoc}
     */
	@Override
	public void deleteArtist(Integer id) {
		artistDao.deleteById(id);
	}

    /**
     * {@inheritDoc}
     */
	@Override
	public Long countArtists() {
		return artistDao.count();
	}

    /**
     * {@inheritDoc}
     */
	@Override
	public Artist findLastArtist() {
		return artistDao.findLast();
	}

    /**
     * {@inheritDoc}
     */
	@Override
	public Artist randomArtist() {
		return artistDao.findRandom();
	}

    /**
     * {@inheritDoc}
     */
	@Override
	public List<String> findArtistsLike(String term) {
		List<Artist> artistList = artistDao.findLike(term);
		List<String> titleList = new ArrayList<>();
		for(Artist artist : artistList) {
			titleList.add(artist.getTitle());
		}
		return titleList;
	}
}