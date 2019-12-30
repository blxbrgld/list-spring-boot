package gr.blxbrgld.list.rest;

import gr.blxbrgld.list.enums.Order;
import gr.blxbrgld.list.model.Artist;
import gr.blxbrgld.list.service.ArtistService;
import gr.blxbrgld.list.utils.Tags;
import gr.blxbrgld.list.validators.ArtistDeletionAllowed;
import gr.blxbrgld.list.validators.TitleNotDuplicate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * Artist Controller
 * @author blxbrgld
 */
@RestController
@Validated
@RequestMapping("/artists")
@Api(description = "Artist related operations", tags = Tags.ARTISTS)
public class ArtistController {

    @Autowired
    private ArtistService artistService;

    private static final String DEFAULT_ORDER = "title";
    private static final int DEFAULT_START = 0;
    private static final int DEFAULT_SIZE = 100;

    /**
     * Get Artist By Id
     * @param id The Id
     * @return {@link Artist}
     */
    @GetMapping("{id}")
    @ApiOperation(value = "Get Artist by Id")
    public Artist get(@ApiParam(value = "The Artist Id", required = true) @PathVariable("id") Integer id) {
        Optional<Artist> artist = artistService.getArtist(id);
        if(artist.isPresent()) {
            return artist.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Artist not found.");
        }
    }

    /**
     * Get A Random Artist
     * @return {@link Artist}
     */
    @GetMapping("random")
    @ApiOperation(value = "Retrieve a random Artist")
    public Artist random() {
        return artistService.randomArtist();
    }

    /**
     * List Of Artists
     * @param order Ascending or Descending Ordering
     * @return List Of {@link Artist}
     */
    @GetMapping
    @ApiOperation(value = "Retrieve list of Artists")
    public List<Artist> list(
            @ApiParam(value = "List ordering", defaultValue = "ASC", allowableValues = "ASC,DESC") @RequestParam(required = false) String order,
            @ApiParam(value = "Result to start from", defaultValue = "0") @RequestParam(required = false) Integer start,
            @ApiParam(value = "Number of results", defaultValue = "10") @RequestParam(required = false) Integer size) {
        int startFrom = ObjectUtils.defaultIfNull(start, 0);
        int pageSize = ObjectUtils.defaultIfNull(size, 0);
        return artistService.getArtists(
            DEFAULT_ORDER,
            Order.get(order),
            startFrom > 0 ? startFrom : DEFAULT_START,
            pageSize > DEFAULT_SIZE ? DEFAULT_SIZE : pageSize
        );
    }

    /**
     * Create An Artist
     * @param artist {@link Artist}
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Create Artist")
    @TitleNotDuplicate
    public void create(@Valid @ApiParam(value = "Artist to create", required = true) @RequestBody Artist artist) {
        artistService.persistOrMergeArtist(artist);
    }

    /**
     * Update an artist
     * @param id The id to update
     * @param artist {@link Artist}
     */
    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Update Artist")
    @TitleNotDuplicate
    public void update(@ApiParam(value = "The Artist Id", required = true) @PathVariable("id") Integer id, @Valid @ApiParam(value = "Artist to update", required = true) @RequestBody Artist artist) {
        if(!artistService.getArtist(id).isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Artist not found.");
        }
        artist.setId(id); // Set Artist's Id
        artistService.persistOrMergeArtist(artist);
    }

    /**
     * Delete An Artist By Id
     * @param id The Id
     */
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Delete Artist by Id")
    public void delete(@ArtistDeletionAllowed @ApiParam(value = "The Artist Id", required = true) @PathVariable("id") Integer id) {
        artistService.deleteArtist(id);
    }

    /**
     * Count Of Artists
     * @return Count Of Artists
     */
    @GetMapping("count")
    @ApiOperation(value = "Count of Artists")
    public Long count() {
        return artistService.countArtists();
    }
}
