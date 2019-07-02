package com.xuhe.platform.dal.model;

import lombok.Data;

import java.util.Date;

@Data
public class SysRoleDO {

    private Integer roleId;
    private String roleName;
    private String remark;
    private Date createTime;
    private Date updateTime;
    private Boolean isDelete;



}

