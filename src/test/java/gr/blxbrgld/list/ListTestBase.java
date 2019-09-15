package gr.blxbrgld.list;

import gr.blxbrgld.list.configuration.ApplicationConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Base Spring test class to setup the web environment
 * @author blxbrgld
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(
    classes = ApplicationConfiguration.class,
    webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT
)
public class ListTestBase {

    @Test
    public void noRunnableMethods() {}
}
