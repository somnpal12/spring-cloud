package com.sample.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.sample.constant.SecurityConstants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.Map;

public class JWTUtil {


    public static String generateJWT() {
        String token = JWT.create()
                .withIssuer("PurpleTech")
                .withSubject("TestUser")
                .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(SecurityConstants.SECRET.getBytes()));
        return token;
    }

    public static Map<String, Claim> parseJWT(String token) {
        return JWT.decode(token).getClaims();
    }


    public static String extractUserName(String token) {
        return JWT.decode(token).getSubject();
    }


    private static Boolean isTokenExpired(String token) {
        return JWT.decode(token).getExpiresAt().before(new Date());
    }

    public static Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUserName(token);
        return StringUtils.equals(username,userDetails.getUsername()) && !isTokenExpired(token);
    }
}
