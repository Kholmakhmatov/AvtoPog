package uz.agrobank.avtopog.service;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.agrobank.avtopog.config.MyPasswordEncoder;
import uz.agrobank.avtopog.config.SecretKeys;
import uz.agrobank.avtopog.dto.UserDto;
import uz.agrobank.avtopog.exceptions.UniversalException;
import uz.agrobank.avtopog.mapper.MyMapper;
import uz.agrobank.avtopog.model.User;
import uz.agrobank.avtopog.repository.UserRepository;
import uz.agrobank.avtopog.response.ResponseDto;

import java.util.Date;
import java.util.Optional;

import static uz.agrobank.avtopog.config.SecretKeys.secretWord;


@Service
@RequiredArgsConstructor
public class JwtService {
    private final MyPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final MyMapper myMapper;

    public boolean validationToken(String token) {

        try {
            Jwts.parser().setSigningKey(secretWord).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getUsername(String token) {
        try {

            Claims body = Jwts.parser().setSigningKey(SecretKeys.secretWord).parseClaimsJws(token).getBody();
            return body.getSubject();
        } catch (Exception e) {
            throw new UniversalException("Access_token invalid", HttpStatus.FORBIDDEN);
        }

    }

    public ResponseDto<UserDto> getUsernameByResponse(String token) {
        ResponseDto<UserDto> userResponseDto = new ResponseDto<>();
        try {

            Claims body = Jwts.parser().setSigningKey(SecretKeys.secretWord).parseClaimsJws(token).getBody();
            String subject = body.getSubject();
            Optional<User> byEmailOrUserName = userRepository.getUserByUsernameAndActive(subject,true);
            if (byEmailOrUserName.isPresent()) {
                userResponseDto.setMessage("home");
                userResponseDto.setSuccess(true);
                UserDto userDto = myMapper.fromUser(byEmailOrUserName.get());
                userResponseDto.setObj(userDto);
                return userResponseDto;
            }
        } catch (Exception ignored) {

        }
        userResponseDto.setMessage("index");
        userResponseDto.setSuccess(false);
        return userResponseDto;
    }


    public String createToken(User user) {
        String accessToken = "";
        Claims claims = Jwts.claims().setSubject(user.getUsername());

        accessToken = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + SecretKeys.accessTokenDate))
                .signWith(SignatureAlgorithm.HS512, secretWord).compact();

        claims.put("user", user.getRole());
        claims.put("access_token", accessToken);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + SecretKeys.accessTokenDate))
                .signWith(SignatureAlgorithm.HS512, secretWord).compact();

    }


}