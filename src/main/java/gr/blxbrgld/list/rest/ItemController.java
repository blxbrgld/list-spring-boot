package gr.blxbrgld.list.rest;

import gr.blxbrgld.list.utils.Tags;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Item Controller
 * @author blxbrgld
 */
@RestController
@Validated
@RequestMapping("/item")
@Api(description = "Item related operations", tags = Tags.ITEMS)
public class ItemController {

    @GetMapping
    @ApiOperation("To be deleted")
    public void inProgress() {
        //TODO To Be Deleted
    }
}
