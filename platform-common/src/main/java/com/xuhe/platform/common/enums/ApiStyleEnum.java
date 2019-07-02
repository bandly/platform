package com.xuhe.platform.common.enums;

/**
 * @author liqiang
 * @date 2019/05/28
 * @description: api 风格枚举
 */
public enum ApiStyleEnum {

    /**
     * 平台统一返回格式
     */
    PLATFORM_RESULT,

    NONE;

    public static boolean isValid(String name) {
        for (ApiStyleEnum callSource : ApiStyleEnum.values()) {
            if (callSource.name().equals(name)) {
                return true;
            }
        }
        return false;
    }

}
