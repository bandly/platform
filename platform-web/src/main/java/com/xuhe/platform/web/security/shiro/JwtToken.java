package com.xuhe.platform.web.security.shiro;

import org.apache.shiro.authc.AuthenticationToken;


/**
 * @author liqiang
 * @date 2019/06/27
 * @description:
 */
public class JwtToken implements AuthenticationToken {

    /**
     * 密钥token
     */
    private String token;

    public JwtToken(String token){
        this.token =  token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }


}
