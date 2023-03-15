package uz.agrobank.avtopog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.agrobank.avtopog.model.LdSvGateAdd;
import uz.agrobank.avtopog.model.User;

import java.util.Optional;

public interface LdSvGateAddRepository extends JpaRepository<LdSvGateAdd,Long> {

}
