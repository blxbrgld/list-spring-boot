package gr.blxbrgld.list.stepdefinitions;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import cucumber.runtime.CucumberException;
import gr.blxbrgld.list.ListTestBase;
import gr.blxbrgld.list.model.User;
import gr.blxbrgld.list.service.FixtureService;
import gr.blxbrgld.list.service.UserService;
import gr.blxbrgld.list.steps.CommonSteps;
import gr.blxbrgld.list.utils.HttpMethod;
import net.thucydides.core.annotations.Steps;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * User step definitions
 * @author blxbrgld
 */
public class UserStepDefinitions extends ListTestBase {

    @Autowired
    private FixtureService fixtureService;

    @Autowired
    private UserService userService;

    @Steps
    CommonSteps commonSteps;

    private static final String USERS_PATH = "/users/";

    @Given("^the following users exist$")
    public void userExist(List<Map<String, String>> users) {
        for(User user : fixtures(users)) {
            userService.persistOrMergeUser(user);
        }
    }

    @When("^request user (.*) by id$")
    public void getUserById(String username) {
        Optional<User> user = userService.getUser(username);
        Integer id = user.map(User::getId).orElse(-1); // The found id or an id that for sure does not exist
        commonSteps.request(HttpMethod.GET, USERS_PATH + id);
    }

    @When("^users list is requested$")
    public void getUsers() {
        commonSteps.request(HttpMethod.GET, USERS_PATH);
    }

    @When("^request to create user$")
    public void createUser(List<Map<String, String>> users) {
        List<User> fixtures = fixtures(users);
        if(fixtures.size()!=1) {
            throw new CucumberException("Cucumber expression is misconfigured.");
        } else {
            commonSteps.request(HttpMethod.POST, USERS_PATH, fixtures.get(0));
        }
    }

    @When("^request to update user with username (.*) to$")
    public void updateUser(String username, List<Map<String, String>> users) {
        Optional<User> existing = userService.getUser(username);
        Integer id = existing.map(User::getId).orElse(-1); // The found id or an id that for sure does not exist
        List<User> fixtures = fixtures(users);
        if(fixtures.size()!=1) {
            throw new CucumberException("Cucumber expression is misconfigured.");
        } else {
            commonSteps.request(HttpMethod.PUT, USERS_PATH + id, fixtures.get(0));
        }
    }

    /**
     * Build and persist {@link User} and {@link gr.blxbrgld.list.model.Fixture} from Cucumber table input
     * @param users Cucumber table input as List Of key/value pairs
     * @return List of {@link User}
     */
    private List<User> fixtures(List<Map<String, String>> users) {
        List<User> result = new ArrayList<>();
        for(Map<String, String> user : users) {
            User fixture = fixtureService.userFixture(
                user.get("username"),
                user.get("password"),
                user.get("email"),
                user.get("role")
            );
            result.add(fixture);
        }
        return result;
    }
}
