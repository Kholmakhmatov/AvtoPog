package uz.agrobank.avtopog.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDto<T> {
    private Boolean success = false;
    private String message = "";
    private T obj;

    public ResponseDto(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}
