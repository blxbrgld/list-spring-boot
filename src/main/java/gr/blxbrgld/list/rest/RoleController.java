package gr.blxbrgld.list.rest;

import gr.blxbrgld.list.utils.Tags;
import io.swagger.annotations.Api;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Role Controller
 * @author blxbrgld
 */
@RestController
@Validated
@RequestMapping("/role")
@Api(description = "User related operations", tags = Tags.USERS)
public class RoleController {

}
