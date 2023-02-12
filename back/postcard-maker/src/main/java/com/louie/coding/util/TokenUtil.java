package com.louie.coding.util;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.louie.coding.entity.User;
import com.louie.coding.exception.BusinessException;
import com.louie.coding.exception.BusinessExceptionCode;

import java.util.Calendar;
import java.util.Date;

public class TokenUtil {
    private static final String ISSUER = "LOUIE_CODING";

    public static String generateToken(User user) {
        String email = user.getEmail();
        String username = user.getUsername();
        Long id = user.getId();

        Algorithm algorithm;
        try {
            algorithm = Algorithm.RSA256(RSAUtil.getPublicKey(), RSAUtil.getPrivateKey());
        } catch (Exception e) {
            throw new BusinessException(BusinessExceptionCode.ERROR_GET_RSA_KEY);
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, 15);

        // JWT的方法其实很简单，核心就是下面这部分，一个建造者模式
        return JWT.create()
                .withKeyId(String.valueOf(id))
                .withClaim("username", username)
                .withClaim("email", email)
                .withIssuer(ISSUER)
                .withExpiresAt(calendar.getTime())
                .sign(algorithm);
    }

    public static Long verifyToken(String token) {
        Algorithm algorithm;
        try {
            algorithm = Algorithm.RSA256(RSAUtil.getPublicKey(), RSAUtil.getPrivateKey());
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(ISSUER)
                    .build();
            DecodedJWT decodedJWT = verifier.verify(token);
            return Long.valueOf(decodedJWT.getKeyId());
        } catch (TokenExpiredException e) {
            throw new BusinessException(BusinessExceptionCode.TOKEN_EXPIRED);
        } catch (Exception e) {
            throw new BusinessException(BusinessExceptionCode.TOKEN_ILLEGAL);
        }
    }
}
