package gr.blxbrgld.list.rest;

import gr.blxbrgld.list.enums.Order;
import gr.blxbrgld.list.model.Role;
import gr.blxbrgld.list.service.RoleService;
import gr.blxbrgld.list.utils.Tags;
import gr.blxbrgld.list.validators.RoleDeletionAllowed;
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
 * Role Controller
 * @author blxbrgld
 */
@RestController
@Validated
@RequestMapping("/roles")
@Api(description = "User related operations", tags = Tags.USERS)
public class RoleController {

    @Autowired
    private RoleService roleService;

    private static final String DEFAULT_ORDER = "title";

    /**
     * Get role by Id
     * @param id The Id
     * @return {@link Role}
     */
    @GetMapping("{id}")
    @ApiOperation(value = "Get Role by Id")
    public Role get(@ApiParam(value = "The Role Id", required = true) @PathVariable("id") Integer id) {
        Optional<Role> role = roleService.getRole(id);
        if(role.isPresent()) {
            return role.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found.");
        }
    }

    /**
     * List of roles
     * @param order Ascending or Descending Ordering
     * @return List Of {@link Role}
     */
    @GetMapping
    @ApiOperation(value = "Retrieve list of Roles")
    public List<Role> list(@ApiParam(value = "List ordering", defaultValue = "ASC", allowableValues = "ASC,DESC") @RequestParam(required = false) String order) {
        return roleService.getRoles(DEFAULT_ORDER, Order.get(order));
    }

    /**
     * Create a role
     * @param role {@link Role}
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Create Role")
    @TitleNotDuplicate
    public void create(@Valid @ApiParam(value = "Role to create", required = true) @RequestBody Role role) {
        roleService.persistOrMergeRole(role);
    }

    /**
     * Update a role
     * @param id The id to update
     * @param role {@link Role}
     */
    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Update Role")
    @TitleNotDuplicate
    public void update(@ApiParam(value = "The Role Id", required = true) @PathVariable("id") Integer id, @Valid @ApiParam(value = "Role to update", required = true) @RequestBody Role role) {
        if(!roleService.getRole(id).isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found.");
        }
        role.setId(id); // Set Role's Id
        roleService.persistOrMergeRole(role);
    }

    /**
     * Delete a role by id
     * @param id The id
     */
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Delete Role by Id")
    public void delete(@RoleDeletionAllowed @ApiParam(value = "The Role Id", required = true) @PathVariable("id") Integer id) {
        roleService.deleteRole(id);
    }
}
