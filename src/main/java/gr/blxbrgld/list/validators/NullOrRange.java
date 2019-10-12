package gr.blxbrgld.list.validators;

import org.hibernate.validator.constraints.CompositionType;
import org.hibernate.validator.constraints.ConstraintComposition;
import org.hibernate.validator.constraints.Range;

import javax.validation.Constraint;
import javax.validation.OverridesAttribute;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.Null;
import java.lang.annotation.*;

/**
 * Constraint composition for attributes that can be either null or numeric in a specified range
 * @author blxbrgld
 */
@ConstraintComposition(CompositionType.OR)
@Null @Range
@ReportAsSingleViolation
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
@Documented
public @interface NullOrRange {

    String message() default "The value must be either null or between {min} and {max}.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    /**
     * Override {@link Range} min attribute
     * @return The min value
     */
    @OverridesAttribute(constraint = Range.class, name = "min")
    long min() default 0L;

    /**
     * Override {@link Range} max attribute
     * @return The max value
     */
    @OverridesAttribute(constraint = Range.class, name = "max")
    long max() default Long.MAX_VALUE;
}
