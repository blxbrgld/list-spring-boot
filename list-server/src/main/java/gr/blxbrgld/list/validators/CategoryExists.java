package gr.blxbrgld.list.validators;

import gr.blxbrgld.list.model.Item;
import gr.blxbrgld.list.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * CategoryExists Annotation
 * @author blxbrgld
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {CategoryExists.CategoryExistsValidator.class})
@Documented
public @interface CategoryExists {

    String message() default "The category does not exist.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    /**
     * Validate the existence of the category
     * @author blxbrgld
     */
    class CategoryExistsValidator implements ConstraintValidator<CategoryExists, Item> {

        @Autowired
        private CategoryService categoryService;

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean isValid(Item item, ConstraintValidatorContext context) {
            return categoryService.getCategory(item.getCategory().getTitle()).isPresent();
        }
    }
}
