package gr.blxbrgld.list.rest;

import gr.blxbrgld.list.model.Item;
import gr.blxbrgld.list.service.ItemService;
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
 * Item Controller
 * @author blxbrgld
 */
@RestController
@Validated
@RequestMapping("/items")
@Api(description = "Item related operations", tags = Tags.ITEMS)
public class ItemController {

    @Autowired
    private ItemService itemService;

    /**
     * Get item by id
     * @param id The id
     * @return {@link Item}
     */
    @GetMapping("{id}")
    @ApiOperation(value = "Get Item by Id")
    public Item get(@ApiParam(value = "The Item Id", required = true) @PathVariable("id") Integer id) {
        Optional<Item> item = itemService.getItem(id);
        if(item.isPresent()) {
            return item.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found.");
        }
    }
}
