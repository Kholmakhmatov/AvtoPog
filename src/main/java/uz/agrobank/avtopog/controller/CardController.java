package uz.agrobank.avtopog.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.agrobank.avtopog.annotation.CheckRole;
import uz.agrobank.avtopog.baseUtil.MyBaseUtil;
import uz.agrobank.avtopog.config.SecretKeys;
import uz.agrobank.avtopog.dto.LdSvGateAddCreate;
import uz.agrobank.avtopog.dto.LdSvGateAddSearch;
import uz.agrobank.avtopog.dto.UserDto;
import uz.agrobank.avtopog.model.Branch;
import uz.agrobank.avtopog.model.LdSvGate;
import uz.agrobank.avtopog.model.LdSvGateAdd;
import uz.agrobank.avtopog.model.enums.RoleEnum;
import uz.agrobank.avtopog.response.ContentList;
import uz.agrobank.avtopog.response.ResponseDto;
import uz.agrobank.avtopog.service.CardService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.TreeSet;

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

    @GetMapping(path = "/add")
    public String addCardPage(Model model) {

        UserDto userDto = myBaseUtil.userDto();
        List<Branch> branchList = cardService.getBranches();
        model.addAttribute("branches", branchList);
        model.addAttribute("addCard", new LdSvGateAddCreate());
        model.addAttribute("months", List.of("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"));
        model.addAttribute("years", List.of("23", "24", "25", "26", "27", "28", "29", "30", "31", "32"));
        model.addAttribute("message", "");
        model.addAttribute("message", new ResponseDto<String>());
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
            model.addAttribute("user", userDto);
        }
        return "addCard";

    }

    @PostMapping(path = "/addCardFromFile")
    public String addCardFromFile(MultipartFile[] files, HttpServletResponse response) {
        UserDto userDto = myBaseUtil.userDto();
        cardService.addCardFromFile(files, response, userDto.getId());
        return "navbar";
    }

    @GetMapping(path = "/operation")
    public String deleteCard(Model model, @RequestParam(name = "id", required = false) Long id, @RequestParam(name = "branch", required = false) String branch, @RequestParam(name = "cardNumber", required = false) String cardNumber, @RequestParam(name = "page", defaultValue = SecretKeys.PAGE, required = false) Integer page) {
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
        if (page==0)
            model.addAttribute("firstPage",true);
        else
            model.addAttribute("firstPage",false);
        ContentList<LdSvGate> allActive = cardService.getAllActive(id, branch, cardNumber, page);
        model.addAttribute("cards", allActive.getList());
        TreeSet<Integer> integers = cardService.generateCount(allActive.getCount(), page+1);
        model.addAttribute("count", integers);
        model.addAttribute("page", page + 1);
        if (integers.size()>0 && !(branch==null && cardNumber==null && id==null))
            model.addAttribute("firstPage",true);
        else
            model.addAttribute("firstPage",false);
        return "cardsOperation";
    }

    @GetMapping(path = "/delete/{id}")
    @CheckRole({RoleEnum.ADMIN})
    public String deleteCardById(@PathVariable(name = "id") Long cardId, @RequestParam(name = "branch", required = false) String branch, @RequestParam(name = "page", defaultValue = SecretKeys.PAGE, required = false) Integer page) {
        cardService.deleteCadById(cardId);
        return "redirect:/card/operation?page="+page+"&branch="+branch+"&id="+cardId;

    }
    @GetMapping(path = "/downloadTemplate")
    @CheckRole({RoleEnum.ADMIN,RoleEnum.USER})
    public void downloadTemplate(HttpServletResponse response){
        cardService.downloadTemplate(response);
    }


}
