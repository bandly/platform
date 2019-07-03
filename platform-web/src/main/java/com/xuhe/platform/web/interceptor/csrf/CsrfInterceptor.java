package com.xuhe.platform.web.interceptor.csrf;

import com.alibaba.fastjson.JSON;
import com.xuhe.platform.common.enums.EnvironmentEnum;
import com.xuhe.platform.common.enums.ResultCode;
import com.xuhe.platform.common.result.PlatformResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.lang.Nullable;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author liqiang
 * @date 2019/06/17
 * @description: csrf 拦截器
 */
@Slf4j
public class CsrfInterceptor implements HandlerInterceptor {


    public static final String FORM_TOKEN_KEY = "_X_CSRF_TOKEN";

    public static final String HEADER_TOKEN_KEY = "X-CSRF-TOKEN";

    private Environment environment;

    public  CsrfInterceptor(Environment environment){
        this.environment = environment;
    }


    private List<String> includeUrlList = new ArrayList<>();

    private List<String> excludePathPatterns = new ArrayList<>();


    public void setExcludePathPatterns(List<String> excludePathPatterns) {
        this.excludePathPatterns = excludePathPatterns;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {

    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String[] activeProfiles = environment.getActiveProfiles();
        if(ArrayUtils.isNotEmpty(activeProfiles)){
            String env = activeProfiles[0];
            if(StringUtils.isNotEmpty(env) && Objects.equals(env, EnvironmentEnum.DEV.name())){
                return true;
            }
        }
        String requestURI = request.getRequestURI();
        if(isExcluded(requestURI,excludePathPatterns)){
            return true;
        }

        if(null == request.getSession().getAttribute(FORM_TOKEN_KEY)){
            request.getSession().setAttribute(FORM_TOKEN_KEY, UUID.randomUUID().toString().replace("-",""));

        }

        //只有post 请求校验csrf
        if(!HttpMethod.POST.name().equalsIgnoreCase(request.getMethod())){
            return true;
        }
        if(handler instanceof HandlerMethod){
            HandlerMethod m = (HandlerMethod) handler;
            if(null == m.getMethodAnnotation(CsrfCheck.class)){
                return true;
            }
            String token = getHeaderToken(request);
            if(org.springframework.util.StringUtils.hasText(token)){
                Object sessionToken = request.getSession().getAttribute(FORM_TOKEN_KEY);
                if(null == sessionToken){
                    responseWithJson(response, ResultCode.CSRF_ERROR);
                    return false;
                }
                if (token.equals(String.valueOf(sessionToken))) {
                    //request.getSession().removeAttribute(FORM_TOKEN_KEY);
                    return true;
                } else {
                    responseWithJson(response, ResultCode.CSRF_ERROR);
                    return false;
                }
            }else {
                responseWithJson(response, ResultCode.CSRF_ERROR);
                return false;
            }
        }
        return true;
    }

    private void responseWithJson(HttpServletResponse response, ResultCode resultCode) {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json; charset=utf-8");
        PlatformResult platformResult = PlatformResult.failure(resultCode);


        // 写入返回
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.append(JSON.toJSONString(platformResult));
            out.flush();
        } catch (IOException e) {
            log.warn("处理失败", e);
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    private String getHeaderToken(HttpServletRequest request){
        String token = request.getHeader(HEADER_TOKEN_KEY);
        if(token == null){
            return request.getHeader(HEADER_TOKEN_KEY.toLowerCase());
        }
        return token;
    }

    public static boolean isExcluded(String url, List<String> allowedUrlList) {
        for (String pattern : allowedUrlList) {
            Pattern p = Pattern.compile(pattern);
            Matcher m = p.matcher(url);
            if(m.find()) {
                return true;
            }
        }
        return false;
    }

}
