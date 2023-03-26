package uz.agrobank.avtopog.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.agrobank.avtopog.baseUtil.MyBaseUtil;
import uz.agrobank.avtopog.config.activeMq.QueueManager;
import uz.agrobank.avtopog.dto.AvtoPogDto;
import uz.agrobank.avtopog.dto.UserDto;
import uz.agrobank.avtopog.model.Branch;
import uz.agrobank.avtopog.response.ResponseDto;
import uz.agrobank.avtopog.service.BranchService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/avtoPog")
public class AvtoPogController {
    private final MyBaseUtil myBaseUtil;
    private final BranchService branchService;
    private final QueueManager queueManager;
    @Value("${jms.queues.uzcard-queue.name}")
    public String uzcardQueue;

    @Value("${jms.queues.humo-queue.name}")
    public String humoQueue;

    @GetMapping(path = "/start")
    public String startPage(Model model) {
        UserDto userDto = myBaseUtil.userDto();
        model.addAttribute("user", userDto);
        List<Branch> branchList = branchService.getBranches();
        List<Branch> regions = branchService.getRegion();
        model.addAttribute("branches", branchList);
        model.addAttribute("regions", regions);
        model.addAttribute("dto", new AvtoPogDto());
        int messageCountUzcard = queueManager.getMessageCount(uzcardQueue);
        model.addAttribute("uzcardQueue", messageCountUzcard);
        ResponseDto<String> responseMessage = new ResponseDto<>();
        int messageCountHumo = queueManager.getMessageCount(humoQueue);
        if (messageCountUzcard != 0 || messageCountHumo != 0) {
            responseMessage.setMessage("Uzcard = " + messageCountUzcard + " Humo=" + messageCountHumo);
        }
        model.addAttribute("humoQueue", messageCountHumo);
        model.addAttribute("message", responseMessage);

        return "avtoPog";

    }

    @PostMapping(path = "/humo/branch")
    public String humodBranch(Model model, @ModelAttribute(name = "dto") AvtoPogDto avtoPogDto) {
        return "redirect:/avtoPog/start";
    }

    @PostMapping(path = "/humo/region")
    public String humodRegion(Model model, @ModelAttribute(name = "dto") AvtoPogDto avtoPogDto) {
        return "redirect:/avtoPog/start";
    }

    @PostMapping(path = "/humo/all")
    public String humodAll(Model model, @ModelAttribute(name = "dto") AvtoPogDto avtoPogDto) {
        return "redirect:/avtoPog/start";
    }

    @PostMapping(path = "/uzcard/branch")
    public String uzcarddBranch(Model model, @ModelAttribute(name = "dto") AvtoPogDto avtoPogDto) {
        return "redirect:/avtoPog/start";
    }

    @PostMapping(path = "/uzcard/region")
    public String uzcarddRegion(Model model, @ModelAttribute(name = "dto") AvtoPogDto avtoPogDto) {
        return "redirect:/avtoPog/start";
    }

    @PostMapping(path = "/uzcard/all")
    public String uzcarddAll(Model model, @ModelAttribute(name = "dto") AvtoPogDto avtoPogDto) {
        return "redirect:/avtoPog/start";
    }
}
