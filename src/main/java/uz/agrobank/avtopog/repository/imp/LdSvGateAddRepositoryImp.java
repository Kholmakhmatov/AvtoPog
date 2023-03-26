package uz.agrobank.avtopog.repository.imp;

import uz.agrobank.avtopog.model.LdSvGateAdd;

public interface LdSvGateAddRepositoryImp {
    int save(LdSvGateAdd ldSvGateAdd);
    int delete(LdSvGateAdd ldSvGateAdd);
    int findSameCard(LdSvGateAdd ldSvGateAdd);
}
