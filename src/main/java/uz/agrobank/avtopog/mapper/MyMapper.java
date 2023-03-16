package uz.agrobank.avtopog.mapper;

import org.mapstruct.Mapper;
import uz.agrobank.avtopog.dto.UserDto;
import uz.agrobank.avtopog.model.LdSvGate;
import uz.agrobank.avtopog.model.LdSvGateAdd;
import uz.agrobank.avtopog.model.User;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MyMapper {
    UserDto fromUser(User user);
    List<LdSvGateAdd> fromLdSvList(List<LdSvGate> ldSvGateList);
}
