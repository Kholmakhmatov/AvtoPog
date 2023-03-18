package uz.agrobank.avtopog.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.agrobank.avtopog.config.SecretKeys;
import uz.agrobank.avtopog.dto.LdSvGateAddCreate;
import uz.agrobank.avtopog.exceptions.UniversalException;
import uz.agrobank.avtopog.mapper.MyMapper;
import uz.agrobank.avtopog.model.Branch;
import uz.agrobank.avtopog.model.LdSvGate;
import uz.agrobank.avtopog.model.LdSvGateAdd;
import uz.agrobank.avtopog.repository.BranchRepository;
import uz.agrobank.avtopog.repository.LdSvGateAddRepository;
import uz.agrobank.avtopog.repository.LdSvGateRepository;
import uz.agrobank.avtopog.response.ContentList;
import uz.agrobank.avtopog.response.ResponseDto;
import uz.agrobank.avtopog.response.ResponseDtoList;

import java.time.LocalDate;
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
    private final LdSvGateRepository ldSvGateRepository;
    private final MyMapper mapper;

    public List<Branch> getBranches() {
        return branchRepository.getBranchList();
    }

    public ResponseDto<LdSvGateAdd> addCard(LdSvGateAddCreate ldSvGateAddCreate, Long userId) {
        ResponseDto<LdSvGateAdd> responseDto = new ResponseDto<>();
        try {
            Optional<LdSvGateAdd> byId = ldSvGateAddRepository.findById(ldSvGateAddCreate.getId());
            if (byId.isPresent()) {
                responseDto.setMessage("Anketa Id avvaldan mavjud");
                return responseDto;
            }
            responseDto.setSuccess(true);
            LdSvGateAdd ldSvGateAdd = new LdSvGateAdd(ldSvGateAddCreate.getId(), ldSvGateAddCreate.getBranch(), ldSvGateAddCreate.getCardNumber(), ldSvGateAddCreate.getExpiryMonth() + ldSvGateAddCreate.getExpiryYear(), userId);
            LdSvGateAdd save = ldSvGateAddRepository.save(ldSvGateAdd);
            responseDto.setMessage("Add new card");
            responseDto.setObj(save);
            return responseDto;
        } catch (Exception e) {
            responseDto.setMessage("Serverda nosozlik adminga murojaat qiling");
            return responseDto;
        }


    }

    public ContentList<LdSvGate> getAllActive(Long id, String brach, String cardNumber, Integer page) {
        ContentList<LdSvGate> contentList = new ContentList<>();
        Integer size = Integer.parseInt(SecretKeys.SIZE);
        if (brach == null || brach.isEmpty()) brach = null;
        if (id == null || id == -1) id = null;
        if (cardNumber == null || cardNumber.length() < 14) cardNumber = null;
        Integer offset = size * page;
        List<LdSvGate> allActive = ldSvGateRepository.findAllActiveUnion(id, brach, cardNumber, id, brach, cardNumber, size, offset);
        contentList.setPage(page);
        List<Integer> allActiveCountList = ldSvGateRepository.findAllActiveCountUnion(id, brach, cardNumber, id, brach, cardNumber);
        contentList.setList(allActive);
        Integer allActiveCount = 0;
        for (Integer integer : allActiveCountList) {
            allActiveCount += integer;
        }
        double div = allActiveCount / 10.0;
        Integer a = (int) Math.ceil(div);
        contentList.setCount(a);
        return contentList;
    }

    public ResponseDto<String> deleteCadById(Long id) {
        ResponseDto<String> responseDto = new ResponseDto<>();
        Optional<LdSvGateAdd> byId = ldSvGateAddRepository.findById(id);
        if (byId.isPresent()) {
            LdSvGateAdd ldSvGateAdd = byId.get();
            ldSvGateAdd.setState(0);
            ldSvGateAddRepository.save(ldSvGateAdd);
            responseDto.setSuccess(true);
            responseDto.setMessage("Card position is passive");
            return responseDto;
        } else {
            Optional<LdSvGate> byIdGate = ldSvGateRepository.findById(id);
            if (byIdGate.isPresent()) {
                LdSvGate ldSvGate = byIdGate.get();
                ldSvGate.setState(0);
                ldSvGateRepository.save(ldSvGate);
                responseDto.setSuccess(true);
                responseDto.setMessage("Card position is passive");
                return responseDto;
            }
        }
        responseDto.setMessage("Card not found");
        return responseDto;
    }


    public LdSvGateAddCreate getCardById(Long id) {
        Optional<LdSvGate> byId = ldSvGateRepository.findById(id);
        if (byId.isPresent()) {
            LdSvGate ldSvGate = byId.get();
            LdSvGateAddCreate ldSvGateAddCreate = mapper.fromLdSvGateToCreate(ldSvGate);
            ldSvGateAddCreate.setExpiryMonth(ldSvGate.getExpiryDate().substring(0, 2));
            ldSvGateAddCreate.setExpiryYear(ldSvGate.getExpiryDate().substring(2, 4));
            return ldSvGateAddCreate;
        } else {
            Optional<LdSvGateAdd> byId1 = ldSvGateAddRepository.findById(id);
            if (byId1.isPresent()) {
                LdSvGateAdd ldSvAdd = byId1.get();
                LdSvGateAddCreate ldSvGateAddCreate = mapper.fromLdSvAddToCreate(ldSvAdd);
                ldSvGateAddCreate.setExpiryMonth(ldSvAdd.getExpiryDate().substring(0, 2));
                ldSvGateAddCreate.setExpiryYear(ldSvAdd.getExpiryDate().substring(2, 4));
                return ldSvGateAddCreate;
            }
        }
        throw new UniversalException("Card not found", HttpStatus.NOT_FOUND);
    }

}
