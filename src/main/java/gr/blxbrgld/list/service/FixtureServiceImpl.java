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
import java.util.Map;

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
    private ItemDao itemDao;

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
    public Item itemFixture(String title, String category, Integer year, Map<String, String> artists, List<String> comments) {
        try {
            Item item = Item.builder()
                .titleEng(title)
                .category(category)
                .year(year)
                .artists(artists)
                .comments(comments)
                .build();
            fixtureDao.persist(new Fixture(FixtureType.ITEM, objectMapper.writeValueAsString(item)));
            for(ArtistActivityItem artist : item.getArtistActivityItems()) { // The item's artists have to be persisted as fixtures too
                fixtureDao.persist(new Fixture(FixtureType.ARTIST, objectMapper.writeValueAsString(artist.getIdArtist())));
            }
            return item;
        } catch (Exception exception) {
            throw new RuntimeException("Item fixture exception.");
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
        deleteFixturesByType(FixtureType.ITEM);
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
                        activityDao.deleteByTitle(objectMapper.readValue(fixture.getFixture(), Activity.class).getTitle());
                        break;
                    case ARTIST:
                        artistDao.deleteByTitle(objectMapper.readValue(fixture.getFixture(), Artist.class).getTitle());
                        break;
                    case CATEGORY:
                        categoryDao.deleteByTitle(objectMapper.readValue(fixture.getFixture(), Category.class).getTitle());
                        break;
                    case COMMENT:
                        commentDao.deleteByTitle(objectMapper.readValue(fixture.getFixture(), Comment.class).getTitle());
                        break;
                    case ITEM:
                        itemDao.deleteByTitleEng(objectMapper.readValue(fixture.getFixture(), Item.class).getTitleEng());
                        break;
                    case ROLE:
                        roleDao.deleteByTitle(objectMapper.readValue(fixture.getFixture(), Role.class).getTitle());
                        break;
                    case SUBTITLES:
                        subtitlesDao.deleteByTitle(objectMapper.readValue(fixture.getFixture(), Subtitles.class).getTitle());
                        break;
                    case USER:
                        userDao.deleteByUsername(objectMapper.readValue(fixture.getFixture(), User.class).getUsername());
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
