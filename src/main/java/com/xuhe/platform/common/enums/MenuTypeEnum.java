package com.xuhe.platform.common.enums;

/**
 * @author liqiang
 * @date 2019/06/09
 * @description:
 */
public enum MenuTypeEnum {

    /**
     * 目录
     */
    CATALOG(0,"目录"),

    /**
     * 菜单
     */
    MENU(1,"菜单"),

    /**
     * 按钮
     */
    BUTTON(2,"按钮");

    private Integer code;

    private String value;

    MenuTypeEnum(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static MenuTypeEnum getMenuTypeEnum(Integer code){
        if(null == code){
            return null;
        }
        for (MenuTypeEnum item : MenuTypeEnum.values()) {
            if (item.code.intValue() == code.intValue()) {
                return item;
            }
        }
        return null;
    }
    public static String getMenuTypeName(Integer code){
        MenuTypeEnum menuType = getMenuTypeEnum(code);
        if(null != menuType){
            return menuType.getValue();
        }
        return null;
    }
}
