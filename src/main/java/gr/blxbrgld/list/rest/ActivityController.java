package gr.blxbrgld.list.rest;

import gr.blxbrgld.list.enums.Order;
import gr.blxbrgld.list.model.Activity;
import gr.blxbrgld.list.service.ActivityService;
import gr.blxbrgld.list.utils.Tags;
import gr.blxbrgld.list.validators.ActivityDeletionAllowed;
import gr.blxbrgld.list.validators.TitleNotDuplicate;
import io.swagger.annotations.*;
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
@RequestMapping("/activities")
@Api(description = "Reference data related operations", tags = Tags.REFERENCE_DATA)
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    /**
     * Get Activity By Id
     * @param id The Id
     * @return {@link Activity}
     */
    @GetMapping("{id}")
    @ApiOperation(value = "Get Activity by Id")
    public Activity get(@ApiParam(value = "The Activity Id", required = true) @PathVariable("id") Integer id) {
        Optional<Activity> activity = activityService.getActivity(id);
        if(activity.isPresent()) {
            return activity.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Activity not found.");
        }
    }

    /**
     * List Of Activities
     * @param order Ascending or Descending Ordering
     * @return List Of {@link Activity}
     */
    @GetMapping
    @ApiOperation(value = "Retrieve list of Activities")
    public List<Activity> list(@ApiParam(value = "List ordering", defaultValue = "ASC", allowableValues = "ASC,DESC") @RequestParam(required = false) String order) {
        return activityService.getActivities("title", Order.get(order));
    }

    /**
     * Create an Activity
     * @param activity {@link Activity}
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Create Activity")
    public void create(@Valid @TitleNotDuplicate @ApiParam(value = "Activity info to create", required = true) @RequestBody Activity activity) {
        activityService.persistOrMergeActivity(activity);
    }

    /**
     * Update An Activity
     * @param activity {@link Activity}
     */
    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Update Activity")
    public void update(@ApiParam(value = "The Activity Id", required = true) @PathVariable("id") Integer id, @Valid @TitleNotDuplicate @ApiParam(value = "Activity info to update", required = true) @RequestBody Activity activity) {
        if(!activityService.getActivity(id).isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Activity not found.");
        }
        activity.setId(id); // Set Activity's Id
        activityService.persistOrMergeActivity(activity);
    }

    /**
     * Delete An Activity By Id
     * @param id The Id
     */
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Delete Activity by Id")
    public void delete(@ActivityDeletionAllowed @ApiParam(value = "The Activity Id", required = true) @PathVariable("id") Integer id) {
        activityService.deleteActivity(id);
    }

    /**
     * Count Of Activities
     * @return Count Of Activities
     */
    @GetMapping("count")
    @ApiOperation(value = "Count of Activities")
    public Long count() {
        return activityService.countActivities();
    }
}
