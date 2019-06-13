package com.xuhe.platform.common.web.handler;


import com.xuhe.platform.common.annotation.ApiResponseStyle;
import com.xuhe.platform.common.annotation.ResponseResult;
import com.xuhe.platform.common.enums.ApiStyleEnum;
import com.xuhe.platform.common.result.DefaultErrorResult;
import com.xuhe.platform.common.result.PlatformResult;
import com.xuhe.platform.common.result.Result;
import com.xuhe.platform.common.web.constants.HeaderConstants;
import com.xuhe.platform.common.web.interceptor.ResponseResultInterceptor;
import com.xuhe.platform.utils.RequestContextHolderUtil;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;


/**
 * @author liqiang
 * @date 2019/05/28
 * @description: 接口响应 统一格式处理器
 */
@ControllerAdvice
public class ResponseResultHandler implements ResponseBodyAdvice<Object> {


    /**
     * 判断是否需要执行 下面 beforeBodyWrite 方法
     * @param returnType
     * @param converterType
     * @return
     */
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        HttpServletRequest request = RequestContextHolderUtil.getRequest();
        //获取之前ResponseResultInterceptor 中存入的ResponseResult 对象
        ResponseResult responseResultAnn = (ResponseResult) request.getAttribute(ResponseResultInterceptor.RESPONSE_RESULT);

        ApiStyleEnum apiStyleEnum = (ApiStyleEnum) request.getAttribute(HeaderConstants.API_STYLE);

        if(null == apiStyleEnum){
            return null != responseResultAnn;
        }
        //当调用人员不想要封装结果时，可以在header上设置参数Api-Style=none
        return responseResultAnn != null && ApiStyleEnum.NONE !=  apiStyleEnum;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        ResponseResult responseResultAnn = (ResponseResult) RequestContextHolderUtil.getRequest().getAttribute(ResponseResultInterceptor.RESPONSE_RESULT);
        Class<? extends Result> resultClazz = responseResultAnn.value();
        if(resultClazz.isAssignableFrom(PlatformResult.class)){
            if(body instanceof DefaultErrorResult){
                DefaultErrorResult defaultErrorResult = (DefaultErrorResult) body;
                PlatformResult result = new PlatformResult();
                result.setCode(defaultErrorResult.getCode());
                result.setMsg(defaultErrorResult.getMessage());
                result.setData(defaultErrorResult.getErrors());
                return result;
            }else if(body instanceof PlatformResult){
                return body;
            }
            return PlatformResult.success(body);
        }
        return body;
    }
}

