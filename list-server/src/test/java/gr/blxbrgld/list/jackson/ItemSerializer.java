package gr.blxbrgld.list.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import gr.blxbrgld.list.model.Item;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.util.stream.Collectors;

/**
 * Item serializer for REST-assured calls
 * @author blxbrgld
 */
public class ItemSerializer extends StdSerializer<Item> {

    /**
     * Constructor
     */
    public ItemSerializer() {
        this(null);
    }

    /**
     * Constructor
     * @param item {@link Item}
     */
    public ItemSerializer(Class<Item> item) {
        super(item);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void serialize(Item item, JsonGenerator generator, SerializerProvider provider) throws IOException {
        generator.writeStartObject();
        generator.writeStringField("titleEng", item.getTitleEng());
        generator.writeStringField("titleEll", item.getTitleEll());
        generator.writeStringField("category", item.getCategory()!=null ? item.getCategory().getTitle() : null);
        generator.writeStringField("publisher", item.getPublisher()!=null ? item.getPublisher().getTitle() : null);
        generator.writeStringField("description", item.getDescription());
        generator.writeObjectField("year", item.getYear());
        generator.writeObjectField("rating", item.getRating());
        generator.writeStringField("subtitles", item.getSubtitles()!=null ? item.getSubtitles().getTitle() : null);
        generator.writeObjectField("discs", item.getDiscs());
        generator.writeObjectField("pages", item.getPages());
        generator.writeObjectField("place", item.getPlace());
        generator.writeObjectField("artists",
            item.getArtistActivityItems()
                .stream()
                .map(i -> new ArtistNode(i.getIdArtist().getTitle(), i.getIdActivity().getTitle()))
                .collect(Collectors.toList())
        );
        generator.writeObjectField("comments",
            item.getCommentItems()
                .stream()
                .map(i -> new CommentNode(i.getIdComment().getTitle()))
                .collect(Collectors.toList())
        );
        generator.writeEndObject();
    }

    /**
     * ArtistNode to serialize the artists list
     * @author blxbrgld
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private class ArtistNode {
        private String artist;
        private String activity;
    }

    /**
     * CommentNode to serialize the artists list
     * @author blxbrgld
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private class CommentNode {
        private String comment;
    }
}
