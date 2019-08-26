package gr.blxbrgld.list.validators;

import gr.blxbrgld.list.dao.hibernate.ArtistActivityItemDao;
import gr.blxbrgld.list.dao.hibernate.ArtistDao;
import gr.blxbrgld.list.model.Artist;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;
import java.util.Optional;

/**
 * ArtistDeletionAllowed Annotation
 * @author blxbrgld
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {ArtistDeletionAllowed.ArtistDeletionAllowedValidator.class})
@Documented
public @interface ArtistDeletionAllowed {

    String message() default "Artist does not exist or is related with an Item.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    /**
     * ArtistDeletionAllowed Validator To Ensure That The Given Artist Id Exist And Is Not Related With An Item
     * @author blxbrgld
     */
    class ArtistDeletionAllowedValidator implements ConstraintValidator<ArtistDeletionAllowed, Integer> {

        @Autowired
        private ArtistDao artistDao;

        @Autowired
        private ArtistActivityItemDao artistActivityItemDao;

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean isValid(Integer id, ConstraintValidatorContext context) {
            Optional<Artist> artist = artistDao.get(id);
            return artist.isPresent() && !artistActivityItemDao.havingArtistExists(artist.get());
        }
    }
}
