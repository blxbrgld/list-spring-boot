package gr.blxbrgld.list.validators;

import gr.blxbrgld.list.model.Category;
import gr.blxbrgld.list.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * ParentCategoryValid Annotation
 * @author blxbrgld
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {ParentCategoryValid.ParentCategoryValidator.class})
@Documented
public @interface ParentCategoryValid {

    String message() default "Parent category is not valid.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    /**
     * Validate the parent category of the category to be created / updated
     * @author blxbrgld
     */
    class ParentCategoryValidator implements ConstraintValidator<ParentCategoryValid, Category> {

        @Autowired
        private CategoryService categoryService;

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean isValid(Category category, ConstraintValidatorContext context) {
            return category.getParent() == null // No parent given
                || (!category.getTitle().equalsIgnoreCase(category.getParent().getTitle()) // Parent title is not the same as the child category
                    && categoryService.getCategory(category.getParent().getTitle()).isPresent()); // Parent category exists
        }
    }
}
