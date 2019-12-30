package gr.blxbrgld.list.validators;

import gr.blxbrgld.list.dao.hibernate.ItemDao;
import gr.blxbrgld.list.dao.hibernate.SubtitlesDao;
import gr.blxbrgld.list.model.Subtitles;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;
import java.util.Optional;

/**
 * SubtitlesDeletionAllowed Annotation
 * @author blxbrgld
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {SubtitlesDeletionAllowed.SubtitlesDeletionAllowedValidator.class})
@Documented
public @interface SubtitlesDeletionAllowed {

    String message() default "Subtitles does not exist or is related with an Item.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    /**
     * SubtitlesDeletionAllowed Validator To Ensure That The Given Subtitles Id Exist And Is Not Related With An Item
     * @author blxbrgld
     */
    class SubtitlesDeletionAllowedValidator implements ConstraintValidator<SubtitlesDeletionAllowed, Integer> {

        @Autowired
        private SubtitlesDao subtitlesDao;

        @Autowired
        private ItemDao itemDao;

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean isValid(Integer id, ConstraintValidatorContext context) {
            Optional<Subtitles> subtitles = subtitlesDao.get(id);
            return subtitles.isPresent() && !itemDao.havingSubtitlesExists(subtitles.get());
        }
    }
}
