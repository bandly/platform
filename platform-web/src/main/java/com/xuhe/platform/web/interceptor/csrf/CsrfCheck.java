package com.xuhe.platform.web.interceptor.csrf;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author liqiang
 * @date 2019/06/17
 * @description: 标示需要 csrf 检测
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface CsrfCheck {

}
