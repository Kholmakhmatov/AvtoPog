package uz.agrobank.avtopog.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.agrobank.avtopog.baseUtil.MyBaseUtil;
import uz.agrobank.avtopog.config.SecretKeys;
import uz.agrobank.avtopog.dto.LdSvGateAddCreate;
import uz.agrobank.avtopog.dto.LdSvGateAddSearch;
import uz.agrobank.avtopog.dto.UserDto;
import uz.agrobank.avtopog.model.Branch;
import uz.agrobank.avtopog.model.LdSvGate;
import uz.agrobank.avtopog.model.LdSvGateAdd;
import uz.agrobank.avtopog.response.ContentList;
import uz.agrobank.avtopog.response.ResponseDto;
import uz.agrobank.avtopog.service.CardService;
import uz.agrobank.avtopog.service.JwtService;

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
@RequestMapping(path = "/card")
public class CardController {

    private final CardService cardService;
    private final MyBaseUtil myBaseUtil;
    private final JwtService jwtService;

    @GetMapping(path = "/add")
    public String addCardPage(Model model) {

        List<Branch> branchList = cardService.getBranches();
        model.addAttribute("branches", branchList);
        model.addAttribute("addCard", new LdSvGateAddCreate());
        model.addAttribute("months", List.of("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"));
        model.addAttribute("years", List.of("23", "24", "25", "26", "27", "28", "29", "30", "31", "32"));
        model.addAttribute("message", "");
        UserDto userDto = myBaseUtil.userDto();
        ResponseDto<String> response = new ResponseDto<>();
        response.setMessage("");
        model.addAttribute("message", response);
        model.addAttribute("user", userDto);
        return "addCard";

    }

    @PostMapping(path = "/add")
    public String addCard(Model model, @ModelAttribute(name = "addCard") LdSvGateAddCreate ldSvGateAddCreate) {
        Long userId = myBaseUtil.userDto().getId();
        ResponseDto<LdSvGateAdd> response = cardService.addCard(ldSvGateAddCreate, userId);
        if (response.getSuccess()) {
            LdSvGateAdd obj = response.getObj();
            return "redirect:/card/operation?id=" + obj.getId() + "&branch=" + obj.getBranch();
        } else {
            List<Branch> branchList = cardService.getBranches();
            model.addAttribute("branches", branchList);
            model.addAttribute("addCard", ldSvGateAddCreate);
            model.addAttribute("months", List.of("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"));
            model.addAttribute("years", List.of("23", "24", "25", "26", "27", "28", "29", "30", "31", "32"));
            model.addAttribute("message", response);
            UserDto userDto = myBaseUtil.userDto();
            System.out.println(userDto);
            model.addAttribute("user", userDto);
        }
        return "addCard";

    }

    @PostMapping(path = "/addCardFromFile")
    public void addCardFromFile(MultipartFile[] files, HttpServletResponse response) {
        UserDto userDto = myBaseUtil.userDto();
        cardService.addCardFromFile(files, response, userDto.getId());
    }

    @GetMapping(path = "/operation")
    public String deleteCrad(Model model, @RequestParam(name = "id", required = false) Long id, @RequestParam(name = "branch", required = false) String branch, @RequestParam(name = "cardNumber", required = false) String cardNumber, @RequestParam(name = "page", defaultValue = SecretKeys.PAGE, required = false) Integer page) {
        List<Branch> branchList = cardService.getBranches();
        model.addAttribute("branches", branchList);
        model.addAttribute("message", "");
        UserDto userDto = myBaseUtil.userDto();
        ResponseDto<String> response = new ResponseDto<>();
        response.setMessage("");
        if (page > 0) page--;
        model.addAttribute("message", response);
        model.addAttribute("user", userDto);
        model.addAttribute("searchCard", new LdSvGateAddSearch(id, branch, cardNumber));

        ContentList<LdSvGate> allActive = cardService.getAllActive(id, branch, cardNumber, page);
        model.addAttribute("cards", allActive.getList());
        model.addAttribute("count", allActive.getCount());
        model.addAttribute("page", page + 1);

        return "cardsOperation";
    }

    @GetMapping(path = "/delete/{id}")
    public String deleteCradById(@PathVariable(name = "id") Long cardId, Model model, @RequestParam(name = "id", required = false) Long id, @RequestParam(name = "branch", required = false) String branch, @RequestParam(name = "cardNumber", required = false) String cardNumber, @RequestParam(name = "page", defaultValue = SecretKeys.PAGE, required = false) Integer page) {
        ResponseDto<String> responseDto = cardService.deleteCadById(cardId);
        model.addAttribute("message", responseDto);
        List<Branch> branchList = cardService.getBranches();
        model.addAttribute("branches", branchList);

        UserDto userDto = myBaseUtil.userDto();
        if (page > 0) page--;
        model.addAttribute("user", userDto);
        model.addAttribute("searchCard", new LdSvGateAddSearch(id, branch, cardNumber));

        ContentList<LdSvGate> allActive = cardService.getAllActive(id, branch, cardNumber, page);
        model.addAttribute("cards", allActive.getList());
        model.addAttribute("count", allActive.getCount());
        model.addAttribute("page", page + 1);
        return "cardsOperation";

    }


}
