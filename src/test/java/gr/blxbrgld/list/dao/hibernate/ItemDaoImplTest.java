package gr.blxbrgld.list.dao.hibernate;

import gr.blxbrgld.list.model.Artist;
import gr.blxbrgld.list.model.Category;
import gr.blxbrgld.list.model.Item;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * ItemDaoImpl Tests
 * @author blxbrgld
 */
@Ignore
public class ItemDaoImplTest { //TODO To Be Deleted

    @Autowired
    private ItemDao itemDao;

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private ArtistDao artistDao;

    @Autowired
    private SubtitlesDao subtitlesDao;

    @Test
    public void findByCategory() {
        List<Item> dvd = itemDao.findByCategory(categoryDao.getByTitle("DVD Films").get());
        assertNotNull(dvd);
        List<Item> divX = itemDao.findByCategory(categoryDao.getByTitle("DivX Films").get());
        assertNotNull(divX);
        List<Item> films = itemDao.findByCategory(categoryDao.getByTitle("Films").get()); // Film Category Should Return The Child Items
        assertEquals(films.size(), dvd.size() + divX.size());
    }

    @Test
    public void findByArtist() {
        Optional<Artist> artist = artistDao.getByTitle("Aki Kaurismaki");
        List<Item> itemList = itemDao.findByArtist(artist.get(), 0, 1000); // A Really Big Page
        assertEquals(1, itemList.size());
        itemList = itemDao.findByArtist(artist.get(), 1, 1000); //Non Inclusive
        assertEquals(0, itemList.size());
    }

    @Test
    public void havingCategoryExists() {
        assertTrue(itemDao.havingCategoryExists(categoryDao.getByTitle("DVD Films").get()));
    }

    @Test
    public void havingSubtitlesExists() {
        assertTrue(itemDao.havingSubtitlesExists(subtitlesDao.getByTitle("No Subtitles").get()));
    }

    @Test
    public void findLastDate() {
        Optional<Category> category = categoryDao.getByTitle("Films");
        Item item = itemDao.findLastDate(category.get().getTitle(), true); // The Most Recent Film Item
        assertEquals("24 Hour Party People", item.getTitleEng());
    }

    @Test
    public void findNextPlace() {
        Optional<Category> category = categoryDao.getByTitle("Films");
        assertTrue(itemDao.findNextPlace(category.get().getTitle()).equals(63));
    }
}
