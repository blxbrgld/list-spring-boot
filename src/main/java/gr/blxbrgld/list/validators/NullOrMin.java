package gr.blxbrgld.list.validators;

import org.hibernate.validator.constraints.CompositionType;
import org.hibernate.validator.constraints.ConstraintComposition;

import javax.validation.Constraint;
import javax.validation.OverridesAttribute;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.Min;
import javax.validation.constraints.Null;
import java.lang.annotation.*;

/**
 * Constraint composition for attributes that can be either null or greater than a value
 * @author blxbrgld
 */
@ConstraintComposition(CompositionType.OR)
@Null @Min(0)
@ReportAsSingleViolation
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
@Documented
public @interface NullOrMin {

    String message() default "The value must be either null or greater than {value}.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    /**
     * Override {@link Min} value
     * @return The value
     */
    @OverridesAttribute(constraint = Min.class, name = "value")
    long value() default 0L;
}
