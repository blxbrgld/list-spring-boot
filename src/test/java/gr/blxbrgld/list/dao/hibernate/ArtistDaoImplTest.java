package gr.blxbrgld.list.dao.hibernate;

import gr.blxbrgld.list.model.Artist;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * ArtistDaoImpl Tests
 * @author blxbrgld
 */
@Ignore
public class ArtistDaoImplTest { //TODO To Be Deleted

    @Autowired
    private ArtistDao artistDao;

    @Test
    public void findByTitle() {
        assertTrue(artistDao.getByTitle("Aki Kaurismaki").isPresent());
    }

    @Test
    public void findLast() {
        Artist artist = artistDao.findLast();
        assertNotNull(artist);
        assertEquals("Gakuryu Ishii", artist.getTitle());
    }

    @Test
    public void findRandom() {
        assertNotNull(artistDao.findRandom());
    }

    @Test
    public void findLike() {
        List<Artist> list = artistDao.findLike("Mark");
        assertEquals(3, list.size());
        assertEquals("Mark Chung", list.get(0).getTitle()); // List Is Ordered By Title ASC
    }
}
