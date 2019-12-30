package gr.blxbrgld.list.validators;

import gr.blxbrgld.list.dao.hibernate.CategoryDao;
import gr.blxbrgld.list.dao.hibernate.ItemDao;
import gr.blxbrgld.list.model.Category;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;
import java.util.Optional;

/**
 * CategoryDeletionAllowed Annotation
 * @author blxbrgld
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {CategoryDeletionAllowed.CategoryDeletionAllowedValidator.class})
@Documented
public @interface CategoryDeletionAllowed {

    String message() default "Category does not exist or is related with an Item.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    /**
     * CategoryDeletionAllowed Validator To Ensure That The Given Category Id Exist, Has No Child Categories And Is Not Related With An Item
     * @author blxbrgld
     */
    class CategoryDeletionAllowedValidator implements ConstraintValidator<CategoryDeletionAllowed, Integer> {

        @Autowired
        private CategoryDao categoryDao;

        @Autowired
        private ItemDao itemDao;

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean isValid(Integer id, ConstraintValidatorContext context) {
            Optional<Category> category = categoryDao.get(id);
            return category.isPresent() && categoryDao.findByParent(category.get()).isEmpty() && !itemDao.havingCategoryExists(category.get());
        }
    }
}
