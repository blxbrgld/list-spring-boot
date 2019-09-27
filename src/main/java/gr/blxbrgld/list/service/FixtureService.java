package gr.blxbrgld.list.service;

import gr.blxbrgld.list.model.*;

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
     * Build a {@link Subtitles} and the {@link Fixture} related with it
     * @param title The title
     * @return {@link Subtitles}
     */
    Subtitles subtitlesFixture(String title);

    /**
     * Delete fixtures
     */
    void deleteFixtures();
}
