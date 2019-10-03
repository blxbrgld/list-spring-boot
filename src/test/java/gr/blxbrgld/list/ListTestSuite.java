package gr.blxbrgld.list;

import cucumber.api.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

/**
 * Cucumber Test Suite
 * @author blxbrgld
 */
@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
    features = { "src/test/resources/features" },
    plugin = { "pretty" },
    monochrome = true,
    tags = "@items"
)
public class ListTestSuite {}
