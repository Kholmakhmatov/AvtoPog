package uz.agrobank.avtopog.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uz.agrobank.avtopog.baseUtil.MyBaseUtil;
import uz.agrobank.avtopog.config.SecretKeys;
import uz.agrobank.avtopog.dto.UserDto;

@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/statistic")
public class StatisticController {
    private final MyBaseUtil baseUtil;
    @GetMapping(path = "/operation")
    public String deleteCard(Model model) {
        model.addAttribute("message", "");
        UserDto userDto = baseUtil.userDto();
        model.addAttribute("user", userDto);
        return "statistic";
    }
}
