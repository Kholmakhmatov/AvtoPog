package uz.agrobank.avtopog.service;

import uz.agrobank.avtopog.dto.LdSvGateDto;
import uz.agrobank.avtopog.model.LdSvGate;
import uz.agrobank.avtopog.model.LdSvGateAdd;

import java.util.List;
import java.util.Map;


public interface LdSvGateAddService {

    List<Map<String, Object>> getByIdAndBranch(Long id, String branch);

    LdSvGate getByBranchAndCardNumber(LdSvGateDto ldSvGateDto);

    LdSvGateAdd getByIdAndBranchAdd(LdSvGateDto ldSvGateDto);

    LdSvGateAdd getByBranchAndCardNumberAdd(LdSvGateDto ldSvGateDto);

    LdSvGateAdd addNewCard(LdSvGateDto ldSvGateDto);

    String deleteFromLdSvGate(LdSvGateDto ldSvGateDto);

    String deleteFromLdSvGateAdd(LdSvGateDto ldSvGateDto);

    List<Map<String, Object>> getByAll();


}
