package com.xuhe.platform.web.security.shiro;
import com.xuhe.platform.common.enums.ResultCode;
import com.xuhe.platform.web.constants.HeaderConstants;
import com.xuhe.platform.web.exception.CustomShiroAuthenicationException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.util.AntPathMatcher;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * @author liqiang
 * @date 2019/06/28
 * @description:
 */
@Slf4j
public class JwtFilter  extends AccessControlFilter {


    private static List<String> excludeUrlList = new ArrayList<>();

    private static final AntPathMatcher ANT_PATH_MATCHER = new AntPathMatcher();


    public JwtFilter(String excludeUrl) {
        if (excludeUrl == null) {
            return;
        }
        String[] split = excludeUrl.split(",");
        excludeUrlList = Arrays.asList(split);
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        HttpServletRequest req = (HttpServletRequest) request;
        String requestURI = req.getRequestURI();
        if (isExclude(requestURI)) {
            return true;
        }

        if(null != getSubject(request, response)
                && getSubject(request, response).isAuthenticated()) {
            return true;
        }
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        if(isJwtSubmission(request)){
            AuthenticationToken token = createToken(request, response);
            try {
                Subject subject = getSubject(request, response);
                subject.login(token);
                return true;
            } catch (AuthenticationException e) {
                throw e;
                //log.error(e.getMessage(),e);
                //WebUtils.toHttp(response).sendError(HttpServletResponse.SC_UNAUTHORIZED,e.getMessage());
            }
        }
        throw new CustomShiroAuthenicationException(ResultCode.TOKEN_INVALID);
        //return false;
    }

    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
        HttpServletRequest req = (HttpServletRequest) request;
        String jwt = req.getHeader(HeaderConstants.X_TOKEN);
        //String host = request.getRemoteHost();
        log.info("authenticate jwt token:"+jwt);
        return new JwtToken(jwt);
    }


    protected boolean isJwtSubmission(ServletRequest request) {
        HttpServletRequest req = (HttpServletRequest) request;
        String jwt = req.getHeader(HeaderConstants.X_TOKEN);
        return (request instanceof HttpServletRequest)
                && StringUtils.isNotBlank(jwt);
    }

    private static boolean isExclude(String uri) {
        return matchUrI(excludeUrlList, uri);
    }

    private static boolean matchUrI(List<String> list, String uri) {
        for (String pattern : list) {
            if (ANT_PATH_MATCHER.match(pattern, uri)) {
                return true;
            }
        }
        return false;
    }
}
