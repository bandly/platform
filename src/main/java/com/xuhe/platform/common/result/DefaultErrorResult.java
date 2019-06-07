package com.xuhe.platform.common.result;


import com.xuhe.platform.common.enums.BusinessExceptionEnum;
import com.xuhe.platform.common.enums.ResultCode;
import com.xuhe.platform.common.exceptions.BusinessException;
import com.xuhe.platform.utils.RequestContextHolderUtil;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * @author liqiang
 * @date 2019/05/30
 * @description:
 */
public class DefaultErrorResult implements Result {

    private static final long serialVersionUID = 1899083570489722793L;

    /**
     * http 响应状态码 {@link HttpStatus}
     */
    private Integer status;


    /**
     * http 响应状态码的英文提示
     */
    private String error;


    /**
     * 异常堆栈的精简信息
     */
    private String message;

    /**
     * 应用系统内部自定义的返回值编码，{@link ResultCode} 它是对错误更加详细的编码
     *
     * 备注：spring boot 默认返回异常时，该字段为null
     *
     */
    private Integer code;

    /**
     * 调用接口路径
     */
    private String path;

    /**
     * 异常的名字
     */
    private String exception;

    /**
     * 异常的错误传递的数据
     */
    private Object errors;

    /**
     * 时间戳
     */
    private Date timestamp;

    public static DefaultErrorResult failure(ResultCode resultCode, Throwable e, HttpStatus httpStatus, Object errors){
        DefaultErrorResult result = DefaultErrorResult.failure(resultCode,e,httpStatus);
        result.setErrors(errors);
        return result;
    }

    public static DefaultErrorResult failure(ResultCode resultCode,Throwable e,HttpStatus httpStatus){
        DefaultErrorResult result = new DefaultErrorResult();
        result.setCode(resultCode.code());
        result.setMessage(resultCode.message());
        result.setStatus(httpStatus.value());
        result.setError(httpStatus.getReasonPhrase());
        result.setException(e.getClass().getName());
        result.setPath(RequestContextHolderUtil.getRequest().getRequestURI());
        result.setTimestamp(new Date());
        return result;
    }

    public static DefaultErrorResult failure(BusinessException e){
        BusinessExceptionEnum ee = BusinessExceptionEnum.getByEClass(e.getClass());
        if(null != ee){
            return DefaultErrorResult.failure(ee.getResultCode(),e,ee.getHttpStatus(),e.getData());
        }
        DefaultErrorResult defaultErrorResult = DefaultErrorResult.failure(null == e.getResultCode()?ResultCode.SUCCESS:e.getResultCode(),e,HttpStatus.OK,e.getData());
        if(!StringUtils.isEmpty(e.getMessage())){
            defaultErrorResult.setMessage(e.getMessage());
        }
        return null;
    }




    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public Object getErrors() {
        return errors;
    }

    public void setErrors(Object errors) {
        this.errors = errors;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
