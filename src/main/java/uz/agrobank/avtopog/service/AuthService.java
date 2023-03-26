package uz.agrobank.avtopog.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import uz.agrobank.avtopog.exceptions.UniversalException;
import uz.agrobank.avtopog.repository.UserRepository;
import uz.agrobank.avtopog.model.User;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        User userByUsernameAndActive = userRepository.findUserByUsernameAndActive(username, true);

        if (userByUsernameAndActive!=null)
            return userByUsernameAndActive;
        else throw new UniversalException("Not Found User", HttpStatus.FORBIDDEN);
    }
}
