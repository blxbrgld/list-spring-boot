package gr.blxbrgld.list.rest;

import gr.blxbrgld.list.enums.Order;
import gr.blxbrgld.list.model.Comment;
import gr.blxbrgld.list.service.CommentService;
import gr.blxbrgld.list.utils.Tags;
import gr.blxbrgld.list.validators.CommentDeletionAllowed;
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
 * Comment Controller
 * @author blxbrgld
 */
@RestController
@Validated
@RequestMapping("/comments")
@Api(description = "Reference data related operations", tags = Tags.REFERENCE_DATA)
public class CommentController {

    @Autowired
    private CommentService commentService;

    private static final String DEFAULT_ORDER = "title";

    /**
     * Get Comment By Id
     * @param id The Id
     * @return {@link Comment}
     */
    @GetMapping("{id}")
    @ApiOperation(value = "Get Comment by Id")
    public Comment get(@ApiParam(value = "The Comment Id", required = true) @PathVariable("id") Integer id) {
        Optional<Comment> comment = commentService.getComment(id);
        if(comment.isPresent()) {
            return comment.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment not found.");
        }
    }

    /**
     * List Of Comments
     * @param order Ascending or Descending Ordering
     * @return List Of {@link Comment}
     */
    @GetMapping
    @ApiOperation(value = "Retrieve list of Comments")
    public List<Comment> list(@ApiParam(value = "List ordering", defaultValue = "ASC", allowableValues = "ASC,DESC") @RequestParam(required = false) String order) {
        return commentService.getComments(DEFAULT_ORDER, Order.get(order));
    }

    /**
     * Create A Comment
     * @param comment {@link Comment}
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Create Comment")
    @TitleNotDuplicate
    public void create(@Valid @ApiParam(value = "Comment to create", required = true) @RequestBody Comment comment) {
        commentService.persistOrMergeComment(comment);
    }

    /**
     * Update a comment
     * @param id The id to update
     * @param comment {@link Comment}
     */
    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Update Comment")
    @TitleNotDuplicate
    public void update(@ApiParam(value = "The Comment Id", required = true) @PathVariable("id") Integer id, @Valid @ApiParam(value = "Comment to update", required = true) @RequestBody Comment comment) {
        if(!commentService.getComment(id).isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment not found.");
        }
        comment.setId(id); // Set Comment's Id
        commentService.persistOrMergeComment(comment);
    }

    /**
     * Delete A Comment By Id
     * @param id The Id
     */
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Delete Comment by Id")
    public void delete(@CommentDeletionAllowed @ApiParam(value = "The Comment Id", required = true) @PathVariable("id") Integer id) {
        commentService.deleteComment(id);
    }
}
