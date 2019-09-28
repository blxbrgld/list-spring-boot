package gr.blxbrgld.list.validators;

import gr.blxbrgld.list.dao.hibernate.*;
import gr.blxbrgld.list.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import javax.validation.constraintvalidation.SupportedValidationTarget;
import javax.validation.constraintvalidation.ValidationTarget;
import java.lang.annotation.*;
import java.util.Optional;

/**
 * UsernameNotDuplicate Annotation
 * @author blxbrgld
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { UsernameNotDuplicate.UsernameNotDuplicateValidator.class })
@Documented
public @interface UsernameNotDuplicate {

    String message() default "The username already exists.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    /**
     * For an update endpoint validate id and user, for a create endpoint just the user
     * @author blxbrgld
     */
    @Slf4j
    @SupportedValidationTarget(ValidationTarget.PARAMETERS)
    class UsernameNotDuplicateValidator implements ConstraintValidator<UsernameNotDuplicate, Object[]> {

        @Autowired
        private UserDao userDao;

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean isValid(Object[] params, ConstraintValidatorContext constraintValidatorContext) {
            try {
                if (params.length == 2) {
                    Optional<Integer> existing = existing(params[1]);
                    return !(existing.isPresent() && !existing.get().equals(params[0]));
                } else if (params.length == 1) {
                    Optional<Integer> existing = existing(params[0]);
                    return !existing.isPresent();
                } else {
                    log.error("@UsernameNotDuplicate cannot be applied to this method.");
                    return false;
                }
            } catch (Exception exception) {
                log.error("@UsernameNotDuplicate cannot be applied to this object.");
                return false;
            }
        }

        /**
         * Check if the given object is a {@link User} and if it is and it's username exists return the existing id
         * @param object {@link Object}
         * @return The user's id or empty optional if user does not exist
         * @throws Exception thrown if the given object is not a {@link User}
         */
        private Optional<Integer> existing(Object object) throws Exception {
            if(object instanceof User) {
                return userDao.findByUsername(((User) object).getUsername()).map(User::getId);
            } else {
                throw new Exception("UsernameNotDuplicate Validator Error.");
            }
        }
    }
}
