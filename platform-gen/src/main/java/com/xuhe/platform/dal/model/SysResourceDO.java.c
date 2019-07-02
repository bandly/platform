package com.xuhe.platform.dal.model;

import lombok.Data;

import java.util.Date;

@Data
public class SysResourceDO {

    private Integer resourceId;
    private Integer parentId;
    private String resourceName;
    private String url;
    private String perms;
    private Integer type;
    private String icon;
    private Integer orderNum;
    private Boolean status;
    private Date createTime;
    private Date updateTime;
    private Boolean isDelete;



}

