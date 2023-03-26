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
import uz.agrobank.avtopog.repository.UserRepository;
import uz.agrobank.avtopog.mapper.MyMapper;
import uz.agrobank.avtopog.model.User;
import uz.agrobank.avtopog.model.enums.RoleEnum;
import uz.agrobank.avtopog.response.ContentList;
import uz.agrobank.avtopog.response.ResponseDto;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

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
    private final MyPasswordEncoder encoder;
    private final MyMapper myMapper;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public ResponseDto<String> hasUserForLogin(User userCreate) {

        ResponseDto<String> responseDto = new ResponseDto<>();
        User userByUsername = userRepository.findByUserName(userCreate.getUsername());
        if (userByUsername!=null) {
            if (userByUsername.getActive()) {
                boolean matches = encoder.passwordEncoder().matches(userCreate.getPassword(), userByUsername.getPassword());
                if (matches) {
                    responseDto.setSuccess(true);
                    responseDto.setMessage("User find");
                    return responseDto;
                }
            } else {
                responseDto.setMessage("Your account is blocked");
                return responseDto;
            }
        }
        responseDto.setMessage("User not found");
        return responseDto;
    }

    public ResponseDto<String> hasUser(String token) {
        boolean validationToken = jwtService.validationToken(token);
        ResponseDto<String> usernameByResponse = new ResponseDto<>();
        if (validationToken) {
            return jwtService.getUsernameByResponse(token);
        } else {
            usernameByResponse.setSuccess(false);
            usernameByResponse.setMessage("ok");
            return usernameByResponse;
        }
    }

    public ResponseDto<User> addUser(UserDroCreate userDroCreate) {
        ResponseDto<User> responseDto = new ResponseDto<>();
        User user = parseToUser(userDroCreate);
        // check username
        User byUsername = userRepository.findByUserName(user.getUsername());
        if (byUsername==null) {
            Long maxId = userRepository.findMaxId();
            user.setId(++maxId);
            int save = userRepository.save(user);
            if (save==0){
                responseDto.setMessage("Serverda nosozlik adminga murojaat qiling");
                return responseDto;
            }
            responseDto.setMessage("Add new user");
            responseDto.setObj(userRepository.findByUserName(user.getUsername()));
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
        if (username==null || username.isEmpty()) username=null;
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
        User byId = userRepository.findById(id);
        ResponseDto<UserUpdate> responseDto = new ResponseDto<>();
        if (byId!=null) {
            UserUpdate userDto = myMapper.toUpdate(byId);
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
        User user = userRepository.findById(userUpdate.getId());
        ResponseDto<UserUpdate> responseDto = new ResponseDto<>();
        if (user!=null) {
            //check username
            User byUsernameAndIdNot = userRepository.findByUsernameAndIdNot(userUpdate.getUsername(), userUpdate.getId());
            if (byUsernameAndIdNot!=null) {
                responseDto.setMessage("This username already exist");
                responseDto.setObj(userUpdate);
                return responseDto;
            }

            //check phone
            User byPhoneAndIdNot = userRepository.findByPhoneAndIdNot(userUpdate.getPhone(), userUpdate.getId());
            if (byPhoneAndIdNot!=null) {
                responseDto.setMessage("This phone already exist");
                responseDto.setObj(userUpdate);
                return responseDto;
            }
            /// Agar adminga o'zgartirmoqchi bo'lsa boshqa active admin borligiga tekshirish kerak
            Integer hasAnotherAdmin=hasAnotherAdmin(user,userUpdate);
            if (!(hasAnotherAdmin==1)){
                responseDto.setMessage("Siz Role ni almashtira olmaysiz szdan boshqa ADMIN yo'q");
                userUpdate.setRole(RoleEnum.ADMIN);
                responseDto.setObj(userUpdate);
                return responseDto;
            }
            /// Agar Activligini almashtirmoqchi bo'lsa
             hasAnotherAdmin=hasAnotherAdmin(user,userUpdate.getActive());
            if (!(hasAnotherAdmin==1)){
                responseDto.setMessage("Active holatizni o'zgartira olmaysiz sababi sizdan boshqa admin yo'q");
                responseDto.setObj(userUpdate);
                return responseDto;
            }
            User userSave = toUser(userUpdate, user);
            int save = userRepository.update(userSave);
            userSave.setPassword(null);
            responseDto.setSuccess(true);
            responseDto.setMessage("Edite user info");
            responseDto.setObj(userUpdate);
            // cookies ni almashtirish
            if (userSave.getId().equals(userDto.getId()) ) {
                jwtService.createTokenAndSaveCookies(userRepository.findById(userSave.getId()), response);
            }
        } else {
            throw new UniversalException("User not found", HttpStatus.NOT_FOUND);
        }
        return responseDto;
    }

    private Integer hasAnotherAdmin(User userLast, UserUpdate userNow) {
        if ( userLast.getRole().equals(RoleEnum.ADMIN) && userNow.getRole().equals(RoleEnum.USER)){
           return  userRepository.findAnotherAdmin(userNow.getId());
        }
        return 1;
    }
    private Integer hasAnotherAdmin(User userLast, Boolean userNow) {
        if (userLast.getRole().equals(RoleEnum.ADMIN) && !userNow){
            return userRepository.findAnotherAdmin(userLast.getId());
        }
        return 1;
    }
    public User toUser(UserUpdate userUpdate, User user) {
        if (userUpdate == null) {
            return null;
        }
        user.setFirstName(userUpdate.getFirstName());
        user.setUsername(userUpdate.getUsername());
        if (userUpdate.getPassword() != null && !userUpdate.getPassword().isEmpty()) {
            user.setPassword(encoder.passwordEncoder().encode(userUpdate.getPassword()));
        }
        if (userUpdate.getPhone() != null) {
            user.setPhone(userUpdate.getPhone());
        }
        user.setCreatedAt(user.getCreatedAt());
        user.setActive(userUpdate.getActive());
        user.setRole(userUpdate.getRole());

        return user;
    }
}
