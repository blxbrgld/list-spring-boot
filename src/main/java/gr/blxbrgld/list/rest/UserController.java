package gr.blxbrgld.list.rest;

import gr.blxbrgld.list.model.User;
import gr.blxbrgld.list.service.UserService;
import gr.blxbrgld.list.utils.Tags;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

/**
 * User Controller
 * @author blxbrgld
 */
@RestController
@Validated
@RequestMapping("/user")
@Api(description = "User related operations", tags = Tags.USERS)
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Get User by Username
     * @param username The Username
     * @return {@link User}
     */
    @GetMapping("{username}")
    @ApiOperation(value = "Get User by Username")
    public User get(@ApiParam(value = "The Username", required = true) @PathVariable("username") String username) {
        Optional<User> user = userService.getUserByUsername(username);
        if(user.isPresent()) {
            return user.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
        }
    }
}
