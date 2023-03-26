package uz.agrobank.avtopog.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.agrobank.avtopog.baseUtil.MyBaseUtil;
import uz.agrobank.avtopog.dto.LdSvGateAddCreate;
import uz.agrobank.avtopog.dto.UserDto;
import uz.agrobank.avtopog.model.Branch;
import uz.agrobank.avtopog.response.ResponseDto;
import uz.agrobank.avtopog.service.CardService;
import uz.agrobank.avtopog.service.UserService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/avtoPog")
public class AvtoPogController {
    private final MyBaseUtil myBaseUtil;
    private final CardService cardService;
    @GetMapping(path = "/start")
    public String homePage(Model model) {
            UserDto userDto = myBaseUtil.userDto();
            model.addAttribute("user", userDto);
        List<Branch> branchList = cardService.getBranches();
        model.addAttribute("branches", branchList);
        model.addAttribute("addCard", new LdSvGateAddCreate());
        model.addAttribute("message", new ResponseDto<String>());

            return "avtoPog";

    }
}
