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
import uz.agrobank.avtopog.model.enums.RoleEnum;
import uz.agrobank.avtopog.repository.UserRepository;
import uz.agrobank.avtopog.response.ContentList;
import uz.agrobank.avtopog.response.ResponseDto;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;
import java.util.TreeSet;

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

    public ResponseDto<String> hasUserForLogin(User userCreate) {
        ResponseDto<String> responseDto = new ResponseDto<>();
        Optional<User> userByUsername = userRepository.getUserByUsername(userCreate.getUsername());
        if (userByUsername.isPresent()) {
            User user = userByUsername.get();
            if (user.getActive()) {
                boolean matches = encoder.passwordEncoder().matches(userCreate.getPassword(), user.getPassword());
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
        Optional<User> byUsername = userRepository.findByUsername(user.getUsername());
        if (byUsername.isEmpty()) {
            Long maxId = userRepository.findMaxId();
            user.setId(++maxId);
            User save = userRepository.save(user);
            responseDto.setMessage("Add new user");
            responseDto.setObj(save);
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
            /// Agar adminga o'zgartirmoqchi bo'lsa boshqa active admin borligiga tekshirish kerak
            boolean hasAnotherAdmin=hasAnotherAdmin(user,userUpdate);
            if (!hasAnotherAdmin){
                responseDto.setMessage("Siz Role ni almashtira olmaysiz szdan boshqa ADMIN yo'q");
                userUpdate.setRole(RoleEnum.ADMIN);
                responseDto.setObj(userUpdate);
                return responseDto;
            }
            /// Agar Activligini almashtirmoqchi bo'lsa
             hasAnotherAdmin=hasAnotherAdmin(user,userUpdate.getActive());
            if (!hasAnotherAdmin){
                responseDto.setMessage("Active holatizni o'zgartira olmaysiz sababi sizdan boshqa admin yo'q");
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
            if (userSave.getId().equals(userDto.getId()) ) {
                jwtService.createTokenAndSaveCookies(save, response);
            }
        } else {
            throw new UniversalException("User not found", HttpStatus.NOT_FOUND);
        }
        return responseDto;
    }

    private boolean hasAnotherAdmin(User userLast, UserUpdate userNow) {
        if ( userLast.getRole().equals(RoleEnum.ADMIN) && userNow.getRole().equals(RoleEnum.USER)){
           return userRepository.hasAnotherAdmin(userNow.getId());
        }
        return true;
    }
    private boolean hasAnotherAdmin(User userLast, Boolean userNow) {
        if (userLast.getRole().equals(RoleEnum.ADMIN) && !userNow){
            return userRepository.hasAnotherAdmin(userLast.getId());
        }
        return true;
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
        if (userUpdate.getPhone() != null && !userUpdate.getPhone().isEmpty()) {
            user.setPhone(userUpdate.getPhone());
        }
        user.setCreatedAt(user.getCreatedAt());
        user.setActive(userUpdate.getActive());
        user.setRole(userUpdate.getRole());

        return user;
    }

    public TreeSet<Integer> generateCount(Integer count, Integer page) {
        TreeSet<Integer> integerList = new TreeSet<>();
        int counter = 4;
        if (count < 15) {
            for (Integer i = 1; i <= count; i++) {
                integerList.add(i);
            }
            return integerList;
        } else {
            for (int i = 1; i < 4; i++) {
                integerList.add(i);
            }
            for (int i = page; i > 0 && counter > 0; i--) {
                integerList.add(i);
                counter--;
            }
            for (int i = page; i < count && counter < 5; i++) {
                integerList.add(i);
                counter++;
            }
            for (Integer i = count - 4; i <= count; i++) {
                integerList.add(i);
            }
            integerList.add(page);
            integerList.add((int) (count * 0.20));
            integerList.add((int) (count * 0.40));
            integerList.add((int) (count * 0.60));
            integerList.add((int) (count * 0.80));
            return integerList;
        }
    }
}
