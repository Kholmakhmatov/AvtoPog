package uz.agrobank.avtopog.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.agrobank.avtopog.model.Branch;
import uz.agrobank.avtopog.repository.BranchRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class BranchService {
    private final BranchRepository branchRepository;
    public List<Branch> getBranches() {
        return branchRepository.findBranchList();
    }
    public List<Branch>getRegion(){
        List<Branch> branches = getBranches();
        List<Branch> response = new ArrayList<>();
        Set<String>regIds=new HashSet<>();
        for (Branch branch : branches) {
            boolean contains = regIds.contains(branch.getRegId());
            if(!contains){
                regIds.add(branch.getRegId());
                response.add(branch);
            }
        }
        return response;
    }
}
