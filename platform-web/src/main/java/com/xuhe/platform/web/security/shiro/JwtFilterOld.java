package com.xuhe.platform.web.security.shiro;
import com.xuhe.platform.web.constants.HeaderConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.springframework.util.AntPathMatcher;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author liqiang
 * @date 2019/06/28
 * @description:
 */
@Slf4j
public class JwtFilterOld extends BasicHttpAuthenticationFilter {


    private static List<String> excludeUrlList = new ArrayList<>();

    private static final AntPathMatcher ANT_PATH_MATCHER = new AntPathMatcher();


    public JwtFilterOld() {

    }

    public JwtFilterOld(String excludeUrl) {
        if (excludeUrl == null) {
            return;
        }
        String[] split = excludeUrl.split(",");
        excludeUrlList = Arrays.asList(split);
    }


    /**
     * 检测Header里Authorization字段
     * 判断是否登录
     */
    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        HttpServletRequest req = (HttpServletRequest) request;
        String token = req.getHeader(HeaderConstants.X_TOKEN);
        return token != null;
    }

    /**
     * 登录验证
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response){
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String authorization = httpServletRequest.getHeader(HeaderConstants.X_TOKEN);

        JwtToken jwtToken = new JwtToken(authorization);
        Subject subject = getSubject(request, response);
        // 提交给realm进行登入，如果错误他会抛出异常并被捕获
        subject.login(jwtToken);
        if(subject.isAuthenticated()){
            return true;
        }
        return false;
    }

    /**
     * 是否允许访问
     *
     * @param request
     * @param response
     * @param mappedValue
     * @return
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if (isLoginAttempt(request, response)) {

            boolean res = this.executeLogin(request, response);
            Subject subject = SecurityUtils.getSubject();
            System.out.println(subject.isAuthenticated());
            return res;
        }
        return true;
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
