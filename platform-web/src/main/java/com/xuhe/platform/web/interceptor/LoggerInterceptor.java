package com.xuhe.platform.web.interceptor;

import com.xuhe.platform.service.context.AuthUser;
import com.xuhe.platform.service.context.LoginContextHelper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.UUID;

/**
 * @author liqiang
 * @date 2019/06/25
 * @description: 请求日志 拦截器
 */
public class LoggerInterceptor implements HandlerInterceptor {

    private static final String REFERER_KEY = "referer";
    private static final String USER_AGENT_KEY = "ua";
    private static final String UID_KEY = "userId";
    private static final String HOST_KEY = "host";
    private static final String URI_KEY = "uri";
    public static final String TRACE_ID_KEY = "traceId";

    private static final String USER_AGENT_NAME = "User-Agent";
    private static final String REFERER_NAME = "Referer";

    /**
     * X-Forwarded-For 是一个扩展头。
     * 如：X-Forwarded-For: IP0, IP1, IP2
     * 用来表示 HTTP 请求端真实 IP
     */
    private static final String X_FORWARDED_FOR = "X-Forwarded-For";

    /**
     * health health2
     */
    private static final String HEALTH = "/health";

    /**
     * 访问日志
     */
    private Logger accessLogger = LoggerFactory.getLogger("ACCESS_LOGGER");

    /**
     * 心跳日志
     */
    private Logger healthLogger = LoggerFactory.getLogger("HEALTH-LOGGER");


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        String userAgent = request.getHeader(USER_AGENT_NAME);
        String referer = request.getHeader(REFERER_KEY);
        MDC.put(REFERER_KEY,referer);
        MDC.put(HOST_KEY,request.getHeader(X_FORWARDED_FOR));
        Optional<AuthUser> loginUser = LoginContextHelper.getLoginUser();
        if(loginUser.isPresent()){
            MDC.put(UID_KEY,loginUser.get().getUserId());
        }
        MDC.put(USER_AGENT_KEY,userAgent);
        MDC.put(URI_KEY,requestURI);
        MDC.put(TRACE_ID_KEY, UUID.randomUUID().toString().replace("-",""));
        if(StringUtils.isNotEmpty(requestURI) && requestURI.startsWith(HEALTH)){
            healthLogger.info("request:"+requestURI);
        }else{
           accessLogger.info("request:"+requestURI);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        MDC.clear();
    }
}
