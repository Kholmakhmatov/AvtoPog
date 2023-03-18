package uz.agrobank.avtopog.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uz.agrobank.avtopog.baseUtil.MyBaseUtil;
import uz.agrobank.avtopog.dto.SendMailDto;
import uz.agrobank.avtopog.dto.UserDto;
import uz.agrobank.avtopog.model.User;
import uz.agrobank.avtopog.response.ResponseDto;
import uz.agrobank.avtopog.service.JwtService;
import uz.agrobank.avtopog.service.UserService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Kholmakhmatov_A on 2/21/2023
 *
 * @Author : Kholmakhmatov_A
 * @Date : 2/21/2023
 * @Project : AvtoPog
 **/
@Controller
@RequiredArgsConstructor
public class BaseController {

    private final UserService userService;
    private final MyBaseUtil myBaseUtil;
    private final JwtService jwtService;

    @GetMapping(path = "/")
    public String homePage(Model model, @CookieValue(value = "user", defaultValue = "") String token) {
        ResponseDto<UserDto> responseDto = userService.hasUser(token);
        if (responseDto.getSuccess()) {
            UserDto userDto = myBaseUtil.userDto();
            model.addAttribute("user", userDto);
            return "navbar";

        } else {
            return "index";
        }
    }

    @GetMapping("/login")
    public String index(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        model.addAttribute("message", "");
        return "loginPage";
    }

    @PostMapping(path = "/login")
    public String login(@ModelAttribute(name = "user") User user, HttpServletResponse response, Model model) {
        ResponseDto<UserDto> responseDto = userService.getUser(user);
        if (responseDto.getSuccess()) {
            String token = jwtService.createToken(user);
            Cookie cookie = new Cookie("user", token);
            cookie.setMaxAge(3600);
            response.addCookie(cookie);
            return "redirect:/";

        } else {
            model.addAttribute("user", user);
            model.addAttribute("message", responseDto.getMessage());
            return "loginPage";
        }
    }

    @GetMapping("/logOut")
    public String deleteCookie(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("user")) {
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
        }
        return "redirect:/";
    }

    @GetMapping("/contact")
    public String contact(Model model) {
        model.addAttribute("sendDto", new SendMailDto());
        model.addAttribute("message", new ResponseDto<String>());
        return "contact";
    }
    @GetMapping("/contact/us")
    public String contactUs(Model model) {
        UserDto userDto = myBaseUtil.userDto();
        model.addAttribute("user", userDto);
        model.addAttribute("sendDto", new SendMailDto());
        model.addAttribute("message", new ResponseDto<String>());
        return "contactUs";
    }
}