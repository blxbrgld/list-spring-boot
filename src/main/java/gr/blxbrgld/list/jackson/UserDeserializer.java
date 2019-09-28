package gr.blxbrgld.list.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import gr.blxbrgld.list.model.User;

import java.io.IOException;

/**
 * User Deserializer
 * @author blxbrgld
 */
public class UserDeserializer extends JsonDeserializer<User> {

    /**
     * {@inheritDoc}
     */
    @Override
    public User deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        JsonNode node = parser.getCodec().readTree(parser);
        return User
            .builder()
            .username(node.get("username").textValue())
            .password(node.get("password").textValue())
            .email(node.get("email").textValue())
            .role(node.get("role").textValue())
            .build();
    }
}
