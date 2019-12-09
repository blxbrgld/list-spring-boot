package gr.blxbrgld.list.validators;

import gr.blxbrgld.list.model.Item;
import gr.blxbrgld.list.service.PublisherService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * PublisherValid Annotation
 * @author blxbrgld
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {PublisherValid.PublisherValidator.class})
@Documented
public @interface PublisherValid {

    String message() default "The publisher does not exist.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    /**
     * Validate the existence of the publisher
     * @author blxbrgld
     */
    class PublisherValidator implements ConstraintValidator<PublisherValid, Item> {

        @Autowired
        private PublisherService publisherService;

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean isValid(Item item, ConstraintValidatorContext context) {
            //noinspection SimplifiableIfStatement
            if(item.getPublisher()!=null && StringUtils.trimToNull(item.getPublisher().getTitle())!=null) {
                return publisherService.getPublisher(item.getPublisher().getTitle()).isPresent();
            }
            return true; // No publisher given, proceed
        }
    }
}
