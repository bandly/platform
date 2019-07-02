package com.xuhe.platform.dal.model;

import lombok.Data;

import java.util.Date;

@Data
public class SysUserDO {

    private Integer userId;
    private String account;
    private String password;
    private String email;
    private String mobile;
    private Integer status;
    private Integer createUserId;
    private Date createTime;
    private Date updateTime;
    private Boolean isDelete;



}

