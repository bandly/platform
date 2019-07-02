package com.xuhe.platform.web.handler;


import com.xuhe.platform.common.enums.ResultCode;
import com.xuhe.platform.common.exception.BusinessException;
import com.xuhe.platform.common.result.DefaultErrorResult;
import com.xuhe.platform.common.result.PlatformResult;
import com.xuhe.platform.web.helper.ParameterInvalidItemHelper;
import com.xuhe.platform.web.model.bo.ParameterInvalidItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.servlet.http.HttpServletRequest;

import java.util.List;

import static com.xuhe.platform.common.result.DefaultErrorResult.failure;


/**
 * @author liqiang
 * @date 2019/05/30
 * @description: 全局异常处理基础类
 */
public class BaseGlobalExceptionHandler {

    private static Logger log = LoggerFactory.getLogger(BaseGlobalExceptionHandler.class);


    /**
     * 处理验证参数封装错误时异常 比如 传入个json 格式不对 无法解析
     */
    protected ResponseEntity<PlatformResult> handleMessageNotReadableException(HttpMessageNotReadableException e, HttpServletRequest request) {
        log.info("handleConstraintViolationException start, uri:{}, caused by: ", request.getRequestURI(), e);
        DefaultErrorResult defaultErrorResult =   DefaultErrorResult.failure(ResultCode.PARAM_IS_INVALID, e, HttpStatus.BAD_REQUEST);
        return convertDefaultErrorResultToResponseEntity(defaultErrorResult);
    }

    /**
     * 处理参数绑定时异常 已web 表单形式提交的参数 @Validated 或 @Valid 注解时（反400错误码）
     */
    protected ResponseEntity<PlatformResult> handleBindException(BindException e, HttpServletRequest request) {
        log.info("handleBindException start, uri:{}, caused by: ", request.getRequestURI(), e);
        List<ParameterInvalidItem> parameterInvalidItemList = ParameterInvalidItemHelper.convertBindingResultToMapParameterInvalidItemList(e.getBindingResult());
        DefaultErrorResult defaultErrorResult =  DefaultErrorResult.failure(ResultCode.PARAM_IS_INVALID, e, HttpStatus.BAD_REQUEST, parameterInvalidItemList);
        return convertDefaultErrorResultToResponseEntity(defaultErrorResult);
    }

    /**
     * 处理使用@Validated 或 @Valid 注解时，参数验证错误异常（反400错误码）
     */
    protected ResponseEntity<PlatformResult> handleMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request) {
        log.info("handleMethodArgumentNotValidException start, uri:{}, caused by: ", request.getRequestURI(), e);
        List<ParameterInvalidItem> parameterInvalidItemList = ParameterInvalidItemHelper.convertBindingResultToMapParameterInvalidItemList(e.getBindingResult());
        DefaultErrorResult defaultErrorResult =  DefaultErrorResult.failure(ResultCode.PARAM_IS_INVALID, e, HttpStatus.BAD_REQUEST, parameterInvalidItemList);
        return convertDefaultErrorResultToResponseEntity(defaultErrorResult);
    }



    /**
     * 处理通用自定义业务异常
     */
    protected ResponseEntity<PlatformResult> handleBusinessException(BusinessException e, HttpServletRequest request) {
        log.info("handleBusinessException start, uri:{}, exception:{}, caused by: {}", request.getRequestURI(), e.getClass(), e.getMessage());
        DefaultErrorResult defaultErrorResult =  failure(e);
        return convertDefaultErrorResultToResponseEntity(defaultErrorResult);
    }

    /**
     * 处理未预测到的其他错误（反500错误码）
     */
    protected ResponseEntity<PlatformResult> handleThrowable(Throwable e, HttpServletRequest request) {
        log.error("handleThrowable start, uri:{}, caused by: ", request.getRequestURI(), e);
        DefaultErrorResult defaultErrorResult = failure(ResultCode.SYSTEM_INNER_ERROR, e, HttpStatus.INTERNAL_SERVER_ERROR);
        return convertDefaultErrorResultToResponseEntity(defaultErrorResult);
    }


    private ResponseEntity<PlatformResult> convertDefaultErrorResultToResponseEntity(DefaultErrorResult defaultErrorResult){
        PlatformResult platformResult = new PlatformResult();
        platformResult.setCode(defaultErrorResult.getCode());
        platformResult.setMsg(defaultErrorResult.getMessage());
        platformResult.setData(defaultErrorResult.getClass());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(platformResult);
    }
}
