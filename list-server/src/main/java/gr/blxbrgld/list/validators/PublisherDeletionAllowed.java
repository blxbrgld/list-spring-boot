package gr.blxbrgld.list.validators;

import gr.blxbrgld.list.dao.hibernate.ItemDao;
import gr.blxbrgld.list.dao.hibernate.PublisherDao;
import gr.blxbrgld.list.model.Publisher;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;
import java.util.Optional;

/**
 * PublisherDeletionAllowed Annotation
 * @author jenaki
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {PublisherDeletionAllowed.PublisherDeletionAllowedValidator.class})
@Documented
public @interface PublisherDeletionAllowed {

    String message() default "Publisher does not exist or is related with an Item.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    /**
     * PublisherDeletionAllowed Validator To Ensure That The Given Publisher Id Exist And Is Not Related With An Item
     * @author jenaki
     */
    class PublisherDeletionAllowedValidator implements ConstraintValidator<PublisherDeletionAllowed, Integer> {

        @Autowired
        private PublisherDao publisherDao;

        @Autowired
        private ItemDao itemDao;

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean isValid(Integer id, ConstraintValidatorContext context) {
            Optional<Publisher> publisher = publisherDao.get(id);
            return publisher.isPresent() && !itemDao.havingPublisherExists(publisher.get());
        }
    }
}
