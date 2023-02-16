package com.example.apigateway.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.apigateway.dto.TokenUser;
import com.example.apigateway.properties.JwtProperties;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {

    private static final String ROLE_CLAIM_KEY = "role";

    private final JwtProperties jwtProperties;

    private static Algorithm algorithm;
    private static JWTVerifier jwtVerifier;

    public JwtUtils(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
        this.algorithm = Algorithm.HMAC512(jwtProperties.getSecret());
        this.jwtVerifier = JWT.require(algorithm).acceptLeeway(5).build();
    }

    public boolean isValid(String token) {
        try {
            jwtVerifier.verify(token);
            return true;
        } catch (RuntimeException e) {
            return false;
        }
    }

    public TokenUser decode(String token) {
        jwtVerifier.verify(token);

        DecodedJWT jwt = JWT.decode(token);

        String id = jwt.getSubject();
        String role = jwt.getClaim(ROLE_CLAIM_KEY).asString();

        return new TokenUser(id, role);
    }

    // token 발급용 테스트 메서드
    private static String generate(TokenUser user, Algorithm algorithm) {
        Date now = new Date();
        Date expiresAt = new Date(now.getTime() + 2888800000L * 1000);

        return JWT.create()
                .withSubject(user.getId())
                .withClaim(ROLE_CLAIM_KEY, user.getRole())
                .withExpiresAt(expiresAt)
                .withIssuedAt(now)
                .sign(algorithm);
    }

}
