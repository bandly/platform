package com.xuhe.platform.utils;

import org.apache.commons.lang3.exception.ExceptionUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.InetAddress;

/**
 * Created by liqiang on 2019-05-18.
 */
public class LogUtil {

    private static Logger log = LoggerFactory.getLogger(LogUtil.class);

    public static String ip;

    private static final String LINE_SEP = System.getProperty("line.separator","\n");

    private static final String LOG_SEP_CHAR = " ";

    static{
        try{
            ip = InetAddress.getLocalHost().getHostAddress();
        }catch (Exception e){
            ip = null;
        }
    }


    public static String getErrorKVLogData(String reason,String errorCode,String errorMsg,Throwable throwable){
        if(StringUtils.isEmpty(reason)){
            log.warn("记录错误日志原因不能为空");
            return reason;
        }
        StringBuilder logData = new StringBuilder();
        logData.append(reason);
        if(!StringUtils.isEmpty(errorCode) || !StringUtils.isEmpty(errorMsg)){
            logData.append(LOG_SEP_CHAR);
            logData.append(String.valueOf(errorCode)).append(":").append(String.valueOf(errorMsg));
        }
        if(null != throwable){
            logData.append(LOG_SEP_CHAR);
            logData.append(getRootFistLineMsg(throwable));
        }
        logData.append(LOG_SEP_CHAR);
        logData.append(LOG_SEP_CHAR);
        logData.append(LOG_SEP_CHAR);
        logData.append("ip:");
        logData.append(ip);
        String error = logData.toString();
        return error;
    }


    /**
     * 堆栈信息转换成string
     * @param e e
     * @return string
     */
    private static String getStackMsg(Throwable e) {
        StringWriter buffer = new StringWriter();
        PrintWriter out = new PrintWriter(buffer);
        e.printStackTrace(out);
        out.flush();
        return buffer.toString();
    }

    /**
     * throwable转成string
     * @param throwable throwable
     */
    private static String getRootFistLineMsg(Throwable throwable) {
        if (throwable == null) {
            return null;
        }
        Throwable rootCause = ExceptionUtils.getRootCause(throwable);
        if (rootCause == null) {
            rootCause = throwable;
        }
        String rootCauseMsg = ExceptionUtils.getRootCauseMessage(rootCause);
        StringBuilder buf = new StringBuilder();
        buf.append(String.valueOf(rootCauseMsg));
        StringWriter sw = new StringWriter();
        throwable.printStackTrace(new PrintWriter(sw, true));
        String errorString = sw.getBuffer().toString();
        String[] split = errorString.split(LINE_SEP);
        if (split.length > 1) {
            buf.append(split[1]);
            return buf.toString();
        }
        if (split.length > 0) {
            buf.append(split[0]);
            return buf.toString();
        }
        return buf.toString();
    }

    public static String getErrorKVLogData(String reason, Throwable throwable) {
        return getErrorKVLogData(reason, null, null, throwable);
    }


}
