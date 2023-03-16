package uz.agrobank.avtopog.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uz.agrobank.avtopog.model.enums.RoleEnum;

import javax.persistence.*;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor

public class UserDto {

    private Long id;

    private String firstName;
    private String username;
    private String phone;

    private LocalDateTime createdAt;


    private Boolean active;
    @Enumerated(value = EnumType.STRING)
    private RoleEnum role;


}
