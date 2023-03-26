package uz.agrobank.avtopog.repository.imp;

import uz.agrobank.avtopog.model.Branch;

import java.util.List;

public interface BranchRepositoryImp {
    List<Branch> findBranchList();
    Branch findById(String branch);
}
