package com.xuhe.platform.web.handler;


import com.xuhe.platform.common.enums.ResultCode;
import com.xuhe.platform.common.exception.BusinessException;
import com.xuhe.platform.common.result.DefaultErrorResult;
import com.xuhe.platform.common.result.PlatformResult;
import com.xuhe.platform.web.exception.CustomShiroAuthenicationException;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;


/**
 * @author liqiang
 * @date 2019/05/30
 * @description: 统一异常处理器
 */
@RestControllerAdvice
public class GlobalExceptionHandler extends BaseGlobalExceptionHandler {


    /** 处理参数封装异常 例如 传入的json 格式不对 等 **/
    @Override
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<PlatformResult> handleMessageNotReadableException(HttpMessageNotReadableException e, HttpServletRequest request) {
        return super.handleMessageNotReadableException(e, request);
    }


    /**
     * 处理接口参数校验不通过的异常
     * spring 将数据解释为web 表单数据的时候 校验参数 报 BindException
     */
    @Override
    //@ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public ResponseEntity<PlatformResult> handleBindException(BindException e, HttpServletRequest request) {
        return super.handleBindException(e, request);
    }


    /**
     * 处理接口参数校验不通过的异常
     * spring 将数据解释为JSON 校验参数的时候报 MethodArgumentNotValidException
     */
    @Override
    //@ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<PlatformResult> handleMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request) {
        return super.handleMethodArgumentNotValidException(e, request);
    }


    /** 处理自定义异常 */
    @Override
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<PlatformResult> handleBusinessException(BusinessException e, HttpServletRequest request) {
        return super.handleBusinessException(e, request);
    }


    /** shiro 权限认证异常 */
    @ExceptionHandler(CustomShiroAuthenicationException.class)
    public ResponseEntity<PlatformResult> handleCustomShiroAuthenicationException(CustomShiroAuthenicationException e, HttpServletRequest request) {
        PlatformResult platformResult = PlatformResult.failure(e.getResultCode(),e.getData());
        return ResponseEntity
                //.status(HttpStatus.valueOf(defaultErrorResult.getStatus()))
                .status(HttpStatus.OK)
                .body(platformResult);
    }


    /** 处理运行时异常  */
    @Override
    //@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Throwable.class)
    public ResponseEntity<PlatformResult> handleThrowable(Throwable e, HttpServletRequest request) {
        //TODO 可通过邮件、微信公众号等方式发送信息至开发人员、记录存档等操作
        return super.handleThrowable(e, request);
    }





}
