package gr.blxbrgld.list.stepdefinitions;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import gr.blxbrgld.list.ListTestBase;
import gr.blxbrgld.list.model.Category;
import gr.blxbrgld.list.service.CategoryService;
import gr.blxbrgld.list.service.FixtureService;
import gr.blxbrgld.list.steps.CommonSteps;
import gr.blxbrgld.list.utils.HttpMethod;
import net.thucydides.core.annotations.Steps;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.Optional;

/**
 * Category Step Definitions
 * @author npapadopoulos
 */
public class CategoryStepDefinitions extends ListTestBase {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private FixtureService fixtureService;

    @Steps
    CommonSteps commonSteps;

    private static final String CATEGORIES_PATH = "/categories/";

    @Given("^category with title (.*) exists$")
    public void categoryExists(String title) {
        categoryService.persistOrMergeCategory( // Persist both as category and fixture
            fixtureService.categoryFixture(title, null)
        );
    }

    @When("^request category (.*) by id$")
    public void getCategoryById(String title) {
        Optional<Category> category = categoryService.getCategory(title);
        Integer id = category.map(Category::getId).orElse(-1); // The found id or an id that for sure does not exist
        commonSteps.request(HttpMethod.GET, CATEGORIES_PATH + id);
    }

    @When("^category list with filtering (.*) is requested$")
    public void getCategories(String filter) {
        commonSteps.request(
            HttpMethod.GET,
            CATEGORIES_PATH,
            Collections.singletonMap("filter", filter)
        );
    }

    @When("^request to create category with title (\\S+)$")
    public void createCategory(String title) {
        commonSteps.request(HttpMethod.POST, CATEGORIES_PATH, fixtureService.categoryFixture(title, null));
    }

    @When("^request to create category with title (\\S+) and parent title (\\S+)")
    public void createCategoryWithParent(String title, String parent) {
        commonSteps.request(HttpMethod.POST, CATEGORIES_PATH, fixtureService.categoryFixture(title, parent));
    }
}
