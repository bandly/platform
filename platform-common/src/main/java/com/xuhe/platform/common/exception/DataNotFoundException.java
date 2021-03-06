package com.xuhe.platform.common.exception;


import com.xuhe.platform.common.enums.ResultCode;

/**
 * @author liqiang
 * @date 2019/05/30
 * @description: 数据没找到异常
 */
public class DataNotFoundException extends BusinessException {

    private static final long serialVersionUID = 3721036867889297081L;

    public DataNotFoundException(){
        super();
    }

    public DataNotFoundException(Object data){
        super();
        super.data = data;
    }

    public DataNotFoundException(ResultCode resultCode){
        super(resultCode);
    }

    public DataNotFoundException(ResultCode resultCode,Object data){
        super(resultCode,data);
    }

    public DataNotFoundException(String msg){
        super(msg);
    }

    public DataNotFoundException(String formatMsg,Object...objects){
        super(formatMsg,objects);
    }

}
