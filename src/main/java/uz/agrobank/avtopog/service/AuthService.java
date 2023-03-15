package uz.agrobank.avtopog.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uz.agrobank.avtopog.exceptions.UniversalException;
import uz.agrobank.avtopog.model.User;
import uz.agrobank.avtopog.repository.UserRepository;


import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> byUserName = userRepository.getUserByUsername(username);
        if (byUserName.isPresent())
            return byUserName.get();
        else throw new UniversalException("Not Found User", HttpStatus.NOT_FOUND);
    }
}
