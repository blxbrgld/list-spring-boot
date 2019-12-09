package gr.blxbrgld.list.validators;

import gr.blxbrgld.list.model.User;
import gr.blxbrgld.list.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * RoleValid Annotation
 * @author blxbrgld
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {RoleValid.RoleValidator.class})
@Documented
public @interface RoleValid {

    String message() default "Role is not valid.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    /**
     * Validate the role of the user to be created / updated
     * @author blxbrgld
     */
    class RoleValidator implements ConstraintValidator<RoleValid, User> {

        @Autowired
        private RoleService roleService;

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean isValid(User user, ConstraintValidatorContext context) {
            return roleService.getRole(user.getRole().getTitle()).isPresent();
        }
    }
}
