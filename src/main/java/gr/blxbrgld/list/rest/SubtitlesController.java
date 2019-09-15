package gr.blxbrgld.list.rest;

import gr.blxbrgld.list.enums.Order;
import gr.blxbrgld.list.model.Subtitles;
import gr.blxbrgld.list.service.SubtitlesService;
import gr.blxbrgld.list.utils.Tags;
import gr.blxbrgld.list.validators.SubtitlesDeletionAllowed;
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
 * Activity Controller
 * @author blxbrgld
 */
@RestController
@Validated
@RequestMapping("/subtitles")
@Api(description = "Reference data related operations", tags = Tags.REFERENCE_DATA)
public class SubtitlesController {

    @Autowired
    private SubtitlesService subtitlesService;

    /**
     * Get Subtitle By Id
     * @param id The Id
     * @return {@link Subtitles}
     */
    @GetMapping("{id}")
    @ApiOperation(value = "Get Subtitle by Id")
    public Subtitles get(@ApiParam(value = "The Subtitle Id", required = true) @PathVariable("id") Integer id) {
        Optional<Subtitles> subtitles = subtitlesService.getSubtitles(id);
        if(subtitles.isPresent()) {
            return subtitles.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Subtitle not found.");
        }
    }

    /**
     * List Of Subtitles
     * @param order Ascending or Descending Ordering
     * @return List Of {@link Subtitles}
     */
    @GetMapping
    @ApiOperation(value = "Retrieve list of Subtitles")
    public List<Subtitles> list(@ApiParam(value = "List ordering", defaultValue = "ASC", allowableValues = "ASC,DESC") @RequestParam(required = false) String order) {
        return subtitlesService.getSubtitles("title", Order.get(order));
    }

    /**
     * Create a Subtitle
     * @param subtitles {@link Subtitles}
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Create Subtitle")
    public void create(@Valid @TitleNotDuplicate @ApiParam(value = "Subtitle info to create", required = true) @RequestBody Subtitles subtitles) {
        subtitlesService.persistOrMergeSubtitles(subtitles);
    }

    /**
     * Update A Subtitle
     * @param subtitles {@link Subtitles}
     */
    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Update Subtitle")
    public void update(@ApiParam(value = "The Subtitle Id", required = true) @PathVariable("id") Integer id, @Valid @TitleNotDuplicate @ApiParam(value = "Subtitle info to update", required = true) @RequestBody Subtitles subtitles) {
        if(!subtitlesService.getSubtitles(id).isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Subtitle not found.");
        }
        subtitles.setId(id); // Set Subtitles's Id
        subtitlesService.persistOrMergeSubtitles(subtitles);
    }

    /**
     * Delete A Subtitle By Id
     * @param id The Id
     */
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Delete Subtitle by Id")
    public void delete(@SubtitlesDeletionAllowed @ApiParam(value = "The Subtitle Id", required = true) @PathVariable("id") Integer id) {
        subtitlesService.deleteSubtitles(id);
    }
}
