package com.xuhe.platform.web.security.shiro;

import com.xuhe.platform.web.constants.HeaderConstants;
import com.xuhe.platform.web.constants.MySecurityConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

/**
 * @author liqiang
 * @date 2019/07/02
 * @description:
 */
@Slf4j
public class MyWebSessionManager extends DefaultWebSessionManager {


    @Override
    public Serializable getSessionId(SessionKey key) {
       /* Serializable id = super.getSessionId(key);
        if(null != id){
            return id;
        }
        if (WebUtils.isWeb(key)) {
            ServletRequest request = WebUtils.getRequest(key);
            ServletResponse response = WebUtils.getResponse(key);
            id = getHeaderSessionId(request, response);
        }*/
        return null;
    }

    @Override
    protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
        return null;
        //return getHeaderSessionId(request, response);
    }




    protected Serializable getHeaderSessionId(ServletRequest request, ServletResponse response) {
        String id = getHeaderSessionId((HttpServletRequest)request);
        if (id != null) {
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE,HeaderConstants.X_TOKEN);
        }
        if (id != null) {
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, id);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
        }
        request.setAttribute(ShiroHttpServletRequest.SESSION_ID_URL_REWRITING_ENABLED, isSessionIdUrlRewritingEnabled());
        return id;
    }


    /**
     * 获取请求的token
     */
    private String getHeaderSessionId(HttpServletRequest httpRequest) {
        String sessionId = null;
        //从header中获取session
        sessionId = httpRequest.getHeader(HeaderConstants.X_TOKEN);
        //如果header中不存在token，则从参数中获取token
        if (!StringUtils.hasText(sessionId)) {
            sessionId = httpRequest.getParameter(HeaderConstants.X_TOKEN);
        }
        return sessionId;
    }

}
