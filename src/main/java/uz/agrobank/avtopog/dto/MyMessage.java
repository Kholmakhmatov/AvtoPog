package uz.agrobank.avtopog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyMessage implements Serializable {

    private String content;
    private String queue;

}
