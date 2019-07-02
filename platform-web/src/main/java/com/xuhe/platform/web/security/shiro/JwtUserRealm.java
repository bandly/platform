package com.xuhe.platform.web.security.shiro;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.google.common.collect.Sets;
import com.xuhe.platform.common.enums.ResultCode;
import com.xuhe.platform.common.enums.UserStatusEnum;
import com.xuhe.platform.service.admin.SysUserService;
import com.xuhe.platform.service.context.AuthUser;
import com.xuhe.platform.service.context.LoginContextHelper;
import com.xuhe.platform.service.dto.SysUserDTO;
import com.xuhe.platform.web.constants.MySecurityConstants;
import com.xuhe.platform.web.exception.CustomShiroAuthenicationException;
import com.xuhe.platform.web.helper.JwtHelper;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

/**
 * @author liqiang
 * @date 2019/06/27
 * @description: jwt 验证
 */
public class JwtUserRealm extends AuthorizingRealm {


    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private SysUserService sysUserService;

    @Override
    public boolean supports(AuthenticationToken token){
        return token instanceof JwtToken;
    }


    /**
     * 获取角色信息，授权信息
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String token = (String) principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        //根据用户名查询当前用户拥有的角色
        Set<String> roleIds = Sets.newHashSet();

        //将角色名称提供给info
        authorizationInfo.setRoles(roleIds);
        //根据用户名查询当前用户权限
        Set<String> permissions = Sets.newHashSet();
        permissions.add("sys:user:list");
        //将权限名称提供给info
        authorizationInfo.setStringPermissions(permissions);
        return authorizationInfo;
    }


    /**
     * 校验token 是否合法
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String jwtToken = (String) token.getPrincipal();
        String account = jwtHelper.getClaim(jwtToken, MySecurityConstants.ACCOUNT);
        if(null == account){
            throw new CustomShiroAuthenicationException(ResultCode.TOKEN_INVALID);
        }

        try {
            boolean result = jwtHelper.verify(jwtToken);
            if (!result) {
                throw new CustomShiroAuthenicationException(ResultCode.TOKEN_EXPIRED);
            }
        } catch (TokenExpiredException tokenExpiredException) {
            throw new CustomShiroAuthenicationException(ResultCode.TOKEN_EXPIRED);
        }

        SysUserDTO sysUserDTO =  sysUserService.getUseByAccount(account);
        if(null == sysUserDTO){
            throw new CustomShiroAuthenicationException(ResultCode.USER_LOGIN_ERROR);
        }
        //判断账号是否禁用
        if(UserStatusEnum.FORBIDDEN.code() == sysUserDTO.getStatus().intValue()){
            throw new CustomShiroAuthenicationException(ResultCode.USER_ACCOUNT_FORBIDDEN);
        }
        AuthUser authUser = new AuthUser();
        authUser.setName(sysUserDTO.getAccount());
        authUser.setUserId(sysUserDTO.getUserId().toString());
        LoginContextHelper.setCurrentLoginUser(authUser);
        return new SimpleAuthenticationInfo(jwtToken, jwtToken, MySecurityConstants.SHIRO_REALM);

    }
}
