package uz.agrobank.avtopog.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@EqualsAndHashCode(callSuper = true)
@Data
public class UniversalException  extends RuntimeException {
    private String message;
    private HttpStatus status;

    public UniversalException(String message, HttpStatus httpStatus) {
        this.message = message;
        this.status = httpStatus;
    }

}
