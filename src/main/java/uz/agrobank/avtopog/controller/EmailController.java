package uz.agrobank.avtopog.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import uz.agrobank.avtopog.dto.SendMailDto;
import uz.agrobank.avtopog.response.ResponseDto;
import uz.agrobank.avtopog.service.EmailSenderService;

@Controller
@RequiredArgsConstructor
public class EmailController {
    private final EmailSenderService emailSenderService;
    @PostMapping(path = "/sendMail")
    public String sendMail(@ModelAttribute(name = "sendDto") SendMailDto sendMailDto, Model model){
        ResponseDto<String> responseDto = emailSenderService.sendSimpleEmail(sendMailDto);
        model.addAttribute("message",responseDto);
        if (responseDto.getSuccess())
            model.addAttribute("sendDto", new SendMailDto());
        else
            model.addAttribute("sendDto",sendMailDto);

        return "contact";
    }
}
