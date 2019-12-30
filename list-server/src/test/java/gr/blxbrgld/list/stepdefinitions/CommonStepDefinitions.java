package gr.blxbrgld.list.stepdefinitions;

import cucumber.api.java.After;
import cucumber.api.java.en.Then;
import gr.blxbrgld.list.ListTestBase;
import gr.blxbrgld.list.service.FixtureService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import static net.serenitybdd.rest.SerenityRest.then;

/**
 * Common step definitions
 * @author blxbrgld
 */
public class CommonStepDefinitions extends ListTestBase {

    private static final String ROOT_ELEMENT = "result";

    @Autowired
    private FixtureService fixtureService;

    @After
    public void after() {
        fixtureService.deleteFixtures();
    }

    @Then("^the http response status code is (\\d+)$")
    public void expectedResponseStatus(int status) {
        then().statusCode(status);
    }

    @Then("^the response is (.*)$")
    public void expectedResponse(String response) {
        assertEquals(response, then().extract().asString());
    }

    @Then("^the response contains key (.*) with value (.*)$")
    public void responseContainsEntry(String key, String value) {
        assertTrue(then().extract().jsonPath().get(key).equals(value));
    }

    @Then("^the response does not contain key (.*) with value (.*)$")
    public void responseDoesNotContainEntry(String key, String value) {
        assertFalse(then().extract().jsonPath().get(key).equals(value));
    }

    @Then("^the response contains not null attribute (.*)$")
    public void notNullAttribute(String key) {
        assertNotNull(then().extract().jsonPath().get(key));
    }

    @Then("^the response list contains entry with key (.*) and value (.*)$")
    public void listContainsEntry(String key, String value) {
        assertTrue(then().extract().jsonPath().getList(key).contains(value));
    }

    @Then("^the response list does not contain entry with key (.*) and value (.*)$")
    public void listDoesNotContainEntry(String key, String value) {
        assertFalse(then().extract().jsonPath().getList(key).contains(value));
    }

    @Then("^the response list has size (\\d+)$")
    public void listSize(int size) {
        assertEquals(then().extract().jsonPath().getList("$").size(), size);
    }

    @Then("^(.*) contains in any order (.*)$")
    public void elementContainsInAnyOrder(String element, String values) {
        List<String> actual = then().extract().jsonPath().getList(ROOT_ELEMENT.equals(element) ? "$" : element);
        List<String> expected = Stream.of(values.split(",")).collect(Collectors.toList());
        assertThat(actual, containsInAnyOrder(expected.toArray(new String[expected.size()])));
    }
}
