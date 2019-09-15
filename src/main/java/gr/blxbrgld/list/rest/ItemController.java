package gr.blxbrgld.list.rest;

import gr.blxbrgld.list.utils.Tags;
import io.swagger.annotations.Api;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Item Controller
 * @author blxbrgld
 */
@RestController
@Validated
@RequestMapping("/items")
@Api(description = "Item related operations", tags = Tags.ITEMS)
public class ItemController {

}
