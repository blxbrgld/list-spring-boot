package gr.blxbrgld.list.validators;

import gr.blxbrgld.list.dao.hibernate.ActivityDao;
import gr.blxbrgld.list.dao.hibernate.ArtistActivityItemDao;
import gr.blxbrgld.list.model.Activity;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;
import java.util.Optional;

/**
 * ActivityDeletionAllowed Annotation
 * @author blxbrgld
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {ActivityDeletionAllowed.ActivityDeletionAllowedValidator.class})
@Documented
public @interface ActivityDeletionAllowed {

    String message() default "Activity does not exist or is related with an Item.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    /**
     * ActivityDeletionAllowed Validator To Ensure That The Given Activity Id Exist And Is Not Related With An Item
     * @author blxbrgld
     */
    class ActivityDeletionAllowedValidator implements ConstraintValidator<ActivityDeletionAllowed, Integer> {

        @Autowired
        private ActivityDao activityDao;

        @Autowired
        private ArtistActivityItemDao artistActivityItemDao;

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean isValid(Integer id, ConstraintValidatorContext context) {
            Optional<Activity> activity = activityDao.get(id);
            return activity.isPresent() && !artistActivityItemDao.havingActivityExists(activity.get());
        }
    }
}
