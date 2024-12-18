package org.aome.cvapi.security;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.aome.cvapi.util.responses.ExceptionResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final ObjectMapper objectMapper;

    @Value("${security.username}")
    private String USERNAME;

    @Value("${security.password}")
    private String PASSWORD;

    private String token;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            String authHeader = request.getHeader("Authorization");

            if (headerIsValid(authHeader)) {
                token = authHeader.substring(7);
                checkJWTAndSetAuthentication();
            }

        } catch (JWTVerificationException e) {
            response.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().write(objectMapper.writeValueAsString(new ExceptionResponse("Jwt is incorrect", LocalDateTime.now())));
        }
        if (response.getStatus() != HttpServletResponse.SC_BAD_REQUEST)
            filterChain.doFilter(request, response);
    }

    private void checkJWTAndSetAuthentication() {
        String authData = validateTokenAndRetrieveUsername(token);
        String[] data = authData.split(":");
        String username = data[0];
        String password = data[1];
        if(username.equals(USERNAME) && password.equals(PASSWORD)) {
            UserDetails userDetails = new User(username, password, Collections.emptyList());
            setSecurityContextIfItEmpty(userDetails);
        }else{
            throw new JWTVerificationException("Invalid JWT");
        }
    }
    private void setSecurityContextIfItEmpty(UserDetails userDetails) {
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    userDetails, userDetails.getPassword(), userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }
    }

    private boolean headerIsValid(String authHeader) {
        return authHeader != null && !authHeader.isBlank() && authHeader.startsWith("Bearer ");
    }

    private String validateTokenAndRetrieveUsername(String token) {
        if (token.isBlank()) {
            throw new JWTVerificationException("Token is empty");
        }
        return jwtUtil.validateTokenAndRetrievedSubject(token);
    }


}
