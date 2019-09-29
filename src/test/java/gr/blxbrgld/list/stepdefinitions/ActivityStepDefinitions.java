package gr.blxbrgld.list.stepdefinitions;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import gr.blxbrgld.list.ListTestBase;
import gr.blxbrgld.list.model.Activity;
import gr.blxbrgld.list.service.ActivityService;
import gr.blxbrgld.list.service.FixtureService;
import gr.blxbrgld.list.steps.CommonSteps;
import gr.blxbrgld.list.utils.HttpMethod;
import net.thucydides.core.annotations.Steps;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

/**
 * Activity step definitions
 * @author blxbrgld
 */
public class ActivityStepDefinitions extends ListTestBase {

    @Autowired
    private FixtureService fixtureService;

    @Autowired
    private ActivityService activityService;

    @Steps
    CommonSteps commonSteps;

    private static final String ACTIVITIES_PATH = "/activities/";

    @Given("^activity with title (.*) exists$")
    public void activityExist(String title) {
        activityService.persistOrMergeActivity( // Persist both as activity and fixtures
            fixtureService.activityFixture(title)
        );
    }

    @When("^request activity (.*) by id$")
    public void getActivityById(String title) {
        Optional<Activity> activity = activityService.getActivity(title);
        Integer id = activity.map(Activity::getId).orElse(-1); // The found id or an id that for sure does not exist
        commonSteps.request(HttpMethod.GET, ACTIVITIES_PATH + id);
    }

    @When("^activities list is requested$")
    public void getActivities() {
        commonSteps.request(HttpMethod.GET, ACTIVITIES_PATH);
    }

    @When("^request to create activity with title (.*)$")
    public void createActivity(String title) {
        commonSteps.request(HttpMethod.POST, ACTIVITIES_PATH, fixtureService.activityFixture(title));
    }

    @When("^request to update activity with title (.*) to (.*)$")
    public void updateActivity(String existing, String updated) {
        Optional<Activity> activity = activityService.getActivity(existing);
        Integer id = activity.map(Activity::getId).orElse(-1); // The found id or an id that for sure does not exist
        commonSteps.request(
            HttpMethod.PUT,
            ACTIVITIES_PATH + id,
            fixtureService.activityFixture(updated)
        );
    }

    @When("^request to delete activity with title (.*)$")
    public void deleteActivity(String title) {
        Optional<Activity> activity = activityService.getActivity(title);
        Integer id = activity.map(Activity::getId).orElse(-1); // The found id or an id that for sure does not exist
        commonSteps.request(HttpMethod.DELETE, ACTIVITIES_PATH + id);
    }

    @When("^count of activities is requested$")
    public void countActivities() {
        commonSteps.request(HttpMethod.GET, ACTIVITIES_PATH + "count");
    }
}
