package uz.agrobank.avtopog.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
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
@Entity
@Table(name = "USERS")
public class User implements UserDetails {
    @Id
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

    public User(Long id, String password, String firstName, String username, String phone, Boolean active, RoleEnum role) {
        this.id = id;
        this.password = password;
        this.firstName = firstName;
        this.username = username;
        this.phone = phone;
        this.active = active;
        this.role = role;
        createdAt=LocalDateTime.now();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority>grantedAuthorities=new HashSet<>();
            grantedAuthorities.add(new SimpleGrantedAuthority(role.name()));

        return grantedAuthorities;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return active;
    }

    @Override
    public boolean isAccountNonLocked() {
        return active;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return active;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }
}
