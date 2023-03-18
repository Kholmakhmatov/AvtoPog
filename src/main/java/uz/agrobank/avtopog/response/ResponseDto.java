package uz.agrobank.avtopog.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResponseDto<T> {
    private Boolean success = false;
    private String message = "";
    private T obj;

}
