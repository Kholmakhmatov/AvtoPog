package uz.agrobank.avtopog.annotation;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import uz.agrobank.avtopog.exceptions.UniversalException;
import uz.agrobank.avtopog.model.User;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Aspect
@RequiredArgsConstructor
public class CheckRoleExecutor {

    @Before(value = "@annotation(checkRole)")
    public void checkRole(CheckRole checkRole) {

            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            Set<String> collect = Arrays.stream(checkRole.value()).map(Enum::name).collect(Collectors.toSet());
            boolean exist = false;
            for (String role : collect) {
                if (user.getRole().name().equals(role)) {
                    exist = true;
                    break;
                }
            }
            if (!exist) {
                    throw new UniversalException("Not permission", HttpStatus.FORBIDDEN);
            }
        }

}
