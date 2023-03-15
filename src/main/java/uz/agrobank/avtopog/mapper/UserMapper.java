package uz.agrobank.avtopog.mapper;

import org.mapstruct.Mapper;
import uz.agrobank.avtopog.dto.UserDto;
import uz.agrobank.avtopog.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto fromUser(User user);
}
