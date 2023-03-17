package uz.agrobank.avtopog.service.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jdk.jfr.ContentType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;
import uz.agrobank.avtopog.exceptions.UniversalException;
import uz.agrobank.avtopog.service.AuthService;
import uz.agrobank.avtopog.service.JwtService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.UnavailableException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Configuration
@RequiredArgsConstructor
public class AuthenticationJwtTokenFilter extends OncePerRequestFilter {
    private final AuthService authService;
    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(@Nullable HttpServletRequest request, @Nullable HttpServletResponse response, @Nullable FilterChain filterChain) throws ServletException, IOException {
        assert request != null;
        Cookie[] cookies = request.getCookies();
        StringBuffer contextPath = request.getRequestURL();
        if (!(contextPath.indexOf(".js") > 0 || contextPath.indexOf(".css") > 0)) {
            if (cookies != null) {

                List<Cookie> user = Arrays.stream(cookies).filter(cookie -> cookie.getName().equals("user")).toList();
                if (!user.isEmpty() && user.get(0).getName().equals("user")) {
                    String token = user.get(0).getValue();
                    if (jwtService.validationToken(token)) {
                        String username = jwtService.getUsername(token);
                        try {
                            UserDetails userDetails = authService.loadUserByUsername(username);
                            if (userDetails != null && !Objects.isNull(userDetails.getUsername())) {
                                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                            }
                        } catch (Exception e) {
                            for (Cookie cookie : cookies) {
                                if (cookie.getName().equals("user")) {
                                    cookie.setMaxAge(0);
                                    assert response != null;
                                    response.addCookie(cookie);
                                    break;
                                }
                            }
                        }

                    } else {
                        for (Cookie cookie : cookies) {
                            if (cookie.getName().equals("user")) {
                                cookie.setMaxAge(0);
                                assert response != null;
                                response.addCookie(cookie);
                            }
                        }

                    }
                }
            }
        }
        assert filterChain != null;
        filterChain.doFilter(request, response);
    }
}
