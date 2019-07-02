package com.xuhe.platform.web.exception;


import com.xuhe.platform.common.enums.ResultCode;
import lombok.Data;
import org.apache.shiro.authc.AuthenticationException;

/**
 * @author liqiang
 * @date 2019/06/28
 * @description: 为了使shiro 验证过程中抛出的异常可以自定义，所以继承了AuthenticationException
 */
@Data
public class CustomShiroAuthenicationException extends AuthenticationException {

    protected String code;

    protected String message;

    protected ResultCode resultCode;

    /**
     * 异常抛出的数据
     */
    protected Object data;



    public CustomShiroAuthenicationException(ResultCode resultCode, Object data){
        this(resultCode);
        this.data = data;
    }

    public CustomShiroAuthenicationException(ResultCode resultCode){
        this.resultCode = resultCode;
        this.code = resultCode.code().toString();
        this.message = resultCode.message();
    }



}
