package gr.blxbrgld.list.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import gr.blxbrgld.list.model.User;

import java.io.IOException;

/**
 * User serializer for REST-assured calls
 * @author blxbrgld
 */
public class UserSerializer extends StdSerializer<User> {

    /**
     * Constructor
     */
    public UserSerializer() {
        this(null);
    }

    /**
     * Constructor
     * @param user {@link User}
     */
    public UserSerializer(Class<User> user) {
        super(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void serialize(User user, JsonGenerator generator, SerializerProvider provider) throws IOException {
        generator.writeStartObject();
        generator.writeStringField("username", user.getUsername());
        generator.writeStringField("password", user.getPassword());
        generator.writeStringField("email", user.getEmail());
        generator.writeStringField("role", user.getRole().getTitle());
        generator.writeEndObject();
    }
}
