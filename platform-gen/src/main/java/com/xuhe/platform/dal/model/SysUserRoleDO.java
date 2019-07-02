package com.xuhe.platform.dal.model;

import lombok.Data;

import java.util.Date;

@Data
public class SysUserRoleDO {

    private Integer id;
    private Integer userId;
    private Integer roleId;
    private Date createTime;
    private Date updateTime;
    private Boolean isDelete;



}

