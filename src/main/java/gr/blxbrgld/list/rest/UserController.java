package gr.blxbrgld.list.rest;

import gr.blxbrgld.list.enums.Order;
import gr.blxbrgld.list.model.User;
import gr.blxbrgld.list.service.UserService;
import gr.blxbrgld.list.utils.Tags;
import gr.blxbrgld.list.validators.EmailNotDuplicate;
import gr.blxbrgld.list.validators.RoleValid;
import gr.blxbrgld.list.validators.UsernameNotDuplicate;
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
 * User Controller
 * @author blxbrgld
 */
@RestController
@Validated
@RequestMapping("/users")
@Api(description = "User related operations", tags = Tags.USERS)
public class UserController {

    @Autowired
    private UserService userService;

    private static final String DEFAULT_ORDER = "username";

    /**
     * Get user by Id
     * @param id The Id
     * @return {@link User}
     */
    @GetMapping("{id}")
    @ApiOperation(value = "Get User by Id")
    public User get(@ApiParam(value = "The User Id", required = true) @PathVariable("id") Integer id) {
        Optional<User> user = userService.getUser(id);
        if(user.isPresent()) {
            return user.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
        }
    }

    /**
     * List of users
     * @param order Ascending or Descending Ordering
     * @return List Of {@link User}
     */
    @GetMapping
    @ApiOperation(value = "Retrieve list of Users")
    public List<User> list(@ApiParam(value = "List ordering", defaultValue = "ASC", allowableValues = "ASC,DESC") @RequestParam(required = false) String order) {
        return userService.getUsers(DEFAULT_ORDER, Order.get(order));
    }

    /**
     * Create a user
     * @param user {@link User}
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Create User")
    @UsernameNotDuplicate
    @EmailNotDuplicate
    public void create(@Valid @RoleValid @ApiParam(value = "User to create", required = true) @RequestBody User user) {
        userService.persistOrMergeUser(user);
    }

    /**
     * Update a user
     * @param id The id to update
     * @param user {@link User}
     */
    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Update User")
    @UsernameNotDuplicate
    @EmailNotDuplicate
    public void update(@ApiParam(value = "The User Id", required = true) @PathVariable("id") Integer id, @Valid @RoleValid @ApiParam(value = "User to update", required = true) @RequestBody User user) {
        if(!userService.getUser(id).isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
        }
        user.setId(id); // Set User's Id
        userService.persistOrMergeUser(user);
    }

    /**
     * Delete a user by id
     * @param id The id
     */
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Delete User by Id")
    public void delete(@ApiParam(value = "The User Id", required = true) @PathVariable("id") Integer id) {
        userService.deleteUser(id);
    }
}
