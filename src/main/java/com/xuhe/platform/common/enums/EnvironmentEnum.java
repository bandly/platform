package com.xuhe.platform.common.enums;

import org.springframework.core.env.Environment;
import org.springframework.util.Assert;

/**
 * @author liqiang
 * @date 2019/06/01
 * @description:
 */
public enum EnvironmentEnum {

    /**
     * 线上
     */
    PROD,
    /**
     * 联调
     */
    FE,
    /**
     * 测试
     */
    QA,
    /**
     * 开发
     */
    DEV;

    public static boolean isProdEnv(Environment env) {
        Assert.notNull(env, "env parameter not null.");

        return EnvironmentEnum.PROD.name().equalsIgnoreCase(env.getProperty("spring.profiles.active"));
    }

    @Override
    public String toString() {
        return this.name();
    }

}
