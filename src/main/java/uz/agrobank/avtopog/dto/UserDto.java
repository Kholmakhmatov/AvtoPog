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
    private String password;
    @Column(name = "FIRSTNAME")
    private String firstName;
    @Column(nullable = false,unique = true)
    private String username;

    @Column(nullable = false,unique = true)
    private String phone;
    @Column(name = "CREATEDAT")
    private LocalDateTime createdAt;

   @Column(nullable = false)
    private Boolean active;
    @Enumerated(value = EnumType.STRING)
    private RoleEnum role;


}
