package uz.agrobank.avtopog.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.agrobank.avtopog.config.SecretKeys;
import uz.agrobank.avtopog.dto.LdSvGateAddCreate;
import uz.agrobank.avtopog.model.Branch;
import uz.agrobank.avtopog.model.LdSvGateAdd;
import uz.agrobank.avtopog.repository.BranchRepository;
import uz.agrobank.avtopog.repository.LdSvGateAddRepository;
import uz.agrobank.avtopog.response.ContentList;
import uz.agrobank.avtopog.response.ResponseDto;

import java.util.List;
import java.util.Optional;

/**
 * Created by Kholmakhmatov_A on 3/13/2023
 *
 * @Author : Kholmakhmatov_A
 * @Date : 3/13/2023
 * @Project : AvtoPog
 **/
@Service
@RequiredArgsConstructor
public class CardService {
    private final BranchRepository branchRepository;
    private final LdSvGateAddRepository ldSvGateAddRepository;
    public List<Branch> getBranches() {
        return branchRepository.getBranchList();
    }

    public ResponseDto<String> addCard(LdSvGateAddCreate ldSvGateAddCreate, Long userId) {
        ResponseDto<String>responseDto=new ResponseDto<>();
        try {
            Optional<LdSvGateAdd> byId = ldSvGateAddRepository.findById(ldSvGateAddCreate.getId());
            if (byId.isPresent()) {
                responseDto.setMessage("Anketa Id avvaldan mavjud");
                return responseDto;
            }
            responseDto.setSuccess(true);
            LdSvGateAdd ldSvGateAdd=new LdSvGateAdd(ldSvGateAddCreate.getId(), ldSvGateAddCreate.getBranch(), ldSvGateAddCreate.getCardNumber(), ldSvGateAddCreate.getExpiryMonth()+ldSvGateAddCreate.getExpiryYear(), userId);
            ldSvGateAddRepository.save(ldSvGateAdd);
            responseDto.setMessage("Add new card");
            return responseDto;
        } catch (Exception e){
            responseDto.setMessage("Serverda nosozlik adminga murojaat qiling");
            return responseDto;
        }


    }

    public ContentList<LdSvGateAdd> getAllActive(Long id, String brach, String cardNumber,Integer page) {
        ContentList<LdSvGateAdd>contentList=new ContentList<>();
        Integer size=Integer.parseInt(SecretKeys.SIZE);
       if (brach==null || brach.isEmpty()) brach=null;
       if (id==null || id==-1) id=null;
       if (cardNumber == null || cardNumber.length() < 14) cardNumber=null;
       Integer offset=size*page;
        List<LdSvGateAdd> allActive = ldSvGateAddRepository.findAllActive(id, brach, cardNumber, size, offset);
        contentList.setPage(page);
        Integer allActiveCount = ldSvGateAddRepository.findAllActiveCount(id, brach, cardNumber);
        contentList.setList(allActive);
        double div=allActiveCount/10.0;
        Integer a= (int) Math.ceil(div);
        contentList.setCount(a);
        return contentList;
    }

    public ResponseDto<String> deleteCadById(Long id) {
        ResponseDto<String>responseDto=new ResponseDto<>();
        Optional<LdSvGateAdd> byId = ldSvGateAddRepository.findById(id);
        if (byId.isPresent()) {
            LdSvGateAdd ldSvGateAdd = byId.get();
            ldSvGateAdd.setState(0);
            ldSvGateAddRepository.save(ldSvGateAdd);
            responseDto.setSuccess(true);
            responseDto.setMessage("Delete card");
            return responseDto;
        }else {
            responseDto.setMessage("Card not found");
            return responseDto;
        }
    }
}
