package uz.agrobank.avtopog.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import uz.agrobank.avtopog.model.enums.RoleEnum;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor

public class UserUpdate {

    private Long id;

    private String firstName;
    private String username;
    private String phone;
    private String password;

    private LocalDateTime createdAt;


    private Boolean active;
    @Enumerated(value = EnumType.STRING)
    private RoleEnum role;


}
