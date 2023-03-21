package uz.agrobank.avtopog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.agrobank.avtopog.model.User;

import javax.transaction.Transactional;
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
    @Query(nativeQuery = true, value = "SELECT *\n" +
            "FROM USERS\n" +
            "WHERE\n" +
            "    (?1 IS NOT NULL AND UPPER(USERNAME) LIKE upper('%'|| ?1 ||'%')) or\n" +
            "    (?1 is null and USERNAME is not null )\n" +
            "ORDER BY ID\n" +
            "OFFSET ?3 ROWS FETCH NEXT ?2 ROWS ONLY")
    List<User> findAllByUsernameAndOffset(String username, Integer size, Integer offset);
    @Query(nativeQuery = true, value = "SELECT count(ID) " +
            "FROM USERS " +
            "WHERE" +
            "    (?1 IS NOT NULL AND UPPER(USERNAME) LIKE upper('%'|| ?1 ||'%')) or " +
            "    (?1 is null and USERNAME is not null ) ")
    Long findAllByUsernameCount(String username);

    @Query(nativeQuery = true, value = "select * from users where id!=?2 and username=?1")
    Optional<User> findByUsernameAndIdNot(String username, Long id);

    @Query(nativeQuery = true, value = "select * from users where id!=?2 and phone=?1")
    Optional<User> findByPhoneAndIdNot(String username, Long id);
    @Query(nativeQuery = true,value = "select case when exists(SELECT id FROM users WHERE id != ?1 AND role = 'ADMIN' AND active = 1)\n" +
            "    then 1 else 0 end as result from DUAL")
    Integer hasAnotherAdmin(Long id);
}
