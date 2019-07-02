package com.xuhe.platform.common.annotation;


import com.xuhe.platform.common.enums.ApiStyleEnum;
import com.xuhe.platform.common.result.PlatformResult;
import com.xuhe.platform.common.result.Result;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author liqiang
 * @date 2019/05/28
 * @description:
 */
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiResponseStyle {

    ApiStyleEnum value() default ApiStyleEnum.PLATFORM_RESULT;
}
