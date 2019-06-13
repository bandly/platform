package com.xuhe.platform.common.web.constants;

/**
 * @author liqiang
 * @date 2019/05/28
 * @description: 请求 http 请求header 的一些常量key
 */
public class HeaderConstants {


    /**
     * 用户登录的token
     */
    public static final String X_TOKEN = "X-Token";

    /**
     * api的版本号
     */
    public static final String API_VERSION = "Api-Version";

    /**
     * app版本号
     */
    public static final String APP_VERSION = "App-Version";


    /**
     * 调用来源 {@link com.xuhe.platform.common.enums.CallSourceEnum}
     */
    public static final String CALL_SOURCE = "Call-Source";


    /**
     * API的返回格式 {@link com.xuhe.platform.common.enums.ApiStyleEnum}
     */
    public static final String API_STYLE = "Api-Style";
}
