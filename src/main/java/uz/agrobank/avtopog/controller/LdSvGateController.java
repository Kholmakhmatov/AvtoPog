//package uz.agrobank.avtopog.controller;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.*;
//import uz.agrobank.avtopog.dto.LdSvGateDto;
//import uz.agrobank.avtopog.model.LdSvGate;
//import uz.agrobank.avtopog.model.LdSvGateAdd;
//import uz.agrobank.avtopog.service.LdSvGateAddServiceImpl;
//
//import java.util.List;
//import java.util.Map;
//
///**
// * Created by Kholmakhmatov_A on 2/20/2023
// *
// * @Author : Kholmakhmatov_A
// * @Date : 2/20/2023
// * @Project : AvtoPog
// **/
//@RequestMapping("/")
//@RestController
//@RequiredArgsConstructor
//@CrossOrigin(origins = "*", maxAge = 3600)
//public class LdSvGateController {
//    private final LdSvGateAddServiceImpl ldSvGateAddService;
//
//    @GetMapping("/ldbranchadd")
//    public LdSvGateAdd getByIdAndBranchAdd(LdSvGateDto ldSvGateDto) {
//        LdSvGateAdd byIdAndBranchAdd = ldSvGateAddService.getByIdAndBranchAdd(ldSvGateDto);
//        return byIdAndBranchAdd;
//    }
//
//    @GetMapping("/ldbranch")
//    public List<Map<String, Object>> getByIdAndBranch(@RequestParam Long id, @RequestParam String branch) {
//        return ldSvGateAddService.getByIdAndBranch(id, branch);
//    }
//
//    @GetMapping("/ldcardadd")
//    public LdSvGateAdd getByBranchAndCardNumberAdd(LdSvGateDto ldSvGateDto) {
//        LdSvGateAdd byBranchAndCardNumberAdd = ldSvGateAddService.getByBranchAndCardNumberAdd(ldSvGateDto);
//        return byBranchAndCardNumberAdd;
//    }
//
//    @GetMapping("/ldcard")
//    public LdSvGate getByBranchAndCardNumber(LdSvGateDto ldSvGateDto) {
//        LdSvGate byBranchAndCardNumber = ldSvGateAddService.getByBranchAndCardNumber(ldSvGateDto);
//        return byBranchAndCardNumber;
//    }
//
//    @GetMapping("/ldcardall")
//    public List<Map<String, Object>> getByAll() {
//        return ldSvGateAddService.getByAll();
//    }
//
//
//    @PostMapping
//    public LdSvGateAdd addCard(LdSvGateDto ldSvGateDto) {
//        LdSvGateAdd ldSvGateAdd = ldSvGateAddService.addNewCard(ldSvGateDto);
//        return ldSvGateAdd;
//    }
//
//
//}
