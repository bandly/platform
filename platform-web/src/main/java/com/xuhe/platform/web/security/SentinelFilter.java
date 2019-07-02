package com.xuhe.platform.web.security;


import com.xuhe.platform.web.constants.MySecurityConstants;
import com.xuhe.platform.web.exception.CustomShiroAuthenicationException;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author liqiang
 * @date 2019/07/01
 * @description: 过滤器哨兵
 */
@Slf4j
public class SentinelFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request instanceof HttpServletRequest) {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            String requestURI = httpRequest.getRequestURI();
            //sentinel限流处理
        /*    if (!sentinelCheck(requestURI)) {
                HttpServletResponse httpResponse = (HttpServletResponse) response;
                responseForSentinel(httpResponse, ErrorCode.SENTINEL_ERROR.getCode(), "请求用户过多，请稍后再试", null);
                return;
            }*/
        }
        try{
            chain.doFilter(request, response);
        }catch (Exception e){
            if(e.getCause() instanceof  CustomShiroAuthenicationException){
                request.setAttribute(MySecurityConstants.REQ_ATTR_EX,e.getCause());
                request.getRequestDispatcher("/error/reCustomShiroEx").forward(request,response);
            }else{
                //发生任何异常 ，保存堆栈
                request.setAttribute(MySecurityConstants.REQ_ATTR_EX,e);
                request.getRequestDispatcher("/error/rethrow").forward(request,response);
            }
        }

    }
}
