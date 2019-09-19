package gr.blxbrgld.list.stepdefinitions;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import gr.blxbrgld.list.ListTestBase;
import gr.blxbrgld.list.model.Artist;
import gr.blxbrgld.list.service.ArtistService;
import gr.blxbrgld.list.service.FixtureService;
import gr.blxbrgld.list.steps.CommonSteps;
import gr.blxbrgld.list.utils.HttpMethod;
import net.thucydides.core.annotations.Steps;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.Optional;

/**
 * Artist Step Definitions
 * @author blxbrgld
 */
public class ArtistStepDefinitions extends ListTestBase {

    @Autowired
    private FixtureService fixtureService;

    @Autowired
    private ArtistService artistService;

    @Steps
    CommonSteps commonSteps;

    private static final String ARTISTS_PATH = "/artists/";

    @Given("^artist with title (.*) exist$")
    public void artistExist(String title) {
        artistService.persistOrMergeArtist( // Persist both as artist and fixtures
            fixtureService.artistFixture(title)
        );
    }

    @When("^request artist (.*) by id$")
    public void getArtistById(String title) {
        Optional<Artist> artist = artistService.getArtist(title);
        Integer id = artist.map(Artist::getId).orElse(-1); // The found id or an id that for sure does not exist
        commonSteps.request(HttpMethod.GET, ARTISTS_PATH + id);
    }

    @When("^artist list is requested$")
    public void getArtists() {
        commonSteps.request(HttpMethod.GET, ARTISTS_PATH);
    }

    @When("^artist list is requested in descending order$")
    public void getArtistsDescending() {
        commonSteps.request(
            HttpMethod.GET,
            ARTISTS_PATH,
            Collections.singletonMap("order", "DESC")
        );
    }

    @When("^request to create artist with title (.*)$")
    public void createArtist(String title) {
        commonSteps.request(HttpMethod.POST, ARTISTS_PATH, fixtureService.artistFixture(title));
    }

    @When("^request to update artist with title (.*) to (.*)$")
    public void updateArtist(String existing, String updated) {
        Optional<Artist> artist = artistService.getArtist(existing);
        Integer id = artist.map(Artist::getId).orElse(-1); // The found id or an id that for sure does not exist
        commonSteps.request(
            HttpMethod.PUT,
            ARTISTS_PATH + id,
            fixtureService.artistFixture(updated)
        );
    }

    @When("^request to delete artist with title (.*)$")
    public void deleteArtist(String title) {
        Optional<Artist> artist = artistService.getArtist(title);
        Integer id = artist.map(Artist::getId).orElse(-1); // The found id or an id that for sure does not exist
        commonSteps.request(HttpMethod.DELETE, ARTISTS_PATH + id);
    }

    @When("^random artist is requested$")
    public void randomArtist() {
        commonSteps.request(HttpMethod.GET, ARTISTS_PATH + "random");
    }

    @When("^count of artists is requested$")
    public void countArtists() {
        commonSteps.request(HttpMethod.GET, ARTISTS_PATH + "count");
    }
}
