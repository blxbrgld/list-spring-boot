package gr.blxbrgld.list.utils;

import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;

import static net.serenitybdd.rest.SerenityRest.given;

/**
 * Build the {@link RequestSpecification} and execute the request
 * @author blxbrgld
 */
public class HttpRequest {

    private static final String APPLICATION_TYPE = "application/json";
    private static final Header ACCEPT_HEADER = new Header("Accept", APPLICATION_TYPE);

    private final RequestSpecification requestSpecification;
    private final String endpoint;

    /**
     * Constructor
     * @param endpoint The endpoint
     */
    public HttpRequest(String endpoint) {
        this.endpoint = endpoint;
        this.requestSpecification = given();
    }

    /**
     * Handle the body
     * @param body The body
     * @return {@link HttpRequest}
     */
    public HttpRequest body(Object body) {
        if(body!=null) {
            requestSpecification.body(body);
        }
        return this;
    }

    /**
     * Handle the headers
     * @param headers List of {@link Header}
     * @return {@link HttpRequest}
     */
    public HttpRequest headers(List<Header> headers) {
        requestSpecification
            .header(ACCEPT_HEADER)
            .contentType(APPLICATION_TYPE)
            .log().all();
        if(!CollectionUtils.isEmpty(headers)) {
            requestSpecification.headers(new Headers(headers));
        }
        return this;
    }

    /**
     * Handle the parameters
     * @param params The parameters as key/value pairs
     * @return {@link HttpRequest}
     */
    public HttpRequest params(Map<String, String> params) {
        if(!MapUtils.isEmpty(params)) {
            requestSpecification.params(params);
        }
        return this;
    }

    /**
     * Execute the request
     * @param method {@link HttpMethod}
     * @return {@link Response}
     */
    public Response execute(HttpMethod method) {
        return method.httpMethod(requestSpecification, UriComponentsBuilder.fromPath(this.endpoint).build().toUri());
    }
}