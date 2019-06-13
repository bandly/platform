package com.xuhe.platform.entity.vo.sys.menu;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author liqiang
 * @date 2019/05/27
 * @description: 菜单
 */
@Data
public class MenuVO {

    private Long menuId;
    private String menuName;
    private String url;
    private String perms;
    private Integer type;

    /**
     * 类型名称
     */
    private String typeName;
    private String icon;
    private Integer orderNum;
    private Integer status;
    private Date createTime;
    private Long parentId;



    @Override
    public String toString() {
        return "MenuVO{" +
                "menuId=" + menuId +
                ", menuName='" + menuName + '\'' +
                ", url='" + url + '\'' +
                ", perms='" + perms + '\'' +
                ", type=" + type +
                ", icon='" + icon + '\'' +
                ", orderNum=" + orderNum +
                ", status=" + status +
                ", createTime='" + createTime + '\'' +
                '}';
    }
}
