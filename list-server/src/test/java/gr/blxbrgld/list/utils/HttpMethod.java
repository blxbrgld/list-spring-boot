package gr.blxbrgld.list.utils;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.net.URI;

/**
 * Wrapper for {@link Response} in order to support all possible HTTP verbs
 * @author blxbrgld
 */
public enum HttpMethod {

    GET {
        @Override
        public Response httpMethod(RequestSpecification specification, URI uri) {
            return specification.get(uri);
        }
    },
    PUT {
        @Override
        public Response httpMethod(RequestSpecification specification, URI uri) {
            return specification.put(uri);
        }
    },
    POST {
        @Override
        public Response httpMethod(RequestSpecification specification, URI uri) {
            return specification.post(uri);
        }
    },
    DELETE {
        @Override
        public Response httpMethod(RequestSpecification specification, URI uri) {
            return specification.delete(uri);
        }
    };

    public abstract Response httpMethod(RequestSpecification specification, URI uri);
}