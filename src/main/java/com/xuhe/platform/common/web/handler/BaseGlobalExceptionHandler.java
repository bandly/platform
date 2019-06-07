package com.xuhe.platform.common.web.handler;


import com.xuhe.platform.common.enums.ResultCode;
import com.xuhe.platform.common.exceptions.BusinessException;
import com.xuhe.platform.common.result.DefaultErrorResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;


/**
 * @author liqiang
 * @date 2019/05/30
 * @description: 全局异常处理基础类
 */
public class BaseGlobalExceptionHandler {

    private static Logger log = LoggerFactory.getLogger(BaseGlobalExceptionHandler.class);



    /**
     * 处理通用自定义业务异常
     */
    protected ResponseEntity<DefaultErrorResult> handleBusinessException(BusinessException e, HttpServletRequest request) {
        log.info("handleBusinessException start, uri:{}, exception:{}, caused by: {}", request.getRequestURI(), e.getClass(), e.getMessage());

        DefaultErrorResult defaultErrorResult = DefaultErrorResult.failure(e);
        return ResponseEntity
                .status(HttpStatus.valueOf(defaultErrorResult.getStatus()))
                .body(defaultErrorResult);
    }

    /**
     * 处理未预测到的其他错误（反500错误码）
     */
    protected DefaultErrorResult handleThrowable(Throwable e, HttpServletRequest request) {
        log.error("handleThrowable start, uri:{}, caused by: ", request.getRequestURI(), e);
        return DefaultErrorResult.failure(ResultCode.SYSTEM_INNER_ERROR, e, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
