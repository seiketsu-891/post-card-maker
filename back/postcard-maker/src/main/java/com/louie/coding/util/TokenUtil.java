package com.louie.coding.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.louie.coding.entity.User;
import com.louie.coding.exception.AuthException;
import com.louie.coding.exception.AuthExceptionCode;
import com.louie.coding.exception.BusinessException;
import com.louie.coding.exception.BusinessExceptionCode;

import java.util.Calendar;
import java.util.Date;

public class TokenUtil {
    private static final String ISSUER = "LOUIE_CODING";

    public static String generateToken(User user, String type) {
        String email = user.getEmail();
        String username = user.getUsername();
        Boolean isPremium = user.getIsPremium();
        Long id = user.getId();

        Algorithm algorithm;
        try {
            algorithm = Algorithm.RSA256(RSAUtil.getPublicKey(), RSAUtil.getPrivateKey());
        } catch (Exception e) {
            throw new BusinessException(BusinessExceptionCode.ERROR_GET_RSA_KEY);
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        if (type.equals("1")) { // refreshToken
            calendar.add(Calendar.DATE, 15);
        } else {
            calendar.add(Calendar.HOUR, 1);
        }

        // JWT的方法其实很简单，核心就是下面这部分，一个建造者模式
        return JWT.create()
                .withKeyId(String.valueOf(id))
                .withClaim("username", username)
                .withClaim("email", email)
                .withClaim("isPremium", isPremium)
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
            throw new AuthException(AuthExceptionCode.TOKEN_EXPIRED);
        } catch (Exception e) {
            throw new AuthException(AuthExceptionCode.TOKEN_ILLEGAL);
        }
    }

    /**
     * 刷新token，刷新时不需要再往token里放用户信息，只需要用户id
     */
    public static String refreshToken(Long userId) {
        Algorithm algorithm;
        try {
            algorithm = Algorithm.RSA256(RSAUtil.getPublicKey(), RSAUtil.getPrivateKey());
        } catch (Exception e) {
            throw new BusinessException(BusinessExceptionCode.ERROR_GET_RSA_KEY);
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        calendar.add(Calendar.HOUR, 1);

        return JWT.create()
                .withKeyId(String.valueOf(userId))
                .withIssuer(ISSUER)
                .withExpiresAt(calendar.getTime())
                .sign(algorithm);
    }
}
