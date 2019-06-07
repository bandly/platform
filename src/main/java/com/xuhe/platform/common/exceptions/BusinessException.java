package com.xuhe.platform.common.exceptions;


import com.xuhe.platform.common.enums.BusinessExceptionEnum;
import com.xuhe.platform.common.enums.ResultCode;
import org.apache.commons.lang3.StringUtils;

/**
 * @author liqiang
 * @date 2019/05/30
 * @description: 业务异常类
 */
public class BusinessException extends RuntimeException{

    private static final long serialVersionUID = 194906846739586856L;

    protected String code;

    protected String message;

    protected ResultCode resultCode;

    /**
     * 异常抛出的数据
     */
    protected Object data;

    public BusinessException(){
        BusinessExceptionEnum exceptionEnum = BusinessExceptionEnum.getByEClass(this.getClass());
        if (exceptionEnum != null) {
            resultCode = exceptionEnum.getResultCode();
            code = exceptionEnum.getResultCode().code().toString();
            message = exceptionEnum.getResultCode().message();
        }
    }

    public BusinessException(String message){
        this();
        this.message = message;
    }

    public BusinessException(String format,Object...objects){
        this();
        format = StringUtils.replace(format,"{}","%s");
        this.message = String.format(format,objects);
    }

    public BusinessException(ResultCode resultCode,Object data){
        this(resultCode);
        this.data = data;
    }

    public BusinessException(ResultCode resultCode){
        this.resultCode = resultCode;
        this.code = resultCode.code().toString();
        this.message = resultCode.message();
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ResultCode getResultCode() {
        return resultCode;
    }

    public void setResultCode(ResultCode resultCode) {
        this.resultCode = resultCode;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
