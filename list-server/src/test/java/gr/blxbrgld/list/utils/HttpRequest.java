package gr.blxbrgld.list.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import gr.blxbrgld.list.jackson.CategorySerializer;
import gr.blxbrgld.list.jackson.ItemSerializer;
import gr.blxbrgld.list.jackson.UserSerializer;
import gr.blxbrgld.list.model.Category;
import gr.blxbrgld.list.model.Item;
import gr.blxbrgld.list.model.User;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.mapper.factory.Jackson2ObjectMapperFactory;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.web.util.UriComponentsBuilder;

import java.lang.reflect.Type;
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
     * {@link RestAssuredConfig} to register Jackson serializers needed for performing the requests
     */
    private RestAssuredConfig configuration = RestAssuredConfig.newConfig().objectMapperConfig(
        ObjectMapperConfig.objectMapperConfig().jackson2ObjectMapperFactory(new Jackson2ObjectMapperFactory() {

            /**
             * {@inheritDoc}
             */
            @Override
            public ObjectMapper create(Type type, String value) {
                ObjectMapper objectMapper = new ObjectMapper();
                SimpleModule module = new SimpleModule("E2E");
                module.addSerializer(Category.class, new CategorySerializer());
                module.addSerializer(User.class, new UserSerializer());
                module.addSerializer(Item.class, new ItemSerializer());
                objectMapper.registerModule(module);
                return objectMapper;
            }
        })
    );

    /**
     * Constructor
     * @param endpoint The endpoint
     */
    public HttpRequest(String endpoint) {
        this.endpoint = endpoint;
        this.requestSpecification = given().config(configuration);
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