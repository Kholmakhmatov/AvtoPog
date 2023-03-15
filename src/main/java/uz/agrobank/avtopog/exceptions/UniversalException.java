package uz.agrobank.avtopog.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class UniversalException  extends RuntimeException {
    private String message;
    private HttpStatus status;

    public UniversalException(String message, HttpStatus httpStatus) {
        this.message = message;
        this.status = httpStatus;
    }

}
