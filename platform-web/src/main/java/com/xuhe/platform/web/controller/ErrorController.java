package com.xuhe.platform.web.controller;

import com.xuhe.platform.web.constants.MySecurityConstants;
import com.xuhe.platform.web.exception.CustomShiroAuthenicationException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author liqiang
 * @date 2019/07/01
 * @description:
 */
@RestController
public class ErrorController {

    /**
     * 重新抛出异常
     */
    @RequestMapping("/error/reCustomShiroEx")
    public void reCustomShiroEx(HttpServletRequest request) throws CustomShiroAuthenicationException{
        Object e = request.getAttribute(MySecurityConstants.REQ_ATTR_EX);
        throw (CustomShiroAuthenicationException) request.getAttribute(MySecurityConstants.REQ_ATTR_EX);
    }

    /**
     * 重新抛出异常
     */
    @RequestMapping("/error/rethrow")
    public void rethrow(HttpServletRequest request) throws Throwable {
        Object e = request.getAttribute(MySecurityConstants.REQ_ATTR_EX);
        Exception ee = (Exception) request.getAttribute(MySecurityConstants.REQ_ATTR_EX);
        throw ee.getCause();
    }
}


