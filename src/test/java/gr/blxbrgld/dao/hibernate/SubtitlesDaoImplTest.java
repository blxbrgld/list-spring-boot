package gr.blxbrgld.dao.hibernate;

import gr.blxbrgld.list.dao.hibernate.SubtitlesDao;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * SubtitlesDaoImpl Tests
 * @author blxbrgld
 */
@Ignore
public class SubtitlesDaoImplTest { //TODO To Be Deleted

    @Autowired
    private SubtitlesDao subtitlesDao;

    @Test
    public void getByTitleTest() {
        assertTrue(subtitlesDao.getByTitle("Greek Subtitles").isPresent());
        assertFalse(subtitlesDao.getByTitle("Greek").isPresent());
    }
}
