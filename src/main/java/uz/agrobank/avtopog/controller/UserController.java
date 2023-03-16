package uz.agrobank.avtopog.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uz.agrobank.avtopog.baseUtil.MyBaseUtil;
import uz.agrobank.avtopog.config.SecretKeys;
import uz.agrobank.avtopog.dto.SearchDto;
import uz.agrobank.avtopog.dto.UserDroCreate;
import uz.agrobank.avtopog.dto.UserDto;
import uz.agrobank.avtopog.dto.UserUpdate;
import uz.agrobank.avtopog.exceptions.UniversalException;
import uz.agrobank.avtopog.model.User;
import uz.agrobank.avtopog.model.enums.RoleEnum;
import uz.agrobank.avtopog.response.ContentList;
import uz.agrobank.avtopog.response.ResponseDto;
import uz.agrobank.avtopog.service.JwtService;
import uz.agrobank.avtopog.service.UserService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping(path = "/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final MyBaseUtil myBaseUtil;

    @GetMapping(path = "/profile")
    public String myProfile(Model model) {
        UserDto userDto = myBaseUtil.userDto();
        ResponseDto<UserUpdate> userDtoResponseDto = userService.findById(userDto.getId());
            model.addAttribute("userDto", userDtoResponseDto.getObj());
            model.addAttribute("user", userDto);
            List<String> roles = new ArrayList<>();
            for (RoleEnum value : RoleEnum.values()) {
                roles.add(value.name());
            }
            model.addAttribute("roles", roles);
            model.addAttribute("message", new ResponseDto<String>());
            return "myProfile";


    }

    @GetMapping(path = "/add")
    public String addPage(Model model) {
        model.addAttribute("userDto", new UserDroCreate());
        UserDto userDto = myBaseUtil.userDto();
        model.addAttribute("user", userDto);
        List<String> roles = new ArrayList<>();
        for (RoleEnum value : RoleEnum.values()) {
            roles.add(value.name());
        }
        model.addAttribute("roles", roles);
        model.addAttribute("message", new ResponseDto<String>());

        return "addUser";
    }

    @PostMapping(path = "/add")
    public String addUser(Model model, @ModelAttribute(name = "userDto") UserDroCreate userDroCreate) {
        ResponseDto<String> responseDto = userService.addUser(userDroCreate);
        UserDto userDto = myBaseUtil.userDto();
        model.addAttribute("user", userDto);
        if (responseDto.getSuccess())
            model.addAttribute("userDto", new UserDroCreate());
        else
            model.addAttribute("userDto", userDroCreate);
        List<String> roles = new ArrayList<>();
        for (RoleEnum value : RoleEnum.values()) {
            roles.add(value.name());
        }
        model.addAttribute("roles", roles);
        model.addAttribute("message", responseDto);

        return "addUser";
    }

    @GetMapping(path = "/all")
    public String users(Model model, @RequestParam(name = "name", required = false) String username, @RequestParam(name = "page", defaultValue = SecretKeys.PAGE) Integer page) {

        UserDto userDto = myBaseUtil.userDto();
        model.addAttribute("user", userDto);
        model.addAttribute("search", new SearchDto(username));
        model.addAttribute("message", new ResponseDto<String>());
        ContentList<UserDto> contentList = userService.getUserPage(username, page);
        model.addAttribute("users", contentList.getList());
        model.addAttribute("count", contentList.getCount());
        model.addAttribute("page", contentList.getPage());

        return "usersTable";
    }

    @GetMapping(path = "/edite/{id}")
    public String editeUserPage(Model model, @PathVariable(name = "id") Long id) {
        ResponseDto<UserUpdate> userDtoResponseDto = userService.findById(id);
        if (userDtoResponseDto.getSuccess()) {
            model.addAttribute("userDto", userDtoResponseDto.getObj());
            UserDto userDto = myBaseUtil.userDto();
            model.addAttribute("user", userDto);
            List<String> roles = new ArrayList<>();
            for (RoleEnum value : RoleEnum.values()) {
                roles.add(value.name());
            }
            model.addAttribute("roles", roles);
            model.addAttribute("message", new ResponseDto<String>());
            return "editeUser";
        } else
            throw new UniversalException("User not found", HttpStatus.NOT_FOUND);

    }

    @PostMapping(path = "/edite/{id}")
    public String editeUser(Model model, @ModelAttribute(name = "userDto") UserUpdate userUpdate, @PathVariable(name = "id") Long id, HttpServletResponse response) {
        UserDto userDto = myBaseUtil.userDto();
        ResponseDto<UserUpdate> userDtoResponseDto = userService.updateUser(userUpdate, userDto,response);
        model.addAttribute("userDto", userDtoResponseDto.getObj());
        model.addAttribute("user", userDtoResponseDto.getObj());
        List<String> roles = new ArrayList<>();
        for (RoleEnum value : RoleEnum.values()) {
            roles.add(value.name());
        }
        model.addAttribute("roles", roles);
        model.addAttribute("message", userDtoResponseDto);
        return "editeUser";

    }
    @PostMapping(path = "/editeProfile/{id}")
    public String editeUserProfile(Model model, @ModelAttribute(name = "userDto") UserUpdate userUpdate, @PathVariable(name = "id") Long id, HttpServletResponse response) {
        UserDto userDto = myBaseUtil.userDto();
        ResponseDto<UserUpdate> userDtoResponseDto = userService.updateUser(userUpdate, userDto,response);
        model.addAttribute("userDto", userDtoResponseDto.getObj());
        model.addAttribute("user", userDtoResponseDto.getObj());
        List<String> roles = new ArrayList<>();
        for (RoleEnum value : RoleEnum.values()) {
            roles.add(value.name());
        }
        model.addAttribute("roles", roles);
        model.addAttribute("message", userDtoResponseDto);
        return "myProfile";

    }
}
