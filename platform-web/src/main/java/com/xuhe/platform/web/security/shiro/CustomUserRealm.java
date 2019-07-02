package com.xuhe.platform.web.security.shiro;

import com.google.common.collect.Sets;
import com.xuhe.platform.common.enums.ResultCode;
import com.xuhe.platform.common.enums.UserStatusEnum;
import com.xuhe.platform.service.admin.SysUserService;
import com.xuhe.platform.service.context.AuthUser;
import com.xuhe.platform.service.dto.SysUserDTO;
import com.xuhe.platform.web.constants.MySecurityConstants;
import com.xuhe.platform.web.exception.CustomShiroAuthenicationException;
import com.xuhe.platform.web.helper.JwtHelper;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

/**
 * @author liqiang
 * @date 2019/06/27
 * @description: 通过用户名 密码 验证
 */
public class CustomUserRealm extends AuthorizingRealm {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private JwtHelper jwtHelper;

    @Override
    public boolean supports(AuthenticationToken token){
        return token instanceof UsernamePasswordToken;
    }

    /**
     * 授权验证时调用
     * 提供用户信息 返回 权限信息
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
        //将权限名称提供给info
        authorizationInfo.setStringPermissions(permissions);
        return authorizationInfo;
    }

    /**
     * 认证(登录时调用) 返回账号认证信息 校验用户名密码
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        String account = (String) token.getPrincipal();
        SysUserDTO sysUserDTO =  sysUserService.getUseByAccount(account);
        if(null == sysUserDTO){
            throw new CustomShiroAuthenicationException(ResultCode.USER_LOGIN_ERROR);
        }
        //判断账号是否禁用
        if(UserStatusEnum.FORBIDDEN.code() == sysUserDTO.getStatus().intValue()){
            throw new CustomShiroAuthenicationException(ResultCode.USER_ACCOUNT_FORBIDDEN);
        }
        //加入session
        //加入缓存
        String jwtToken = jwtHelper.sign(account);
        //判断校验
        AuthUser authUser = new AuthUser();
        authUser.setName(sysUserDTO.getAccount());
        authUser.setUserId(sysUserDTO.getUserId().toString());
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(jwtToken,sysUserDTO.getPassword(), MySecurityConstants.SHIRO_REALM);
        return authenticationInfo;
    }
}
