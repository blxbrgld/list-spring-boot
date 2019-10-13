package gr.blxbrgld.list.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import gr.blxbrgld.list.model.Item;

import java.io.IOException;
import java.util.*;

/**
 * Item Deserializer
 * @author blxbrgld
 */
public class ItemDeserializer extends JsonDeserializer<Item> {

    /**
     * {@inheritDoc}
     */
    @Override
    public Item deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        JsonNode node = parser.getCodec().readTree(parser);
        return Item
            .builder()
            .titleEng(node.get("titleEng").textValue())
            .titleEll(node.get("titleEll").textValue())
            .category(node.get("category").textValue())
            .publisher(node.get("publisher").textValue())
            .description(node.get("description").textValue())
            .year(integerValue(node.get("year")))
            .rating(integerValue(node.get("rating")))
            .subtitles(node.get("subtitles").textValue())
            .discs(integerValue(node.get("discs")))
            .pages(integerValue(node.get("pages")))
            .place(integerValue(node.get("place")))
            .artists(artists(node.get("artists")))
            .comments(comments(node.get("comments")))
            .build();
    }

    /**
     * Build the artists map
     * @param node {@link JsonNode}
     * @return The artists map
     */
    private Map<String, String> artists(JsonNode node) {
        Map<String, String> result = new HashMap<>();
        for(int i = 0; i < node.size(); i++) {
            result.put(
                node.get(i).get("artist").textValue(),
                node.get(i).get("activity").textValue()
            );
        }
        return result;
    }

    /**
     * Build the comments list
     * @param node {@link JsonNode}
     * @return The comments list
     */
    private List<String> comments(JsonNode node) {
        List<String> result = new ArrayList<>();
        for(int i = 0; i < node.size(); i++) {
            result.add(node.get(i).get("comment").textValue());
        }
        return result;
    }

    /**
     * Do not accept zero values for integer input nodes
     * @param node {@link JsonNode}
     * @return The {@link Integer} value
     */
    private Integer integerValue(JsonNode node) {
        return node!=null && node.intValue()>0 ? node.intValue() : null;
    }
}
