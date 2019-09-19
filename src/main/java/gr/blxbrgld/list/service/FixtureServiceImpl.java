package gr.blxbrgld.list.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import gr.blxbrgld.list.dao.hibernate.ActivityDao;
import gr.blxbrgld.list.dao.hibernate.FixtureDao;
import gr.blxbrgld.list.dao.hibernate.SubtitlesDao;
import gr.blxbrgld.list.enums.FixtureType;
import gr.blxbrgld.list.model.Activity;
import gr.blxbrgld.list.model.Fixture;
import gr.blxbrgld.list.model.Subtitles;
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
    private SubtitlesDao subtitlesDao;

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
    @Scheduled(cron = "0 0/30 * * * ?") //TODO Delete only stale fixtures?
    public void deleteFixtures() {
        List<Fixture> fixtures = fixtureDao.getAll();
        try {
            for (Fixture fixture : fixtures) {
                switch (fixture.getType()) {
                    case ACTIVITY:
                        Activity activity = objectMapper.readValue(fixture.getFixture(), Activity.class);
                        activityDao.deleteByTitle(activity.getTitle());
                        fixtureDao.deleteById(fixture.getId());
                        break;
                    case SUBTITLES:
                        Subtitles subtitles = objectMapper.readValue(fixture.getFixture(), Subtitles.class);
                        subtitlesDao.deleteByTitle(subtitles.getTitle());
                        fixtureDao.deleteById(fixture.getId());
                        break;
                    default:
                        log.error("Unknown fixture type {}.", fixture.getType());
                }
            }
        } catch (Exception exception) {
            log.error("Exception", exception);
        }
    }
}
