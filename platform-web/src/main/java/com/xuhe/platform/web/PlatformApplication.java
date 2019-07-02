package com.xuhe.platform.web;

import com.xuhe.platform.web.config.WebConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author liqiang
 * @date 2019/06/25
 * @description:
 */
@SpringBootApplication
public class PlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebConfig.class);
    }
}
