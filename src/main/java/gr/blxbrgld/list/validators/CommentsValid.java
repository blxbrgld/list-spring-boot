package gr.blxbrgld.list.validators;

import gr.blxbrgld.list.model.CommentItem;
import gr.blxbrgld.list.model.Item;
import gr.blxbrgld.list.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * CommentsValid Annotation
 * @author blxbrgld
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {CommentsValid.CommentsValidator.class})
@Documented
public @interface CommentsValid {

    String message() default "The comments do not exist.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    /**
     * Validate the existence of all comments given
     * @author blxbrgld
     */
    class CommentsValidator implements ConstraintValidator<CommentsValid, Item> {

        @Autowired
        private CommentService commentService;

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean isValid(Item item, ConstraintValidatorContext context) {
            boolean valid = true; // The default result
            if(item.getCommentItems()!=null) {
                for(CommentItem commentItem : item.getCommentItems()) {
                    if(commentItem.getIdComment()==null
                        || StringUtils.trimToNull(commentItem.getIdComment().getTitle())==null
                            || !commentService.getComment(commentItem.getIdComment().getTitle()).isPresent()) {
                        valid = false;
                        break; // Fail fast
                    }
                }
            }
            return valid;
        }
    }
}
