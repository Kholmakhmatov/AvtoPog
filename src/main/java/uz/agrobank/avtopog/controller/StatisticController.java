package uz.agrobank.avtopog.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.agrobank.avtopog.baseUtil.MyBaseUtil;
import uz.agrobank.avtopog.dto.UserDto;
import uz.agrobank.avtopog.response.ResponseDto;
import uz.agrobank.avtopog.service.StatisticService;
import uz.agrobank.avtopog.service.UserService;

@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/statistic")
public class StatisticController {
    private final UserService userService;
    private final MyBaseUtil myBaseUtil;
    private final StatisticService statisticService;
    @GetMapping(path = "/home")
    public String homePage(Model model, @CookieValue(value = "user", defaultValue = "") String token) {
        ResponseDto<String> responseDto = userService.hasUser(token);
        if (responseDto.getSuccess()) {
            UserDto userDto = myBaseUtil.userDto();
            model.addAttribute("user", userDto);
            String fewDays = statisticService.getFewDays();
            String fewDays2 = statisticService.getFewDays();
            String fewMonth=statisticService.getFewMonth();
            String fewYear=statisticService.getFewYear();
            model.addAttribute("user",userDto);
            model.addAttribute("fewDays", fewDays);
            model.addAttribute("fewDays2", fewDays2);
            model.addAttribute("fewMonth", fewMonth);
            model.addAttribute("fewYear", fewYear);
            return "homeStatistic";
        } else
            return "index";
    }
}
