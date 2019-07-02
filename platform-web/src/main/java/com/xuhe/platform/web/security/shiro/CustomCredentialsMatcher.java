package com.xuhe.platform.web.security.shiro;

import com.xuhe.platform.common.enums.ResultCode;
import com.xuhe.platform.web.exception.CustomShiroAuthenicationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

/**
 * @author liqiang
 * @date 2019/06/28
 * @description: 用户名密码 匹配规则
 */
public class CustomCredentialsMatcher extends SimpleCredentialsMatcher {


    /**
     * 这里就
     * @param authenticationToken
     * @param authenticationInfo
     * @return
     */
    @Override
    public boolean doCredentialsMatch(AuthenticationToken authenticationToken, AuthenticationInfo authenticationInfo){
        Object tokenCredentials = getCredentials(authenticationToken);
        Object accountCredentials = getCredentials(authenticationInfo);
        if( super.equals(tokenCredentials, accountCredentials)){
            return true;
        }else{
            throw new CustomShiroAuthenicationException(ResultCode.USER_LOGIN_ERROR);
        }
    }
}
