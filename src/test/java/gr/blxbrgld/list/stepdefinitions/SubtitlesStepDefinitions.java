package gr.blxbrgld.list.stepdefinitions;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import gr.blxbrgld.list.ListTestBase;
import gr.blxbrgld.list.model.Subtitles;
import gr.blxbrgld.list.service.FixtureService;
import gr.blxbrgld.list.service.SubtitlesService;
import gr.blxbrgld.list.steps.CommonSteps;
import gr.blxbrgld.list.utils.HttpMethod;
import net.thucydides.core.annotations.Steps;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

/**
 * Subtitle step definitions
 * @author blxbrgld
 */
public class SubtitlesStepDefinitions extends ListTestBase {

    @Autowired
    private FixtureService fixtureService;

    @Autowired
    private SubtitlesService subtitlesService;

    @Steps
    CommonSteps commonSteps;

    private static final String SUBTITLES_PATH = "/subtitles/";

    @Given("^subtitles with title (.*) exist$")
    public void subtitlesExist(String title) {
        subtitlesService.persistOrMergeSubtitles( // Persist both as subtitles and fixtures
            fixtureService.subtitlesFixture(title)
        );
    }

    @When("^request subtitles (.*) by id$")
    public void getSubtitleById(String title) {
        Optional<Subtitles> subtitles = subtitlesService.getSubtitles(title);
        Integer id = subtitles.map(Subtitles::getId).orElse(-1); // The found id or an id that for sure does not exist
        commonSteps.request(HttpMethod.GET, SUBTITLES_PATH + id);
    }

    @When("^subtitles list is requested$")
    public void getSubtitles() {
        commonSteps.request(HttpMethod.GET, SUBTITLES_PATH);
    }

    @When("^request to create subtitles with title (.*)$")
    public void createSubtitles(String title) {
        commonSteps.request(HttpMethod.POST, SUBTITLES_PATH, fixtureService.subtitlesFixture(title));
    }

    @When("^request to update subtitles with title (.*) to (.*)$")
    public void updateSubtitles(String existing, String updated) {
        Optional<Subtitles> subtitles = subtitlesService.getSubtitles(existing);
        Integer id = subtitles.map(Subtitles::getId).orElse(-1); // The found id or an id that for sure does not exist
        commonSteps.request(
            HttpMethod.PUT,
            SUBTITLES_PATH + id,
            fixtureService.subtitlesFixture(updated)
        );
    }

    @When("^request to delete subtitles with title (.*)$")
    public void deleteSubtitles(String title) {
        Optional<Subtitles> subtitles = subtitlesService.getSubtitles(title);
        Integer id = subtitles.map(Subtitles::getId).orElse(-1); // The found id or an id that for sure does not exist
        commonSteps.request(HttpMethod.DELETE, SUBTITLES_PATH + id);
    }
}
