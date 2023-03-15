package uz.agrobank.avtopog.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.agrobank.avtopog.model.enums.RoleEnum;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role {

    private Integer id;
    private RoleEnum name;

}
