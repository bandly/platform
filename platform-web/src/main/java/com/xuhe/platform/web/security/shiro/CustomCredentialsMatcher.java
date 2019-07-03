package com.xuhe.platform.web.security.shiro;

import com.xuhe.platform.common.enums.ResultCode;
import com.xuhe.platform.service.util.PasswordUtil;
import com.xuhe.platform.web.exception.CustomShiroAuthenicationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

/**
 * @author liqiang
 * @date 2019/06/28
 * @description: 用户名密码 匹配规则 这里也可以对输入密码次数做校验
 *                例如：如果输入5次密码，都不能验证通过，则锁定当前账号
 */
public class CustomCredentialsMatcher extends SimpleCredentialsMatcher {




    /**
     * 针对用户输入的密码 和 用户系统存储的密码 比对
     * @param authenticationToken
     * @param authenticationInfo
     * @return
     */
    @Override
    public boolean doCredentialsMatch(AuthenticationToken authenticationToken, AuthenticationInfo authenticationInfo){

        UsernamePasswordToken upt = (UsernamePasswordToken)authenticationToken;
        //系统存储密码
        String accountCredentials = (String)getCredentials(authenticationInfo);
        //用户输入密码
        String tokenCredentials = new String(upt.getPassword());

        String account = upt.getUsername();
        //这采用  用户登录名 进行加盐
        String encryptPassword = PasswordUtil.passwordEncrypt(tokenCredentials,account);

        if(super.equals(accountCredentials, encryptPassword)){
            return true;
        }else{
            throw new CustomShiroAuthenicationException(ResultCode.USER_LOGIN_ERROR);
        }
    }
}
