package uz.agrobank.avtopog.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.agrobank.avtopog.baseUtil.MyBaseUtil;
import uz.agrobank.avtopog.config.activeMq.QueueManager;
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
    private final QueueManager queueManager;
    private final StatisticService statisticService;
    @GetMapping(path = "/home")
    public String homePage(Model model, @CookieValue(value = "user", defaultValue = "") String token) {
        ResponseDto<String> responseDto = userService.hasUser(token);
        if (responseDto.getSuccess()) {
            UserDto userDto = myBaseUtil.userDto();
            model.addAttribute("user", userDto);
            String fewDaysHumo = statisticService.getFewDaysHumo();
            String fewDaysUzCard = statisticService.getFewDaysUzcard();
            String fewMonthHumo=statisticService.getFewMonthHumo();
            String fewMonthUzCard=statisticService.getFewMonthUzCard();
            model.addAttribute("user",userDto);
            model.addAttribute("humo", fewDaysHumo);
            model.addAttribute("uzCard", fewDaysUzCard);
            model.addAttribute("fewMonthHumo", fewMonthHumo);
            model.addAttribute("fewMonthUzCard", fewMonthUzCard);
            String list = queueManager.mqList();
            model.addAttribute("MQ", list);
            return "homeStatistic";
        } else
            return "index";
    }
}
