package gr.blxbrgld.dao.hibernate;

import gr.blxbrgld.list.dao.hibernate.ArtistDao;
import gr.blxbrgld.list.dao.hibernate.CommentDao;
import gr.blxbrgld.list.dao.hibernate.SubtitlesDao;
import gr.blxbrgld.list.model.Artist;
import gr.blxbrgld.list.model.Comment;
import gr.blxbrgld.list.model.Subtitles;
import org.apache.commons.lang3.time.DateUtils;
import org.hibernate.SessionFactory;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Calendar;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * AbstractDaoImpl Tests
 * @author blxbrgld
 */
@Ignore
public class AbstractDaoImplTest { //TODO To Be Deleted

    @Autowired
    private SubtitlesDao subtitlesDao;

    @Autowired
    private ArtistDao artistDao;

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private SessionFactory sessionFactory;

    @Test
    public void persist() {
        Subtitles subtitles = new Subtitles();
        subtitles.setTitle("French Subtitles");
        subtitlesDao.persist(subtitles);

        sessionFactory.getCurrentSession().flush(); // Ensure That Hibernate Session Is Flushed
        subtitles = subtitlesDao.getByTitle("French Subtitles").get();
        assertTrue(subtitles.getId().equals(4)); // Auto Incremented
        assertNotNull(subtitles.getDateUpdated()); // Date Persisted Via Reflection
    }

    @Test
    public void merge() {
        Artist artist = artistDao.getByTitle("Peter Greenaway").get();
        artist.setTitle("Edited");
        artistDao.persist(artist);

        sessionFactory.getCurrentSession().flush(); // Ensure That Hibernate Session Is Flushed
        Optional<Artist> edited = artistDao.getByTitle("Edited");
        assertEquals(artist.getId(), edited.get().getId()); // Id Did Not Change
        assertTrue(DateUtils.isSameDay(edited.get().getDateUpdated(), Calendar.getInstance())); // But dateUpdated Did
    }

    @Test
    public void get() {
        Optional<Subtitles> subtitles = subtitlesDao.get(1);
        assertTrue(subtitles.isPresent());
        assertEquals("No Subtitles", subtitles.get().getTitle());
    }

    @Test
    public void delete() {
        Optional<Comment> comment = commentDao.getByTitle("Split Single");
        commentDao.delete(comment.get());

        sessionFactory.getCurrentSession().flush(); // Ensure That Hibernate Session Is Flushed
        comment = commentDao.getByTitle("Split Single");
        assertFalse(comment.isPresent()); // Deleted
    }

    @Test
    public void deleteById() {
        Optional<Comment> comment = commentDao.getByTitle("Split EP");
        commentDao.deleteById(comment.get().getId());

        sessionFactory.getCurrentSession().flush(); // Ensure That Hibernate Session Is Flushed
        comment = commentDao.getByTitle("Split EP");
        assertFalse(comment.isPresent()); // Deleted
    }

    @Test
    public void count() {
        Long count = subtitlesDao.count();
        assertEquals(3, count.intValue());
    }
}
