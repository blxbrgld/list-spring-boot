package gr.blxbrgld.list.errors;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Api Error
 * @author npapadopoulos
 */
@Data
public class ApiError {

    private int status;
    private String error;
    private String message;
    private List<String> errors;
    private String timestamp;

    /**
     * Constructor
     * @param status {@link HttpStatus}
     * @param message Error Message
     * @param errors List Of Errors
     */
    public ApiError(HttpStatus status, String message, List<String> errors) {
        this.status = status.value();
        this.error = status.getReasonPhrase();
        this.message = message;
        this.errors = errors;
        this.timestamp = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(new Date());
    }
}
