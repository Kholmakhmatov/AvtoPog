package uz.agrobank.avtopog.service;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.agrobank.avtopog.config.SecretKeys;
import uz.agrobank.avtopog.exceptions.UniversalException;
import uz.agrobank.avtopog.repository.UserRepository;
import uz.agrobank.avtopog.model.User;
import uz.agrobank.avtopog.response.ResponseDto;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

import static uz.agrobank.avtopog.config.SecretKeys.secretWord;


@Service
@RequiredArgsConstructor
public class JwtService {
    private final UserRepository userRepository;

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

    public ResponseDto<String> getUsernameByResponse(String token) {
        ResponseDto<String> userResponseDto = new ResponseDto<>();
        try {
            Claims body = Jwts.parser().setSigningKey(SecretKeys.secretWord).parseClaimsJws(token).getBody();
            String subject = body.getSubject();
            User byEmailOrUserName = userRepository.findUserByUsernameAndActive(subject, true);
            if (byEmailOrUserName!=null) {
                userResponseDto.setMessage("Find User");
                userResponseDto.setSuccess(true);
                return userResponseDto;
            }
        } catch (Exception ignored) {

        }
        userResponseDto.setMessage("User not found");
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

    public void createTokenAndSaveCookies(User user, HttpServletResponse response) {
        String token = createToken(user);
        Cookie cookie = new Cookie("user", token);
        cookie.setMaxAge(3600);
        response.addCookie(cookie);
    }

    public void removeCookies(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("user")) {
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                }
            }
        }
    }
}