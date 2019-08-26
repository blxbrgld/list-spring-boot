package gr.blxbrgld.list.errors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

/**
 * Exception Handler To Return HttpStatus.BAD_REQUEST On {@link ConstraintViolationException} [ https://www.baeldung.com/global-error-handler-in-a-spring-rest-api ]
 * @author blxbrgld
 */
@ControllerAdvice
public class ConstraintViolationExceptionHandler {

    /**
     * Handle The ConstraintViolationException
     * @param exception {@link ConstraintViolationException}
     * @return {@link ResponseEntity}
     */
    @ExceptionHandler({ ConstraintViolationException.class })
    public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException exception) {
        List<String> violations = new ArrayList<>();
        for (ConstraintViolation<?> violation : exception.getConstraintViolations()) {
            violations.add(violation.getRootBeanClass().getName() + " " + violation.getPropertyPath() + ": " + violation.getMessage());
        }
        ApiError error = new ApiError(HttpStatus.BAD_REQUEST, exception.getLocalizedMessage(), violations);
        return new ResponseEntity<>(error, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
}
