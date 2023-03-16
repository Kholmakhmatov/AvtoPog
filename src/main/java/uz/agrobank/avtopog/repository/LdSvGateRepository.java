package uz.agrobank.avtopog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.agrobank.avtopog.model.LdSvGate;

import java.util.List;

public interface LdSvGateRepository extends JpaRepository<LdSvGate,Long> {
  List<LdSvGate>findAllByIdAndBranch(Long id,String branch);
}
