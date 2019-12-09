package gr.blxbrgld.list.validators;

import gr.blxbrgld.list.dao.hibernate.CommentDao;
import gr.blxbrgld.list.dao.hibernate.CommentItemDao;
import gr.blxbrgld.list.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;
import java.util.Optional;

/**
 * CommentDeletionAllowed Annotation
 * @author blxbrgld
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {CommentDeletionAllowed.CommentDeletionAllowedValidator.class})
@Documented
public @interface CommentDeletionAllowed {

    String message() default "Comment does not exist or is related with an Item.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    /**
     * CommentDeletionAllowed Validator To Ensure That The Given Comment Id Exist And Is Not Related With An Item
     * @author blxbrgld
     */
    class CommentDeletionAllowedValidator implements ConstraintValidator<CommentDeletionAllowed, Integer> {

        @Autowired
        private CommentDao commentDao;

        @Autowired
        private CommentItemDao commentItemDao;

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean isValid(Integer id, ConstraintValidatorContext context) {
            Optional<Comment> comment = commentDao.get(id);
            return comment.isPresent() && !commentItemDao.havingCommentExists(comment.get());
        }
    }
}
