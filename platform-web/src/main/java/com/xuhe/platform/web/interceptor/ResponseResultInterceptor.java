package com.xuhe.platform.web.interceptor;


import com.xuhe.platform.common.annotation.ApiResponseStyle;
import com.xuhe.platform.common.annotation.ResponseResult;
import com.xuhe.platform.web.constants.HeaderConstants;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @author liqiang
 * @date 2019/05/28
 * @description:接口统一响应格式 拦截器
 */
public class ResponseResultInterceptor implements HandlerInterceptor {

    public static final String RESPONSE_RESULT = "RESPONSE-RESULT";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 在请求处理前 判断请求的方法或者请求的类上是否标记有注解@ResponseResult 有的话说明 需要进行响应格式包装，
        // 把 RESPONSE-RESULT 标记 存入request 属性当中
        if(handler instanceof HandlerMethod){
          final HandlerMethod handlerMethod = (HandlerMethod) handler;
            final Class<?> clazz = handlerMethod.getBeanType();
            final Method method = handlerMethod.getMethod();
            if (clazz.isAnnotationPresent(ResponseResult.class)) {
                request.setAttribute(RESPONSE_RESULT, clazz.getAnnotation(ResponseResult.class));
            } else if (method.isAnnotationPresent(ResponseResult.class)) {
                request.setAttribute(RESPONSE_RESULT, method.getAnnotation(ResponseResult.class));
            }

            if (clazz.isAnnotationPresent(ApiResponseStyle.class)) {
                request.setAttribute(HeaderConstants.API_STYLE, clazz.getAnnotation(ApiResponseStyle.class));
            }else if(method.isAnnotationPresent(ApiResponseStyle.class)){
                request.setAttribute(HeaderConstants.API_STYLE, method.getAnnotation(ApiResponseStyle.class));
            }

        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // nothing to do
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // nothing to do
    }
}
