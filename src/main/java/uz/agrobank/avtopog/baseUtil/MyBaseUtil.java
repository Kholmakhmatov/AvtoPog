package uz.agrobank.avtopog.baseUtil;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uz.agrobank.avtopog.dto.UserDto;
import uz.agrobank.avtopog.mapper.UserMapper;
import uz.agrobank.avtopog.model.User;

@Service
@RequiredArgsConstructor
public class MyBaseUtil {
    private final UserMapper userMapper;

    public UserDto userDto(){
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
       return userMapper.fromUser(principal);
    }


}
