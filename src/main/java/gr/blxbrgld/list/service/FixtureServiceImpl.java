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
    private CommentDao commentDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private SubtitlesDao subtitlesDao;

    @Autowired
    private UserDao userDao;

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
    public Comment commentFixture(String title) {
        try {
            Comment comment = Comment.builder().title(title).build();
            fixtureDao.persist(new Fixture(FixtureType.COMMENT, objectMapper.writeValueAsString(comment)));
            return comment;
        } catch (Exception exception) {
            throw new RuntimeException("Comment fixture exception.");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Role roleFixture(String title) {
        try {
            Role role = Role.builder().title(title).build();
            fixtureDao.persist(new Fixture(FixtureType.ROLE, objectMapper.writeValueAsString(role)));
            return role;
        } catch (Exception exception) {
            throw new RuntimeException("Role fixture exception.");
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
    public User userFixture(String username, String password, String email, String role) {
        try {
            User user = User
                .builder()
                .username(username)
                .password(password)
                .email(email)
                .role(role)
                .build();
            fixtureDao.persist(new Fixture(FixtureType.USER, objectMapper.writeValueAsString(user)));
            return user;
        } catch (Exception exception) {
            throw new RuntimeException("User fixture exception.");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Scheduled(cron = "0 0/30 * * * ?") //TODO Delete only stale fixtures?
    public void deleteFixtures() {
        deleteFixturesByType(FixtureType.USER);
        deleteFixturesByType(FixtureType.ROLE);
        //TODO The ordering should be reviewed when item fixtures are added
        deleteFixturesByType(FixtureType.ACTIVITY);
        deleteFixturesByType(FixtureType.ARTIST);
        deleteFixturesByType(FixtureType.CATEGORY);
        deleteFixturesByType(FixtureType.COMMENT);
        deleteFixturesByType(FixtureType.SUBTITLES);
    }

    /**
     * The entities related to the fixtures should be deleted in specific order due to foreign key relationships.
     * @param type {@link FixtureType}
     */
    private void deleteFixturesByType(FixtureType type) {
        List<Fixture> fixtures = fixtureDao.getByType(type);
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
                    case COMMENT:
                        Comment comment = objectMapper.readValue(fixture.getFixture(), Comment.class);
                        commentDao.deleteByTitle(comment.getTitle());
                        break;
                    case ROLE:
                        Role role = objectMapper.readValue(fixture.getFixture(), Role.class);
                        roleDao.deleteByTitle(role.getTitle());
                        break;
                    case SUBTITLES:
                        Subtitles subtitles = objectMapper.readValue(fixture.getFixture(), Subtitles.class);
                        subtitlesDao.deleteByTitle(subtitles.getTitle());
                        break;
                    case USER:
                        User user = objectMapper.readValue(fixture.getFixture(), User.class);
                        userDao.deleteByUsername(user.getUsername());
                        break;
                    default:
                        log.error("Unknown fixture type {}.", fixture.getType());
                }
                // Delete the fixture
                fixtureDao.deleteById(fixture.getId());
            }
        } catch(Exception exception) {
            log.error("Exception", exception);
        }
    }
}
