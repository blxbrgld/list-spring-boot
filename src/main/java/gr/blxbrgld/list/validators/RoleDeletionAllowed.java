package gr.blxbrgld.list.validators;

import gr.blxbrgld.list.dao.hibernate.RoleDao;
import gr.blxbrgld.list.dao.hibernate.UserDao;
import gr.blxbrgld.list.model.Role;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;
import java.util.Optional;

/**
 * RoleDeletionAllowed Annotation
 * @author blxbrgld
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {RoleDeletionAllowed.RoleDeletionAllowedValidator.class})
@Documented
public @interface RoleDeletionAllowed {

    String message() default "Role does not exist or is related with a User.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    /**
     * RoleDeletionAllowed Validator To Ensure That The Given Role Id Exist And Is Not Related With A User
     * @author blxbrgld
     */
    class RoleDeletionAllowedValidator implements ConstraintValidator<RoleDeletionAllowed, Integer> {

        @Autowired
        private RoleDao roleDao;

        @Autowired
        private UserDao userDao;

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean isValid(Integer id, ConstraintValidatorContext context) {
            Optional<Role> role = roleDao.get(id);
            return role.isPresent() && !userDao.havingRoleExists(role.get());
        }
    }
}
