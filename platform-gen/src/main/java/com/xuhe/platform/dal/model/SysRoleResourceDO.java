package com.xuhe.platform.dal.model;

import lombok.Data;

import java.util.Date;

@Data
public class SysRoleResourceDO {

    private Integer id;
    private Integer roleId;
    private Integer resourceId;
    private String remark;
    private Date createTime;
    private Date updateTime;
    private Boolean isDelete;



}

