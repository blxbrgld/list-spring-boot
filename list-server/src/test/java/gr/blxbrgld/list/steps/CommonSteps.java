package gr.blxbrgld.list.steps;

import gr.blxbrgld.list.utils.HttpMethod;
import gr.blxbrgld.list.utils.HttpRequest;
import io.restassured.http.Header;
import net.thucydides.core.annotations.Step;

import java.util.List;
import java.util.Map;

/**
 * Common Steps
 * @author blxbrgld
 */
public class CommonSteps {

    /**
     * HTTP request
     * @param method {@link HttpMethod}
     * @param endpoint The endpoint
     */
    @Step("HTTP request")
    public void request(HttpMethod method, String endpoint) {
        performRequest(method, endpoint, null, null, null);
    }

    /**
     * HTTP request
     * @param method {@link HttpMethod}
     * @param endpoint The endpoint
     * @param params Map of parameters
     */
    @Step("HTTP request with parameters")
    public void request(HttpMethod method, String endpoint, Map<String, String> params) {
        performRequest(method, endpoint, null, null, params);
    }

    /**
     * HTTP request
     * @param method {@link HttpMethod}
     * @param endpoint The endpoint
     * @param body The body
     */
    @Step("HTTP request with body")
    public void request(HttpMethod method, String endpoint, Object body) {
        performRequest(method, endpoint, null, body, null);
    }

    /**
     * Perform the HTTP request
     * @param method {@link HttpMethod}
     * @param endpoint The endpoint
     * @param headers List of {@link Header}
     * @param body The body
     * @param params Map of parameters
     */
    @Step("Perform the HTTP request")
    public void performRequest(HttpMethod method, String endpoint, List<Header> headers, Object body, Map<String, String> params) {
        new HttpRequest(endpoint)
            .body(body)
            .headers(headers)
            .params(params)
            .execute(method)
            .prettyPeek();
    }
}