package uz.agrobank.avtopog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.agrobank.avtopog.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query(nativeQuery = true, value = "select * from USERS where USERNAME=?1 ")
    Optional<User> getUserByUsername(String userName);

    @Query(nativeQuery = true, value = "select * from USERS where USERNAME=?1 and active=?2")
    Optional<User> getUserByUsernameAndActive(String userName, Boolean active);

    Optional<User> findByUsername(String userName);


    @Query(nativeQuery = true, value = "select max(id) from users")
    Long findMaxId();

    @Query(nativeQuery = true, value = "select * from users where case when (?1 is not null ) then username ilike concat('%',?1,'%') when ?1 is null then username is not null end  order by id limit ?2 offset ?3")
    List<User> findAllByUsernameAndOffset(String username, Integer size, Integer offset);

    @Query(nativeQuery = true, value = "select count(id)  from users where case when (?1 is not null ) then username ilike concat('%',?1,'%') when ?1 is null then username is not null end")
    Long findAllByUsernameCount(String username);

    @Query(nativeQuery = true, value = "select * from users where id!=?2 and username=?1")
    Optional<User> findByUsernameAndIdNot(String username, Long id);

    @Query(nativeQuery = true, value = "select * from users where id!=?2 and phone=?1")
    Optional<User> findByPhoneAndIdNot(String username, Long id);
}
