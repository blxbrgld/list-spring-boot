package gr.blxbrgld.list.validators;

import gr.blxbrgld.list.model.Item;
import gr.blxbrgld.list.service.SubtitlesService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * SubtitlesValid Annotation
 * @author blxbrgld
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {SubtitlesValid.SubtitlesValidator.class})
@Documented
public @interface SubtitlesValid {

    String message() default "The subtitles do not exist.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    /**
     * Validate the existence of the subtitles
     * @author blxbrgld
     */
    class SubtitlesValidator implements ConstraintValidator<SubtitlesValid, Item> {

        @Autowired
        private SubtitlesService subtitlesService;

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean isValid(Item item, ConstraintValidatorContext context) {
            if(item.getSubtitles()!=null && StringUtils.trimToNull(item.getSubtitles().getTitle())!=null) {
                return subtitlesService.getSubtitles(item.getSubtitles().getTitle()).isPresent();
            }
            return true; // No subtitles given, proceed
        }
    }
}
