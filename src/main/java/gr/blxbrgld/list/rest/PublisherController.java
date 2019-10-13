package gr.blxbrgld.list.rest;

import gr.blxbrgld.list.enums.Order;
import gr.blxbrgld.list.model.Publisher;
import gr.blxbrgld.list.service.PublisherService;
import gr.blxbrgld.list.utils.Tags;
import gr.blxbrgld.list.validators.PublisherDeletionAllowed;
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
 * Publisher Controller
 * @author jenaki
 */
@RestController
@Validated
@RequestMapping("/publishers")
@Api(description = "Reference data related operations", tags = Tags.REFERENCE_DATA)
public class PublisherController {

    @Autowired
    private PublisherService publisherService;

    /**
     * Get Publisher By Id
     * @param id The Id
     * @return {@link Publisher}
     */
    @GetMapping("{id}")
    @ApiOperation(value = "Get Publisher by Id")
    public Publisher get(@ApiParam(value = "The Publisher Id", required = true) @PathVariable("id") Integer id) {
        Optional<Publisher> publisher = publisherService.getPublisher(id);
        if(publisher.isPresent()) {
            return publisher.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Publisher not found.");
        }
    }

    /**
     * List Of Publishers
     * @param order Ascending or Descending Ordering
     * @return List Of {@link Publisher}
     */
    @GetMapping
    @ApiOperation(value = "Retrieve list of Publishers")
    public List<Publisher> list(@ApiParam(value = "List ordering", defaultValue = "ASC", allowableValues = "ASC,DESC") @RequestParam(required = false) String order) {
        return publisherService.getPublishers("title", Order.get(order));
    }

    /**
     * Create a Publisher
     * @param publisher {@link Publisher}
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Create Publisher")
    @TitleNotDuplicate
    public void create(@Valid @ApiParam(value = "Publisher info to create", required = true) @RequestBody Publisher publisher) {
        publisherService.persistOrMergePublisher(publisher);
    }

    /**
     * Update A Publisher
     * @param publisher {@link Publisher}
     */
    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Update Publisher")
    @TitleNotDuplicate
    public void update(@ApiParam(value = "The Publisher Id", required = true) @PathVariable("id") Integer id, @Valid @ApiParam(value = "Publisher info to update", required = true) @RequestBody Publisher publisher) {
        if(!publisherService.getPublisher(id).isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Publisher not found.");
        }
        publisher.setId(id); // Set Publishers's Id
        publisherService.persistOrMergePublisher(publisher);
    }

    /**
     * Delete A Publisher By Id
     * @param id The Id
     */
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Delete Publisher by Id")
    public void delete(@PublisherDeletionAllowed @ApiParam(value = "The Publisher Id", required = true) @PathVariable("id") Integer id) {
        publisherService.deletePublisher(id);
    }
}
