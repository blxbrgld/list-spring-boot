package gr.blxbrgld.list.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import gr.blxbrgld.list.model.Category;

import java.io.IOException;

/**
 * Category serializer for REST-assured calls
 * @author blxbrgld
 */
public class CategorySerializer extends StdSerializer<Category> {

    private static final String TITLE_ATTRIBUTE = "title";
    private static final String PARENT_ATTRIBUTE = "parent";

    /**
     * Constructor
     */
    public CategorySerializer() {
        this(null);
    }

    /**
     * Constructor
     * @param category {@link Category}
     */
    public CategorySerializer(Class<Category> category) {
        super(category);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void serialize(Category category, JsonGenerator generator, SerializerProvider provider) throws IOException {
        generator.writeStartObject();
        generator.writeStringField(TITLE_ATTRIBUTE, category.getTitle());
        generator.writeStringField(PARENT_ATTRIBUTE, category.getParent()!=null ? category.getParent().getTitle() : null);
        generator.writeEndObject();
    }
}
