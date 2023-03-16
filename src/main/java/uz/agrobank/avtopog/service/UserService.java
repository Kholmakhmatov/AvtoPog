package uz.agrobank.avtopog.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.agrobank.avtopog.config.MyPasswordEncoder;
import uz.agrobank.avtopog.dto.UserDroCreate;
import uz.agrobank.avtopog.dto.UserDto;
import uz.agrobank.avtopog.mapper.MyMapper;
import uz.agrobank.avtopog.model.User;
import uz.agrobank.avtopog.repository.UserRepository;
import uz.agrobank.avtopog.response.ResponseDto;

import java.util.Optional;

/**
 * Created by Kholmakhmatov_A on 2/21/2023
 *
 * @Author : Kholmakhmatov_A
 * @Date : 2/21/2023
 * @Project : AvtoPog
 **/
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final MyPasswordEncoder encoder;
    private final MyMapper myMapper;
    private final JwtService jwtService;

    public ResponseDto<UserDto> getUser(User userCreate) {
        ResponseDto<UserDto>responseDto=new ResponseDto<>();
        Optional<User> userByUsername = userRepository.getUserByUsername(userCreate.getUsername());
        if (userByUsername.isPresent()) {
            User user = userByUsername.get();
            boolean matches = encoder.passwordEncoder().matches(userCreate.getPassword(), user.getPassword());
            if (matches){
                responseDto.setSuccess(true);
                UserDto userDto = myMapper.fromUser(user);
                responseDto.setObj(userDto);
                return responseDto;
            }
        }
        responseDto.setMessage("User not found");
        return responseDto;
    }
    public ResponseDto<UserDto> hasUser(String token) {
        boolean validationToken = jwtService.validationToken(token);
        ResponseDto<UserDto> usernameByResponse = new ResponseDto<>();
        if (validationToken) {
            return jwtService.getUsernameByResponse(token);
        } else {
            usernameByResponse.setSuccess(false);
            usernameByResponse.setMessage("ok");
            return usernameByResponse;
        }
    }

    public ResponseDto<String> addUser(UserDroCreate userDroCreate) {
        ResponseDto<String>responseDto=new ResponseDto<>();
        User user=parseToUser(userDroCreate);
        // check username
        Optional<User> byUsername = userRepository.findByUsername(user.getUsername());
        if (byUsername.isEmpty()) {
                Long maxId = userRepository.findMaxId();
                user.setId(++maxId);
                userRepository.save(user);
                responseDto.setMessage("addUser");
                responseDto.setSuccess(true);

        }else {
            responseDto.setMessage("Username already exist");
        }
        return responseDto;
    }

    private User parseToUser(UserDroCreate userDroCreate) {
        userDroCreate.setPassword(userDroCreate.getPassword().replaceAll(" ",""));
        userDroCreate.setUsername(userDroCreate.getUsername().replaceAll(" ",""));
        return new User(encoder.passwordEncoder().encode(userDroCreate.getPassword()), userDroCreate.getFirstName(), userDroCreate.getUsername(), userDroCreate.getActive(), userDroCreate.getRole());
    }
}
