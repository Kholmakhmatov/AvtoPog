package uz.agrobank.avtopog.repository.imp;

import uz.agrobank.avtopog.model.LdSvGate;
import uz.agrobank.avtopog.model.LdSvGateAdd;

import java.util.List;

public interface LdSvGateRepositoryImp {
    List<LdSvGate> findAllActiveUnion(Long id, String branch, String cardNumber, Integer size, Integer offset);
    Integer findAllActiveCountUnion(Long id, String branch, String cardNumber);
    int delete(LdSvGateAdd ldSvGateAdd);
}
