package uz.agrobank.avtopog.repository.imp;

import uz.agrobank.avtopog.model.User;

import java.util.List;

public interface UserRepositoryImp {
    User findByUserName(String userName);
    User findUserByUsernameAndActive(String userName,Boolean active);
    User findById(Long id);
    User findByUsernameAndIdNot(String username, Long id);
    User findByPhoneAndIdNot(String username, Long id);
    int update(User user);
    Long findMaxId();
    List<User> findAllByUsernameAndOffset(String username, Integer size, Integer offset);
    Long findAllByUsernameCount(String username);
    Integer findAnotherAdmin(Long id);

    int save(User admin);
}
