package com.xuhe.platform.service.util;

import com.xuhe.platform.common.util.Md5Util;

/**
 * @author liqiang
 * @date 2019/07/03
 * @description:
 */
public class PasswordUtil {


    /**
     * 加密用户的密码 以用户账号进行加盐
     * @param password
     * @param account
     * @return
     */
    public static String passwordEncrypt(String password,String account){
        String newPassword = Md5Util.md5Salt(password,account);
        return newPassword;
    }
}
