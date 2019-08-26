package gr.blxbrgld.list.validators;

import gr.blxbrgld.list.dao.hibernate.ActivityDao;
import gr.blxbrgld.list.dao.hibernate.ArtistDao;
import gr.blxbrgld.list.dao.hibernate.SubtitlesDao;
import gr.blxbrgld.list.model.Activity;
import gr.blxbrgld.list.model.Artist;
import gr.blxbrgld.list.model.Subtitles;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;
import java.util.Optional;

/**
 * TitleNotDuplicate Annotation
 * @author blxbrgld
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {
    TitleNotDuplicate.ArtistTitleNotDuplicateValidator.class,
    TitleNotDuplicate.ActivityTitleNotDuplicateValidator.class,
    TitleNotDuplicate.SubtitlesTitleNotDuplicateValidator.class
})
@Documented
public @interface TitleNotDuplicate {

    String message() default "The title already exists.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    /**
     * Ensure That The Activity's To Be Updated/Persisted Title Does Not Exist
     * @author blxbrgld
     */
    class ActivityTitleNotDuplicateValidator implements ConstraintValidator<TitleNotDuplicate, Activity> {

        @Autowired
        private ActivityDao activityDao;

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean isValid(Activity activity, ConstraintValidatorContext constraintValidatorContext) {
            Optional<Activity> existing = activityDao.getByTitle(activity.getTitle());
            return !(existing.isPresent() && !existing.get().getId().equals(activity.getId()));
        }
    }

    /**
     * Ensure That The Artist's To Be Updated/Persisted Title Does Not Exist
     * @author blxbrgld
     */
    class ArtistTitleNotDuplicateValidator implements ConstraintValidator<TitleNotDuplicate, Artist> {

        @Autowired
        private ArtistDao artistDao;

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean isValid(Artist artist, ConstraintValidatorContext constraintValidatorContext) {
            Optional<Artist> existing = artistDao.getByTitle(artist.getTitle());
            return !(existing.isPresent() && !existing.get().getId().equals(artist.getId()));
        }
    }

    /**
     * Ensure That The Subtitle's To Be Updated/Persisted Title Does Not Exist
     * @author blxbrgld
     */
    class SubtitlesTitleNotDuplicateValidator implements ConstraintValidator<TitleNotDuplicate, Subtitles> {

        @Autowired
        private SubtitlesDao subtitlesDao;

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean isValid(Subtitles subtitles, ConstraintValidatorContext constraintValidatorContext) {
            Optional<Subtitles> existing = subtitlesDao.getByTitle(subtitles.getTitle());
            return !(existing.isPresent() && !existing.get().getId().equals(subtitles.getId()));
        }
    }
}
