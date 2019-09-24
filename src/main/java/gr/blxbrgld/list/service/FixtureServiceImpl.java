package gr.blxbrgld.list.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import gr.blxbrgld.list.dao.hibernate.*;
import gr.blxbrgld.list.enums.FixtureType;
import gr.blxbrgld.list.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Fixtures service implementation
 * @author blxbrgld
 */
@Slf4j
@Service
@Transactional
public class FixtureServiceImpl implements FixtureService {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private FixtureDao fixtureDao;

    @Autowired
    private ActivityDao activityDao;

    @Autowired
    private ArtistDao artistDao;

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private SubtitlesDao subtitlesDao;

    /**
     * {@inheritDoc}
     */
    @Override
    public Activity activityFixture(String title) {
        try {
            Activity activity = Activity.builder().title(title).build();
            fixtureDao.persist(new Fixture(FixtureType.ACTIVITY, objectMapper.writeValueAsString(activity)));
            return activity;
        } catch (Exception exception) {
            throw new RuntimeException("Activity fixture exception.");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Artist artistFixture(String title) {
        try {
            Artist artist = Artist.builder().title(title).build();
            fixtureDao.persist(new Fixture(FixtureType.ARTIST, objectMapper.writeValueAsString(artist)));
            return artist;
        } catch (Exception exception) {
            throw new RuntimeException("Artist fixture exception.");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Category categoryFixture(String title, String parent) {
        try {
            Category category = Category.builder().title(title).parent(parent).build();
            fixtureDao.persist(new Fixture(FixtureType.CATEGORY, objectMapper.writeValueAsString(category)));
            return category;
        } catch (Exception exception) {
            throw new RuntimeException("Category fixture exception.");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Subtitles subtitlesFixture(String title) {
        try {
            Subtitles subtitles = Subtitles.builder().title(title).build();
            fixtureDao.persist(new Fixture(FixtureType.SUBTITLES, objectMapper.writeValueAsString(subtitles)));
            return subtitles;
        } catch (Exception exception) {
            throw new RuntimeException("Subtitles fixture exception.");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Scheduled(cron = "0 0/30 * * * ?") //TODO Delete only stale fixtures?
    public void deleteFixtures() {
        List<Fixture> fixtures = fixtureDao.getAll();
        try {
            for (Fixture fixture : fixtures) {
                // Delete the entity created
                switch (fixture.getType()) {
                    case ACTIVITY:
                        Activity activity = objectMapper.readValue(fixture.getFixture(), Activity.class);
                        activityDao.deleteByTitle(activity.getTitle());
                        break;
                    case ARTIST:
                        Artist artist = objectMapper.readValue(fixture.getFixture(), Artist.class);
                        artistDao.deleteByTitle(artist.getTitle());
                        break;
                    case CATEGORY:
                        Category category = objectMapper.readValue(fixture.getFixture(), Category.class);
                        categoryDao.deleteByTitle(category.getTitle());
                        break;
                    case SUBTITLES:
                        Subtitles subtitles = objectMapper.readValue(fixture.getFixture(), Subtitles.class);
                        subtitlesDao.deleteByTitle(subtitles.getTitle());
                        break;
                    default:
                        log.error("Unknown fixture type {}.", fixture.getType());
                }
                // Delete the fixture
                fixtureDao.deleteById(fixture.getId());
            }
        } catch (Exception exception) {
            log.error("Exception", exception);
        }
    }
}
