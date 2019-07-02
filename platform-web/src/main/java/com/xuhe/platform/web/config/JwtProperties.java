package com.xuhe.platform.web.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author liqiang
 * @date 2019/06/27
 * @description:
 */
@ConfigurationProperties(prefix = "jwt.token")
@Data
public class JwtProperties {

    /**
     * token过期时间，单位分钟
     */
    Integer tokenExpireTime;



    /**
     * token加密密钥
     */
    String secretKey;

}
