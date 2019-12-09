package gr.blxbrgld.list.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import gr.blxbrgld.list.model.Category;

import java.io.IOException;

/**
 * Category Deserializer
 * @author blxbrgld
 */
public class CategoryDeserializer extends JsonDeserializer<Category> {

    /**
     * {@inheritDoc}
     */
    @Override
    public Category deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        JsonNode node = parser.getCodec().readTree(parser);
        JsonNode parent = node.get("parent");
        return Category
            .builder()
            .title(node.get("title").textValue())
            .parent(parent!=null ? parent.textValue() : null)
            .build();
    }
}
