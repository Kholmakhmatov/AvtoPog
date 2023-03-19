package uz.agrobank.avtopog.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import uz.agrobank.avtopog.model.enums.RoleEnum;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@NoArgsConstructor
public class UserDroCreate {

    private String password = " ";

    private String firstName;

    private String username = " ";
    private Boolean active = true;

    @Enumerated(value = EnumType.STRING)
    private RoleEnum role = RoleEnum.USER;


}
