package com.xuhe.platform.service.context;

import java.util.Optional;

/**
 * @author liqiang
 * @date 2019/06/25
 * @description:
 */
public class LoginContextHelper {


    /**
     * 方便当前线程 中的登录信息传递
     */
    private static final ThreadLocal<AuthUser> AUTH_HOLDER = new ThreadLocal<AuthUser>();


    public static Optional<AuthUser> getLoginUser(){
        return Optional.ofNullable(AUTH_HOLDER.get());
    }


    /**
     * 设置当前登录用户信息
     * @param authUser
     */
    public static void setCurrentLoginUser(AuthUser authUser){
        AUTH_HOLDER.set(authUser);
    }



    public static Optional<String> getLoginUserId(){
        Optional<AuthUser> currentUser = getLoginUser();
        if(currentUser.isPresent()){
            return Optional.ofNullable(currentUser.get().getUserId());
        }
        return Optional.empty();
    }




}
