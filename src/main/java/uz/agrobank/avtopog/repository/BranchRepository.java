package uz.agrobank.avtopog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.agrobank.avtopog.model.Branch;

import java.util.List;
import java.util.Optional;

public interface BranchRepository extends JpaRepository<Branch, Long> {
    @Query(nativeQuery = true, value = "select * from BRANCH")
    List<Branch> getBranchList();
    Optional<Branch>findByBranch(String branch);
}
