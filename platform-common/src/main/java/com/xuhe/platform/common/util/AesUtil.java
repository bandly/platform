package com.xuhe.platform.common.util;


import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;

/**
 * @author liqiang
 * @date 2019/06/18
 * @description: AES 加解密 工具类
 */
@Slf4j
public class AesUtil {

    private static final String DEFAULT_CODING = "utf-8";

    public static String encrypt(String data,String secretKey){
        String encryptedData = null;
        try{
            byte[] input = data.getBytes(DEFAULT_CODING);
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(secretKey.getBytes(DEFAULT_CODING));
            SecretKeySpec skc = new SecretKeySpec(digest,"AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5padding");
            cipher.init(Cipher.ENCRYPT_MODE,skc);

            byte[] cipherText = new byte[cipher.getOutputSize(input.length)];
            encryptedData = parseByte2HexStr(cipherText);
        }catch (Exception e){
            log.error("encrypt occurs exception,caused by:",e);
        }
        return encryptedData;
    }

    /**
     * 字节转16 进制字符串
     * @param buf
     * @return
     */
    private static String parseByte2HexStr(byte[] buf) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < buf.length; i++){
            String hex = Integer.toHexString(buf[i] & 0XFF );
            if(hex.length() == 1){
                hex = '0' + hex;
            }
            sb.append(hex);
        }
        return sb.toString();
    }

}
