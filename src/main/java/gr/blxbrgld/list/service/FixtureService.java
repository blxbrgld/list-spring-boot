package gr.blxbrgld.list.service;

import gr.blxbrgld.list.model.Activity;
import gr.blxbrgld.list.model.Subtitles;
import gr.blxbrgld.list.model.Fixture;

/**
 * Fixtures service interface
 * @author blxbrgld
 */
public interface FixtureService {

    /**
     * Build a {@link Subtitles} and the {@link Fixture} related with it
     * @param title The title
     * @return {@link Subtitles}
     */
    Subtitles subtitlesFixture(String title);

    /**
     * Build a {@link Activity} and the {@link Fixture} related with it
     * @param title The title
     * @return {@link Activity}
     */
    Activity activityFixture(String title);

    /**
     * Delete fixtures
     */
    void deleteFixtures();
}
