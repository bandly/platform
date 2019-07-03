package com.xuhe.platform.common.util;


import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author liqiang
 * @date 2019/07/03
 * @description: md5 加密工具
 */
public class Md5Util {



    public static String md5Salt(String input, String salt) {
        return DigestUtils.md5Hex(input + salt);
    }

    public static Boolean checkMd5Salt(String input, String salt, String md5) {
        return md5.equals(DigestUtils.md5Hex(input + salt));
    }

    /**
     * 计算md5 len小于32 二次加密
     * input 为string
     */
    public static String md5Salt2(String input, Integer len, String salt) {
        return DigestUtils.md5Hex(DigestUtils.md5Hex(input).substring(0, len) + salt);
    }

    public static Boolean checkMd5Salt2(String input, Integer len, String salt, String md5) {
        if (md5 == null) {
            return false;
        }

        return md5.equals(DigestUtils.md5Hex(DigestUtils.md5Hex(input).substring(0, len) + salt));
    }

    /**
     * 计算md5 len小于32 二次加密
     * input 为byte
     * @param input
     * @param len
     * @param salt
     * @return
     */
    public static String md5Salt2(byte[] input, Integer len, String salt) {
        return DigestUtils.md5Hex(DigestUtils.md5Hex(input).substring(0, len) + salt);
    }

    /**
     * 比对
     * @param input
     * @param len
     * @param salt
     * @param md5
     * @return
     */
    public static Boolean checkMd5Salt2(byte[] input, Integer len, String salt, String md5) {
        if (md5 == null) {
            return false;
        }

        return md5.equals(DigestUtils.md5Hex(DigestUtils.md5Hex(input).substring(0, len) + salt));
    }
}
