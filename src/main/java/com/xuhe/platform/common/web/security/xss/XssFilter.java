package com.xuhe.platform.common.web.security.xss;


import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author liqiang
 * @date 2019/06/17
 * @description: 过滤xss 跨站脚本攻击
 */
public class XssFilter implements Filter {

    //需要排除过滤的url
    private String excludedPages;
    private String[] excludedPageArray;

    @Override
    public void init(FilterConfig config) throws ServletException {
        excludedPages = config.getInitParameter("excludedPages");
        if (StringUtils.isNotEmpty(excludedPages)) {
            excludedPageArray = excludedPages.split(",");
        }else{
            excludedPageArray = new String[]{};
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        XssHttpServletRequestWrapper xssRequest = new XssHttpServletRequestWrapper((HttpServletRequest) request);

        boolean isExcludedPage = false;
        //判断是否在过滤url之外
        for (String page : excludedPageArray) {
            if (((HttpServletRequest) request).getServletPath().equals(page)) {
                isExcludedPage = true;
                break;
            }
        }
        //排除过滤url
        if (isExcludedPage) {
            chain.doFilter(request, response);
        } else {
            chain.doFilter(xssRequest, response);
        }
    }

    @Override
    public void destroy() {
    }
}
