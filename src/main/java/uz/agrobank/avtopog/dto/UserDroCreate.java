package uz.agrobank.avtopog.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uz.agrobank.avtopog.model.enums.RoleEnum;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

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
