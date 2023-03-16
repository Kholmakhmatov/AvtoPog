package uz.agrobank.avtopog.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uz.agrobank.avtopog.baseUtil.MyBaseUtil;
import uz.agrobank.avtopog.dto.UserDroCreate;
import uz.agrobank.avtopog.dto.UserDto;
import uz.agrobank.avtopog.model.User;
import uz.agrobank.avtopog.model.enums.RoleEnum;
import uz.agrobank.avtopog.response.ResponseDto;
import uz.agrobank.avtopog.service.JwtService;
import uz.agrobank.avtopog.service.UserService;

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
    public String myProfile(Model model){
            UserDto userDto = myBaseUtil.userDto();
            model.addAttribute("user",userDto);
            return "myProfile";

    }
    @GetMapping(path = "/add")
    public String addPage(Model model){
        model.addAttribute("userDto",new UserDroCreate());
        UserDto userDto = myBaseUtil.userDto();
        model.addAttribute("user",userDto);
        List<String> roles=new ArrayList<>();
        for (RoleEnum value : RoleEnum.values()) {
            roles.add(value.name());
        }
       model.addAttribute("roles",roles);
        model.addAttribute("message",new ResponseDto<String>());

        return "addUser";
    }
    @PostMapping(path = "/add")
    public String addUser(Model model, @ModelAttribute(name = "userDto") UserDroCreate userDroCreate){
     ResponseDto<String>responseDto=userService.addUser(userDroCreate);
        UserDto userDto = myBaseUtil.userDto();
        model.addAttribute("user",userDto);
        if (responseDto.getSuccess())
            model.addAttribute("userDto",new UserDroCreate());
        else
            model.addAttribute("userDto",userDroCreate);
        List<String> roles=new ArrayList<>();
        for (RoleEnum value : RoleEnum.values()) {
            roles.add(value.name());
        }
        model.addAttribute("roles",roles);
        model.addAttribute("message",responseDto);

        return "addUser";
    }
}
