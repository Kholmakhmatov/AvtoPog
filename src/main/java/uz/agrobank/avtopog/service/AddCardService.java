package uz.agrobank.avtopog.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.agrobank.avtopog.dto.LdSvGateAddCreate;
import uz.agrobank.avtopog.model.Branch;
import uz.agrobank.avtopog.model.LdSvGateAdd;
import uz.agrobank.avtopog.repository.BranchRepository;
import uz.agrobank.avtopog.repository.LdSvGateAddRepository;
import uz.agrobank.avtopog.response.ResponseDto;

import java.util.List;

/**
 * Created by Kholmakhmatov_A on 3/13/2023
 *
 * @Author : Kholmakhmatov_A
 * @Date : 3/13/2023
 * @Project : AvtoPog
 **/
@Service
@RequiredArgsConstructor
public class AddCardService {
    private final BranchRepository branchRepository;
    private final LdSvGateAddRepository ldSvGateAddRepository;
    public List<Branch> getBranches() {
        return branchRepository.getBranchList();
    }

    public Boolean addCard(LdSvGateAddCreate ldSvGateAddCreate, Long userId) {
        try {
            ResponseDto<String>responseDto=new ResponseDto<>();
            LdSvGateAdd ldSvGateAdd=new LdSvGateAdd(ldSvGateAddCreate.getId(), ldSvGateAddCreate.getBranch(), ldSvGateAddCreate.getCardNumber(), ldSvGateAddCreate.getExpiryMonth()+ldSvGateAddCreate.getExpiryYear(), userId);
            ldSvGateAddRepository.save(ldSvGateAdd);
            return true;
        } catch (Exception e){
            return false;
        }


    }
}
