package gr.blxbrgld.list.service;

import gr.blxbrgld.list.model.*;

import java.util.List;
import java.util.Map;

/**
 * Fixtures service interface
 * @author blxbrgld
 */
public interface FixtureService {

    /**
     * Build a {@link Activity} and the {@link Fixture} related with it
     * @param title The title
     * @return {@link Activity}
     */
    Activity activityFixture(String title);

    /**
     * Build a {@link Artist} and the {@link Fixture} related with it
     * @param title The title
     * @return {@link Artist}
     */
    Artist artistFixture(String title);

    /**
     * Build a {@link Category} and the {@link Fixture} related with it
     * @param title Category's title
     * @param parent Category's parent title
     * @return {@link Category}
     */
    Category categoryFixture(String title, String parent);

    /**
     * Build a {@link Comment} and the {@link Fixture} related with it
     * @param title Comment's title
     * @return {@link Comment}
     */
    Comment commentFixture(String title);

    /**
     * Build a {@link Item} and the {@link Fixture} related with it. We won't test all item attributes,
     * hence only a subset of them are taken into consideration when building the fixture.
     * @param title The english title
     * @param category The category
     * @param year The year
     * @param artists Artist/Activity as key/value pairs
     * @param comments The comments
     * @return {@link Item}
     */
    Item itemFixture(String title, String category, Integer year, Map<String, String> artists, List<String> comments);

    /**
     * Build a {@link Role} and the {@link Fixture} related with it
     * @param title Role's title
     * @return {@link Role}
     */
    Role roleFixture(String title);

    /**
     * Build a {@link Subtitles} and the {@link Fixture} related with it
     * @param title The title
     * @return {@link Subtitles}
     */
    Subtitles subtitlesFixture(String title);

    /**
     * Build a {@link User} and the {@link Fixture} related with it
     * @param username The username
     * @param password The password
     * @param email The email
     * @param role The role
     * @return {@link User}
     */
    User userFixture(String username, String password, String email, String role);

    /**
     * Delete fixtures
     */
    void deleteFixtures();
}
