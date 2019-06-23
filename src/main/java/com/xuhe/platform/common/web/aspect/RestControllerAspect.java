package com.xuhe.platform.common.web.aspect;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.xuhe.platform.common.web.constants.HeaderConstants;
import com.xuhe.platform.common.web.handler.GlobalExceptionHandler;
import com.xuhe.platform.utils.IpUtil;
import com.xuhe.platform.utils.RequestContextHolderUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author liqiang
 * @date 2019/06/14
 * @description: 请求参数、响应体统一日志打印
 */
@Slf4j
@Aspect
@Component
public class RestControllerAspect {


    //@within：使用“@within(注解类型)”匹配所有持有指定注解类型内的方法
    @Around("@within(org.springframework.web.bind.annotation.RestController) || @annotation(org.springframework.web.bind.annotation.RestController)")
    public Object apiLog(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();

        Method method = signature.getMethod();


        boolean logFlag = this.needToLog(method);

        if(!logFlag){
           return joinPoint.proceed();
        }

        HttpServletRequest request = RequestContextHolderUtil.getRequest();

        //ip
        String ip = IpUtil.getRealIp(request);
        //方法名
        String methodName = this.getMethodName(joinPoint);
        //参数
        String params = this.getParamsJson(joinPoint);
        //请求的用户
        String requester = "unknown";
        //请求来源
        String callSource = request.getHeader(HeaderConstants.CALL_SOURCE);
        //app 版本好
        String appVersion = request.getHeader(HeaderConstants.APP_VERSION);
        //apiVersion
        String apiVersion = request.getHeader(HeaderConstants.API_VERSION);
        //浏览器信息
        String userAgent = request.getHeader("user-agent");

        log.info("started request requester [{}] method [{}] params [{}] IP [{}] callSource [{}] appVersion [{}] apiVersion [{}] userAgent [{}]",
                requester,methodName,params,ip,callSource,appVersion,apiVersion,userAgent);
        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        log.info("Ended request requester [{}] method [{}] params [{}] response is [{}] cost [{}] millis ",
                requester,methodName,params,this.deleteSensitiveContent(result),System.currentTimeMillis() - start);

        return result;
    }

    private boolean needToLog(Method method){
       return !method.getDeclaringClass().equals(GlobalExceptionHandler.class);
        //  //判断 不是get请求 并且 不是GlobalExceptionHandler 异常拦截器方法
        //return method.getAnnotation(GetMapping.class) == null && !method.getDeclaringClass().equals(GlobalExceptionHandler.class);
    }

    private String getMethodName(ProceedingJoinPoint joinPoint){
        String methodName = joinPoint.getSignature().toShortString();
        String shortMethodNamesSuffix = "(..)";
        if(methodName.endsWith(shortMethodNamesSuffix)){
            methodName = methodName.substring(0,methodName.length() - shortMethodNamesSuffix.length());
        }
        return methodName;
    }

    private String getParamsJson(ProceedingJoinPoint joinPoint){
        Object[] args = joinPoint.getArgs();
        StringBuilder sb = new StringBuilder();
        for(Object arg : args){
            //移除敏感内容
            String paramStr;
            if(arg instanceof HttpServletResponse){
                paramStr = HttpServletResponse.class.getSimpleName();
            }else if(arg instanceof HttpServletRequest){
                paramStr = HttpServletRequest.class.getSimpleName();
            }else if(arg instanceof MultipartFile){
                long size = ((MultipartFile) arg).getSize();
                paramStr = MultipartFile.class.getSimpleName() + " size:"+size;
            }else{
                paramStr = this.deleteSensitiveContent(arg);
            }
            sb.append(paramStr).append(",");
        }
        return sb.deleteCharAt(sb.length() -1 ).toString();
    }

    /**
     * 删除参数中的敏感内容
     * @param obj 参数对象
     * @return 去除敏感内容后的参数对象
     */
    private String deleteSensitiveContent(Object obj){
        JSONObject jsonObject = new JSONObject();
        if(null == obj || obj instanceof Exception){
            return jsonObject.toJSONString();
        }

        try {
            String param = JSON.toJSONString(obj);
            jsonObject = JSONObject.parseObject(param);
            List<String> sensitiveFieldList =  this.getnSensitiveFieldList();
            for(String sensitiveField : sensitiveFieldList){
                if(jsonObject.containsKey(sensitiveField)){
                    jsonObject.put(sensitiveField,"******");
                }
            }
        }catch (ClassCastException e){
            return String.valueOf(obj);
        }
        return jsonObject.toJSONString();
    }

    private List<String> getnSensitiveFieldList(){
        List<String> sensitiveFieldList = Lists.newArrayList();
        sensitiveFieldList.add("pwd");
        sensitiveFieldList.add("password");
        sensitiveFieldList.add("repassword");
        return sensitiveFieldList;
    }

}
