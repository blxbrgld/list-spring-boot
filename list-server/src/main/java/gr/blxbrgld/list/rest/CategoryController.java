package gr.blxbrgld.list.rest;

import gr.blxbrgld.list.enums.CategoryFilter;
import gr.blxbrgld.list.model.Category;
import gr.blxbrgld.list.service.CategoryService;
import gr.blxbrgld.list.utils.Tags;
import gr.blxbrgld.list.validators.CategoryDeletionAllowed;
import gr.blxbrgld.list.validators.ParentCategoryValid;
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
 * Category Controller
 * @author blxbrgld
 */
@RestController
@Validated
@RequestMapping("/categories")
@Api(description = "Reference data related operations", tags = Tags.REFERENCE_DATA)
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * Get category by Id
     * @param id The Id
     * @return {@link Category}
     */
    @GetMapping("{id}")
    @ApiOperation(value = "Get Category by Id")
    public Category get(@ApiParam(value = "The Category Id", required = true) @PathVariable("id") Integer id) {
        Optional<Category> category = categoryService.getCategory(id);
        if(category.isPresent()) {
            return category.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found.");
        }
    }

    /**
     * List of categories
     * @param filter Category filter
     * @return List of {@link Category}
     */
    @GetMapping
    @ApiOperation(value = "Retrieve list of Categories")
    public List<Category> list(@ApiParam(value = "Category Filter", defaultValue = "NONE", allowableValues = "NONE,MENU,DROPDOWN") @RequestParam(required = false) String filter) {
        return categoryService.getCategories(CategoryFilter.get(filter));
    }

    /**
     * Create a category
     * @param category {@link Category}
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Create Category")
    @TitleNotDuplicate
    public void create(@Valid @ParentCategoryValid @ApiParam(value = "Category to create", required = true) @RequestBody Category category) {
        categoryService.persistOrMergeCategory(category);
    }

    /**
     * Update a category
     * @param id The id to update
     * @param category {@link Category}
     */
    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Update Category")
    @TitleNotDuplicate
    public void update(@ApiParam(value = "The Category Id", required = true) @PathVariable("id") Integer id, @Valid @ParentCategoryValid @ApiParam(value = "Category to update", required = true) @RequestBody Category category) {
        if(!categoryService.getCategory(id).isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found.");
        }
        category.setId(id); // Set Category's Id
        categoryService.persistOrMergeCategory(category);
    }

    /**
     * Delete category by Id
     * @param id The Id
     */
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Delete Category by Id")
    public void delete(@CategoryDeletionAllowed @ApiParam(value = "The Category Id", required = true) @PathVariable("id") Integer id) {
        categoryService.deleteCategory(id);
    }
}
