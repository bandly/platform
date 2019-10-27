package com.shinemo.smartauth.dal.model;

import lombok.Data;

import java.util.Date;

@Data
public class SaOrgRechargeRecordDO {

    private Long id;
    private Integer accType;
    private Integer siteId;
    private Long orgId;
    private Integer amount;
    private String rechargeId;
    private Date gmtCreate;
    private Date gmtModified;



}

