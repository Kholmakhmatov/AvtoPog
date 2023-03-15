package uz.agrobank.avtopog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.agrobank.avtopog.model.Branch;
import java.util.List;

public interface BranchRepository extends JpaRepository<Branch,Long> {
    @Query(nativeQuery = true,value = "select * from BRANCH")
    List<Branch> getBranchList();
}
