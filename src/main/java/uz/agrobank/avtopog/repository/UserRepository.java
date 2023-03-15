package uz.agrobank.avtopog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.agrobank.avtopog.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    @Query(nativeQuery = true,value = "select * from USERS where USERNAME=?1  and ACTIVE=true")
    Optional<User>getUserByUsername(String userName);
}
