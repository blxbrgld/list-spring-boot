package gr.blxbrgld.list.stepdefinitions;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import gr.blxbrgld.list.ListTestBase;
import gr.blxbrgld.list.model.Role;
import gr.blxbrgld.list.service.FixtureService;
import gr.blxbrgld.list.service.RoleService;
import gr.blxbrgld.list.steps.CommonSteps;
import gr.blxbrgld.list.utils.HttpMethod;
import net.thucydides.core.annotations.Steps;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

/**
 * Role step definitions
 * @author blxbgld
 */
public class RoleStepDefinitions extends ListTestBase {

    @Autowired
    private FixtureService fixtureService;

    @Autowired
    private RoleService roleService;

    @Steps
    CommonSteps commonSteps;

    private static final String ROLES_PATH = "/api/roles/";

    @Given("^role with title (.*) exists$")
    public void roleExist(String title) {
        roleService.persistOrMergeRole( // Persist both as role and fixtures
            fixtureService.roleFixture(title)
        );
    }

    @When("^request role (.*) by id$")
    public void getRoleById(String title) {
        Optional<Role> role = roleService.getRole(title);
        Integer id = role.map(Role::getId).orElse(-1); // The found id or an id that for sure does not exist
        commonSteps.request(HttpMethod.GET, ROLES_PATH + id);
    }

    @When("^roles list is requested$")
    public void getRoles() {
        commonSteps.request(HttpMethod.GET, ROLES_PATH);
    }

    @When("^request to create role with title (.*)$")
    public void createRole(String title) {
        commonSteps.request(HttpMethod.POST, ROLES_PATH, fixtureService.roleFixture(title));
    }

    @When("^request to update role with title (.*) to (.*)$")
    public void updateRole(String existing, String updated) {
        Optional<Role> role = roleService.getRole(existing);
        Integer id = role.map(Role::getId).orElse(-1); // The found id or an id that for sure does not exist
        commonSteps.request(
            HttpMethod.PUT,
            ROLES_PATH + id,
            fixtureService.roleFixture(updated)
        );
    }

    @When("^request to delete role with title (.*)$")
    public void deleteRole(String title) {
        Optional<Role> role = roleService.getRole(title);
        Integer id = role.map(Role::getId).orElse(-1); // The found id or an id that for sure does not exist
        commonSteps.request(HttpMethod.DELETE, ROLES_PATH + id);
    }
}
