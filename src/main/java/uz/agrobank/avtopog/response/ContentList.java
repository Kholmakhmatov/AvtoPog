package uz.agrobank.avtopog.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContentList<T>{

    private Integer count=0;
    private Integer page=0;
    private List<T> list;
}
