package uz.agrobank.avtopog.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import uz.agrobank.avtopog.annotation.CheckRole;
import uz.agrobank.avtopog.baseUtil.MyBaseUtil;
import uz.agrobank.avtopog.dto.LdSvGateAddCreate;
import uz.agrobank.avtopog.dto.UserDto;
import uz.agrobank.avtopog.model.Branch;
import uz.agrobank.avtopog.model.User;
import uz.agrobank.avtopog.model.enums.RoleEnum;
import uz.agrobank.avtopog.response.ResponseDto;
import uz.agrobank.avtopog.service.AddCardService;
import uz.agrobank.avtopog.service.JwtService;
import uz.agrobank.avtopog.service.UserService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Kholmakhmatov_A on 2/21/2023
 *
 * @Author : Kholmakhmatov_A
 * @Date : 2/21/2023
 * @Project : AvtoPog
 **/
@Controller
@RequiredArgsConstructor
public class AddCardController {

    private final AddCardService addCardService;
    private final MyBaseUtil myBaseUtil;
    private final JwtService jwtService;
    @GetMapping(path = "/addCard")
    public String addCardPage(Model model, @CookieValue(value = "user",defaultValue = "") String token){
       List<Branch> branchList=addCardService.getBranches();
       model.addAttribute("branches",branchList);
       model.addAttribute("addCard",new LdSvGateAddCreate() );
       model.addAttribute("months",List.of("01","02","03","04","05","06","07","08","09","10","11","12"));
       model.addAttribute("years",List.of("23","24","25","26","27","28","29","30","31","32"));
        model.addAttribute("message","");
        UserDto userDto = myBaseUtil.userDto();
        model.addAttribute("user",userDto);
        return "addCard";

    }
@PostMapping(path = "/addCard")
    public String addCard(Model model,@ModelAttribute(name = "addCard") LdSvGateAddCreate ldSvGateAddCreate){
    Long userId = myBaseUtil.userDto().getId();
    ResponseDto<String> response=addCardService.addCard(ldSvGateAddCreate,userId);
    if (response.getSuccess()){
        List<Branch> branchList=addCardService.getBranches();
        model.addAttribute("branches",branchList);
        model.addAttribute("addCard",new LdSvGateAddCreate() );
        model.addAttribute("months",List.of("01","02","03","04","05","06","07","08","09","10","11","12"));
        model.addAttribute("years",List.of("23","24","25","26","27","28","29","30","31","32"));
        model.addAttribute("message",response);
        UserDto userDto = myBaseUtil.userDto();
        model.addAttribute("user",userDto);
    }else {
        List<Branch> branchList=addCardService.getBranches();
        model.addAttribute("branches",branchList);
        model.addAttribute("addCard",ldSvGateAddCreate );
        model.addAttribute("months",List.of("01","02","03","04","05","06","07","08","09","10","11","12"));
        model.addAttribute("years",List.of("23","24","25","26","27","28","29","30","31","32"));
        model.addAttribute("message",response);
        UserDto userDto = myBaseUtil.userDto();
        model.addAttribute("user",userDto);
    }
    return "addCard";

}


}
