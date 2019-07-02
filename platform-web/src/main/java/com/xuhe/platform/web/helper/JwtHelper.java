package com.xuhe.platform.web.helper;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.xuhe.platform.web.config.JwtProperties;
import com.xuhe.platform.web.constants.MySecurityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author liqiang
 * @date 2019/06/27
 * @description:
 */
@Component
public class JwtHelper {

    @Autowired
    private JwtProperties jwtProperties;


    /**
     * 检验token 是否正确
     * @param token
     * @return
     */
    public boolean verify(String token){
        String secret = getClaim(token,MySecurityConstants.ACCOUNT) + jwtProperties.getSecretKey();
        Algorithm algorithm = Algorithm.HMAC256(secret);
        JWTVerifier verifier = JWT.require(algorithm)
                .build();
        verifier.verify(token);
       return true;
    }


    /**
     * 获得 token 中的信息 无需secret 解密也能获得
     * @param token
     * @param claim
     * @return
     */
    public  String getClaim(String token,String claim){
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim(claim).asString();
        }catch (JWTDecodeException e){
            return null;
        }


    }


    /**
     * 生成签名，设置 后过期
     * @param account
     * @return
     */
    public  String sign(String account){
        Long currentTimeMillis = System.currentTimeMillis();
        //账号加JWT 私钥加密
        String secret = account + jwtProperties.getSecretKey();
        //此出过期时间，单位: 毫秒
        Date date = new Date(currentTimeMillis + jwtProperties.getTokenExpireTime() * 60 * 1000L);
        Algorithm algorithm = Algorithm.HMAC256(secret);


        return JWT.create()
                .withClaim(MySecurityConstants.ACCOUNT,account)
                .withClaim(MySecurityConstants.CURRENT_TIME_MILLIS,currentTimeMillis)
                .withExpiresAt(date)
                .sign(algorithm);
    }
}
