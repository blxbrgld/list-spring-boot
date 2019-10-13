package gr.blxbrgld.list.stepdefinitions;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import cucumber.runtime.CucumberException;
import gr.blxbrgld.list.ListTestBase;
import gr.blxbrgld.list.model.Item;
import gr.blxbrgld.list.service.FixtureService;
import gr.blxbrgld.list.service.ItemService;
import gr.blxbrgld.list.steps.CommonSteps;
import gr.blxbrgld.list.utils.HttpMethod;
import net.thucydides.core.annotations.Steps;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * Item step definitions
 * @author blxbrgld
 */
public class ItemStepDefinitions extends ListTestBase {

    @Autowired
    private FixtureService fixtureService;

    @Autowired
    private ItemService itemService;

    @Steps
    CommonSteps commonSteps;

    private static final String ITEMS_PATH = "/items/";

    @Given("^the following items exists$")
    public void itemsExists(List<Map<String, String>> items) {
        for(Item item : fixtures(items)) {
            itemService.persistOrMergeItem(item);
        }
    }

    @When("^request item (.*) by id$")
    public void getItemById(String title) {
        Optional<Item> item = itemService.getItem(title);
        Integer id = item.map(Item::getId).orElse(-1); // The found id or an id that for sure does not exist
        commonSteps.request(HttpMethod.GET, ITEMS_PATH + id);
    }

    @When("^request to create item$")
    public void createItem(List<Map<String, String>> items) {
       commonSteps.request(HttpMethod.POST, ITEMS_PATH, fixtures(items).get(0));
    }

    @When("^request to update item with title (.*) to$")
    public void updateItem(String title, List<Map<String, String>> items) {
        Optional<Item> existing = itemService.getItem(title);
        Integer id = existing.map(Item::getId).orElse(-1); // The found id or an id that for sure does not exist
        List<Item> fixtures = fixtures(items);
        if(fixtures.size()!=1) {
            throw new CucumberException("Cucumber expression is misconfigured.");
        } else {
            commonSteps.request(HttpMethod.PUT, ITEMS_PATH + id, fixtures.get(0));
        }
    }

    @When("^request to delete item with title (.*)$")
    public void deleteItem(String title) {
        Optional<Item> item = itemService.getItem(title);
        Integer id = item.map(Item::getId).orElse(-1); // The found id or an id that for sure does not exist
        commonSteps.request(HttpMethod.DELETE, ITEMS_PATH + id);
    }

    /**
     * Build and persist {@link Item} and {@link gr.blxbrgld.list.model.Fixture} from Cucumber table input
     * @param items Cucumber table input as List Of key/value pairs
     * @return List of {@link Item}
     */
    private List<Item> fixtures(List<Map<String, String>> items) {
        List<Item> result = new ArrayList<>();
        for(Map<String, String> item : items) {
            Item fixture = fixtureService.itemFixture(
                item.get("title"), // The english title
                item.get("category"),
                Integer.valueOf(item.get("year")),
                Collections.singletonMap(item.get("artist"), item.get("activity")),
                Collections.singletonList(item.get("comment"))
            );
            result.add(fixture);
        }
        return result;
    }
}
