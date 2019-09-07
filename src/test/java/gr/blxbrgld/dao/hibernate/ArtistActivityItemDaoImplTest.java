package gr.blxbrgld.dao.hibernate;

import gr.blxbrgld.list.dao.hibernate.ActivityDao;
import gr.blxbrgld.list.dao.hibernate.ArtistActivityItemDao;
import gr.blxbrgld.list.dao.hibernate.ArtistDao;
import gr.blxbrgld.list.dao.hibernate.ItemDao;
import gr.blxbrgld.list.model.Activity;
import gr.blxbrgld.list.model.Artist;
import org.hibernate.SessionFactory;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * ArtistActivityItemDaoImpl Tests
 * @author blxbrgld
 */
@Ignore
public class ArtistActivityItemDaoImplTest { //TODO To Be Deleted

    @Autowired
    private ArtistActivityItemDao artistActivityItemDao;

    @Autowired
    private ArtistDao artistDao;

    @Autowired
    private ActivityDao activityDao;

    @Autowired
    private ItemDao itemDao;

    @Autowired
    private SessionFactory sessionFactory;

    @Test
    public void countArtistActivityItems() {
        Optional<Artist> artist = artistDao.getByTitle("Aki Kaurismaki");
        Long items = artistActivityItemDao.countArtistActivityItems(artist.get());
        assertEquals(1, items.intValue()); // Kaurismaki Rules, Get All His Movies!
    }

    @Test
    public void havingArtistExists() {
        Optional<Artist> artist = artistDao.getByTitle("Aki Kaurismaki");
        assertTrue(artistActivityItemDao.havingArtistExists(artist.get()));
    }

    @Test
    public void havingActivityExists() {
        Optional<Activity> activity = activityDao.getByTitle("Musician");
        assertTrue(artistActivityItemDao.havingActivityExists(activity.get()));
    }

    @Test
    public void deleteByItem() {
        Optional<Artist> artist = artistDao.getByTitle("Bruce Willis");
        Long items = artistActivityItemDao.countArtistActivityItems(artist.get());
        assertEquals(1, items.intValue()); // An Item Exist
        artistActivityItemDao.deleteByItem(itemDao.get(4).get()); // A Little Cheating Here To Delete Bruce's Only Item

        sessionFactory.getCurrentSession().flush(); // Ensure That Hibernate Session Is Flushed
        items = artistActivityItemDao.countArtistActivityItems(artist.get());
        assertEquals(0, items.intValue()); // Item Does Not Exist Anymore
    }
}
