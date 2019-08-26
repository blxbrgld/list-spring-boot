package gr.blxbrgld.list.validators;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Arrays;
import java.util.List;

import static java.lang.annotation.ElementType.*;

/**
 * FileValid
 * @author blxbrgld
 */
@Target({ANNOTATION_TYPE, METHOD, FIELD, PARAMETER, CONSTRUCTOR})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FileValid.FileValidator.class)
@Documented
public @interface FileValid {

    String message() default "Only .jpg, .jpeg, .png Files are Allowed, with Size < 2Mb";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    String fileSize() default "2000000"; //2 MB
    String[] supportedFileTypes() default { "jpg", "jpeg", "png" };

    /**
     * Implementing a Constraint Validator For The Constraint FileValid
     * @author blxbrgld
     */
    class FileValidator implements ConstraintValidator<FileValid, CommonsMultipartFile> {

        private String fileSize;
        private String[] supportedFileTypes;

        /**
         * Access To The Attribute Values That Should Be Validated
         * @param constraintAnnotation FileValid
         */
        @Override
        public void initialize(FileValid constraintAnnotation) {
            fileSize = constraintAnnotation.fileSize();
            supportedFileTypes = constraintAnnotation.supportedFileTypes();
        }

        /**
         * Validation Logic
         * @param value CommonsMultipartFile
         * @param context ConstraintValidatorContext
         */
        @Override
        public boolean isValid(CommonsMultipartFile value, ConstraintValidatorContext context) {
            if(value == null || value.isEmpty()) { // Not Required
                return true;
            } else {
                return validateFileExtension(value) && validateFileSize(value);
            }
        }

        /**
         * Validate File Extension If A File Is Given
         * @param value CommonsMultipartFile
         * @return TRUE or FALSE
         */
        private boolean validateFileExtension(CommonsMultipartFile value) {
            int dotPosition = value.getOriginalFilename().lastIndexOf('.');
            boolean result = false;
            if(dotPosition != -1) {
                String extension = value.getOriginalFilename().substring(dotPosition + 1);
                final List<String> supportedExtensions = Arrays.asList(supportedFileTypes);
                for(String supportedExtension : supportedExtensions) {
                    if(extension.equalsIgnoreCase(supportedExtension)) {
                        result = true;
                        break;
                    }
                }
            }
            return result;
        }

        /**
         * Validate File Size If A File Is Given
         * @param value CommonsMultipartFile
         * @return TRUE or FALSE
         */
        private boolean validateFileSize(CommonsMultipartFile value) {
            boolean result = false;
            if(!value.isEmpty() && value.getSize() != 0 && value.getSize() <= Long.valueOf(fileSize)) {
                result = true;
            }
            return result;
        }
    }
}
