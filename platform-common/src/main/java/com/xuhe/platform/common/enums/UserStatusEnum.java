package com.xuhe.platform.common.enums;

/**
 * @author liqiang
 * @date 2019/06/27
 * @description: 用户状态
 */
public enum UserStatusEnum {



    NORMAL(0, "正常"),

    /**
     * 禁用
     */
    FORBIDDEN(1, "禁用");

    private Integer code;

    private String message;

    UserStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer code() {
        return this.code;
    }

    public String message() {
        return this.message;
    }
}
