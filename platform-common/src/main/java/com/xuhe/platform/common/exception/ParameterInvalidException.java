package com.xuhe.platform.common.exception;


import com.xuhe.platform.common.enums.ResultCode;

/**
 * @author liqiang
 * @date 2019/05/30
 * @description: 参数无效异常
 */
public class ParameterInvalidException extends BusinessException {

    private static final long serialVersionUID = 3721036867889297081L;

    public ParameterInvalidException(){
        super();
    }

    public ParameterInvalidException(Object data){
        super();
        super.data = data;
    }

    public ParameterInvalidException(ResultCode resultCode){
        super(resultCode);
    }

    public ParameterInvalidException(ResultCode resultCode,Object data){
        super(resultCode,data);
    }

    public ParameterInvalidException(String msg){
        super(msg);
    }

    public ParameterInvalidException(String formatMsg,Object...objects){
        super(formatMsg,objects);
    }

}
