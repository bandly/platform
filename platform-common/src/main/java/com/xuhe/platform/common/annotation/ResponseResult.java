package com.xuhe.platform.common.annotation;



import com.xuhe.platform.common.result.PlatformResult;
import com.xuhe.platform.common.result.Result;


import java.lang.annotation.*;

/**
 * @author liqiang
 * @date 2019/05/28
 * @description:  接口返回结果增强  会通过拦截器拦截后放入标记，在ResponseResultHandler 进行结果处理
 */
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ResponseResult {

    Class<? extends Result>  value() default PlatformResult.class;
}
