package gr.blxbrgld.list.validators;

import gr.blxbrgld.list.dao.hibernate.ActivityDao;
import gr.blxbrgld.list.dao.hibernate.ArtistDao;
import gr.blxbrgld.list.dao.hibernate.CategoryDao;
import gr.blxbrgld.list.dao.hibernate.SubtitlesDao;
import gr.blxbrgld.list.model.Activity;
import gr.blxbrgld.list.model.Artist;
import gr.blxbrgld.list.model.Category;
import gr.blxbrgld.list.model.Subtitles;
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
 * TitleNotDuplicate Annotation
 * @author blxbrgld
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { TitleNotDuplicate.TitleNotDuplicateValidator.class })
@Documented
public @interface TitleNotDuplicate {

    String message() default "The title already exists.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    /**
     * For an update endpoint validate id and object, for a create endpoint just the object
     * @author blxbrgld
     */
    @Slf4j
    @SupportedValidationTarget(ValidationTarget.PARAMETERS)
    class TitleNotDuplicateValidator implements ConstraintValidator<TitleNotDuplicate, Object[]> {

        @Autowired
        private ActivityDao activityDao;

        @Autowired
        private ArtistDao artistDao;

        @Autowired
        private CategoryDao categoryDao;

        @Autowired
        private SubtitlesDao subtitlesDao;

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
                    log.error("@TitleNotDuplicate cannot be applied to this method.");
                    return false;
                }
            } catch (Exception exception) {
                log.error("@TitleNotDuplicate cannot be applied to this object.");
                return false;
            }
        }

        /**
         * Check if the given object is one of the allowed entities and if it is and it exists return it's id
         * @param object {@link Object}
         * @return The entity's id or empty optional if it does not exist
         * @throws Exception thrown if the given object has not one of the allowed types
         */
        private Optional<Integer> existing(Object object) throws Exception {
            if(object instanceof Activity) {
                return activityDao.getByTitle(((Activity) object).getTitle()).map(Activity::getId);
            } else if(object instanceof Artist) {
                return artistDao.getByTitle(((Artist) object).getTitle()).map(Artist::getId);
            } else if(object instanceof Category) {
                return categoryDao.getByTitle(((Category) object).getTitle()).map(Category::getId);
            } else if(object instanceof Subtitles) {
                return subtitlesDao.getByTitle(((Subtitles) object).getTitle()).map(Subtitles::getId);
            } else {
                throw new Exception("TitleNotDuplicate Validator Error.");
            }
        }
    }
}
