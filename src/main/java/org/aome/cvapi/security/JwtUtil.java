package org.aome.cvapi.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Date;

@Component
public class JwtUtil {
    @Value("${security.jwt.secret}")
    private String SECRET_KEY;

    @Value("${security.jwt.issuer}")
    private String ISSUER;

    @Value("${security.jwt.expiration}")
    private int EXPIRATION_TIME;


    public String validateTokenAndRetrievedSubject(String token) throws JWTVerificationException {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET_KEY))
                .withIssuer(ISSUER)
                .build();
        DecodedJWT jwt = verifier.verify(token);
        return jwt.getSubject();
    }

    public String generateToken(String username, String password){
        ZonedDateTime expirationAt = ZonedDateTime.now().plusSeconds(EXPIRATION_TIME);
        Date expirationDate = Date.from(expirationAt.toInstant());
        return JWT.create()
                .withSubject(String.format("%s:%s", username, password))
                .withIssuedAt(ZonedDateTime.now().toInstant())
                .withIssuer(ISSUER)
                .withExpiresAt(expirationDate)
                .sign(Algorithm.HMAC256(SECRET_KEY));
    }
}
