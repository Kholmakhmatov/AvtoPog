package uz.agrobank.avtopog.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.agrobank.avtopog.config.MyPasswordEncoder;
import uz.agrobank.avtopog.config.SecretKeys;
import uz.agrobank.avtopog.dto.UserDroCreate;
import uz.agrobank.avtopog.dto.UserDto;
import uz.agrobank.avtopog.dto.UserUpdate;
import uz.agrobank.avtopog.exceptions.UniversalException;
import uz.agrobank.avtopog.mapper.MyMapper;
import uz.agrobank.avtopog.model.User;
import uz.agrobank.avtopog.repository.UserRepository;
import uz.agrobank.avtopog.response.ContentList;
import uz.agrobank.avtopog.response.ResponseDto;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
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
        ResponseDto<UserDto> responseDto = new ResponseDto<>();
        Optional<User> userByUsername = userRepository.getUserByUsername(userCreate.getUsername());
        if (userByUsername.isPresent()) {
            User user = userByUsername.get();
            boolean matches = encoder.passwordEncoder().matches(userCreate.getPassword(), user.getPassword());
            if (matches) {
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
        ResponseDto<String> responseDto = new ResponseDto<>();
        User user = parseToUser(userDroCreate);
        // check username
        Optional<User> byUsername = userRepository.findByUsername(user.getUsername());
        if (byUsername.isEmpty()) {
            Long maxId = userRepository.findMaxId();
            user.setId(++maxId);
            userRepository.save(user);
            responseDto.setMessage("Add new user");
            responseDto.setSuccess(true);

        } else {
            responseDto.setMessage("Username already exist");
        }
        return responseDto;
    }

    private User parseToUser(UserDroCreate userDroCreate) {
        userDroCreate.setPassword(userDroCreate.getPassword().replaceAll(" ", ""));
        userDroCreate.setUsername(userDroCreate.getUsername().replaceAll(" ", ""));
        return new User(encoder.passwordEncoder().encode(userDroCreate.getPassword()), userDroCreate.getFirstName(), userDroCreate.getUsername(), userDroCreate.getActive(), userDroCreate.getRole());
    }

    public ContentList<UserDto> getUserPage(String username, Integer page) {
        ContentList<UserDto> contentList = new ContentList<>();
        if (page > 0) page--;
        Integer size = Integer.parseInt(SecretKeys.SIZE);
        Integer offset = size * page;
        List<User> userList = userRepository.findAllByUsernameAndOffset(username, size, offset);
        Long userListCount = userRepository.findAllByUsernameCount(username);
        double div = userListCount / 10.0;
        Integer count = (int) Math.ceil(div);
        contentList.setCount(count);
        List<UserDto> userDtos = myMapper.fromUserList(userList);
        contentList.setList(userDtos);
        contentList.setPage(page);
        return contentList;
    }

    public ResponseDto<UserUpdate> findById(Long id) {
        Optional<User> byId = userRepository.findById(id);
        ResponseDto<UserUpdate> responseDto = new ResponseDto<>();
        if (byId.isPresent()) {
            UserUpdate userDto = myMapper.toUpdate(byId.get());
            userDto.setPassword(null);
            responseDto.setSuccess(true);
            responseDto.setMessage("ok");
            responseDto.setObj(userDto);
        } else {
            responseDto.setMessage("User not found");
        }
        return responseDto;
    }

    public ResponseDto<UserUpdate> updateUser(UserUpdate userUpdate, UserDto userDto, HttpServletResponse response) {
        Optional<User> byId = userRepository.findById(userUpdate.getId());
        ResponseDto<UserUpdate> responseDto = new ResponseDto<>();
        if (byId.isPresent()) {
            User user = byId.get();
            //check username

                Optional<User> byUsernameAndIdNot = userRepository.findByUsernameAndIdNot(userUpdate.getUsername(), userUpdate.getId());
                if (byUsernameAndIdNot.isPresent()) {
                    responseDto.setMessage("This username already exist");
                    responseDto.setObj(userUpdate);
                    return responseDto;
                }

            //check phone
                Optional<User> byPhoneAndIdNot = userRepository.findByPhoneAndIdNot(userUpdate.getPhone(), userUpdate.getId());
                if (byPhoneAndIdNot.isPresent()) {
                    responseDto.setMessage("This phone already exist");
                    responseDto.setObj(userUpdate);
                    return responseDto;
                }

            User userSave = toUser(userUpdate, user);
            User save = userRepository.save(userSave);
            userUpdate = myMapper.toUpdate(save);
            userUpdate.setPassword(null);
            responseDto.setSuccess(true);
            responseDto.setMessage("Edite user info");
            responseDto.setObj(userUpdate);
            // cookies ni almashtirish
           if (userSave.getId().equals(userDto.getId())){
               String token = jwtService.createToken(save);
               Cookie cookie = new Cookie("user", token);
               cookie.setMaxAge(3600);
               response.addCookie(cookie);
           }
        } else {
            throw new UniversalException("User not found", HttpStatus.NOT_FOUND);
        }
        return responseDto;
    }

    public User toUser(UserUpdate userUpdate, User user) {
        if (userUpdate == null) {
            return null;
        }
        user.setFirstName(userUpdate.getFirstName());
        user.setUsername(userUpdate.getUsername());
        user.setPhone(userUpdate.getPhone());
        if (userUpdate.getPassword() != null && !userUpdate.getPassword().isEmpty()) {
            user.setPassword(encoder.passwordEncoder().encode(userUpdate.getPassword()));
        }
        user.setCreatedAt(user.getCreatedAt());
        user.setActive(userUpdate.getActive());
        user.setRole(userUpdate.getRole());

        return user;
    }
}
