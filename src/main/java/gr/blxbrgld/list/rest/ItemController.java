package gr.blxbrgld.list.rest;

import gr.blxbrgld.list.model.Item;
import gr.blxbrgld.list.service.ItemService;
import gr.blxbrgld.list.utils.Tags;
import gr.blxbrgld.list.validators.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
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

    /**
     * Create an item
     * @param item {@link Item}
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Create Item")
    //TODO @RequiredAttributesPresent
    public void create(@Valid @CategoryExists @ArtistsValid @CommentsValid @SubtitlesValid @PublisherValid @ApiParam(value = "Item to create", required = true) @RequestBody Item item) {
        itemService.persistOrMergeItem(item);
    }

    /**
     * Update an item
     * @param id The id to update
     * @param item {@link Item}
     */
    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Update Item")
    //TODO @RequiredAttributesPresent
    public void update(
            @ApiParam(value = "The Item Id", required = true) @PathVariable("id") Integer id,
            @Valid @CategoryExists @ArtistsValid @CommentsValid @SubtitlesValid @PublisherValid @ApiParam(value = "Item to update", required = true) @RequestBody Item item) {
        if(!itemService.getItem(id).isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found.");
        }
        item.setId(id); // Set Item's Id
        itemService.persistOrMergeItem(item);
    }

    /**
     * Delete an item by id
     * @param id The Id
     */
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Delete Item by Id")
    public void delete(@ApiParam(value = "The Item Id", required = true) @PathVariable("id") Integer id) {
        if(!itemService.getItem(id).isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found.");
        }
        itemService.deleteItem(id);
    }
}
