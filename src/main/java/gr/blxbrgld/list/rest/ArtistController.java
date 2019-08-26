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
@RequestMapping("/artist")
@Api(description = "Artist related operations", tags = Tags.ARTISTS)
public class ArtistController {

    @Autowired
    private ArtistService artistService;

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
    @GetMapping("list")
    @ApiOperation(value = "Retrieve list of Artists")
    public List<Artist> list(
            @ApiParam(value = "List ordering", defaultValue = "ASC", allowableValues = "ASC,DESC") @RequestParam(required = false) String order,
            @ApiParam(value = "Result to start from", defaultValue = "0") @RequestParam(required = false) int first,
            @ApiParam(value = "Number of results", defaultValue = "10") @RequestParam(required = false) int size) {
        //TODO Handle The size Params, Set A Limit etc.
        return artistService.getArtists("title", Order.get(order), first, size);
    }

    /**
     * Create An Artist
     * @param artist {@link Artist}
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Create Artist")
    public void create(@Valid @TitleNotDuplicate @ApiParam(value = "Artist info to create", required = true) @RequestBody Artist artist) {
        artistService.persistOrMergeArtist(artist);
    }

    /**
     * Update An Artist
     * @param artist {@link Artist}
     */
    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Update Artist")
    public void update(@ApiParam(value = "The Artist Id", required = true) @PathVariable("id") Integer id, @Valid @TitleNotDuplicate @ApiParam(value = "Artist info to update", required = true) @RequestBody Artist artist) {
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
