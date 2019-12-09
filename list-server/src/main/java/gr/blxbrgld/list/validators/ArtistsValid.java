package gr.blxbrgld.list.validators;

import gr.blxbrgld.list.model.ArtistActivityItem;
import gr.blxbrgld.list.model.Item;
import gr.blxbrgld.list.service.ActivityService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;
import java.util.*;

/**
 * ArtistsValid Annotation
 * @author blxbrgld
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {ArtistsValid.ArtistsValidator.class})
@Documented
public @interface ArtistsValid {

    String message() default "The artists are not valid.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    /**
     * Validate that at least one artist is given, the activities exist, and that there are no duplicate artist/activity pairs
     * @author blxbrgld
     */
    class ArtistsValidator implements ConstraintValidator<ArtistsValid, Item> {

        @Autowired
        private ActivityService activityService;

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean isValid(Item item, ConstraintValidatorContext context) {
            List<ArtistActivityItem> artistActivityItems = item.getArtistActivityItems();
            if(artistActivityItems.isEmpty()) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("At least one artist must exist.").addConstraintViolation();
                return false;
            } else {
                Map<String, List<String>> artistActivityMap = new HashMap<>();
                for(ArtistActivityItem artistActivityItem : artistActivityItems) {
                    String artist = StringUtils.trimToNull(artistActivityItem.getIdArtist().getTitle());
                    String activity = StringUtils.trimToNull(artistActivityItem.getIdActivity().getTitle());
                    if(artist == null || activity == null) {
                        context.disableDefaultConstraintViolation();
                        context.buildConstraintViolationWithTemplate("Artists and activities can not be null.").addConstraintViolation();
                        return false;
                    }
                    if(!activityService.getActivity(activity).isPresent()) {
                        context.disableDefaultConstraintViolation();
                        context.buildConstraintViolationWithTemplate("Some of the activities given do not exist.").addConstraintViolation();
                        return false;
                    }
                    // Build a map of activities and artists having them
                    if(artistActivityMap.containsKey(activity)) {
                        List<String> artistList = artistActivityMap.get(activity);
                        artistList.add(artist);
                        artistActivityMap.put(activity, artistList);
                    } else {
                        artistActivityMap.put(activity, Collections.singletonList(artist));
                    }
                }
                // Loop map values and check for duplicates
                for(List<String> value : artistActivityMap.values()) {
                    if(value.size() != new HashSet<>(value).size()) {
                        context.disableDefaultConstraintViolation();
                        context.buildConstraintViolationWithTemplate("No duplicate artist/activity pairs are allowed.").addConstraintViolation();
                        return false;
                    }
                }
            }
            return true;
        }
    }
}
