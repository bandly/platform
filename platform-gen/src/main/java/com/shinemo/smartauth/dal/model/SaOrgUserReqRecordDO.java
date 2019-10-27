package com.shinemo.smartauth.dal.model;

import lombok.Data;

import java.util.Date;

@Data
public class SaOrgUserReqRecordDO {

    private Long id;
    private Long uid;
    private String mobile;
    private Integer accType;
    private Integer siteId;
    private Long orgId;
    private Integer supplierType;
    private String actionName;
    private Integer operateType;
    private Integer charge;
    private String chargeId;
    private String extend;
    private Date gmtCreate;
    private Date gmtModified;



}

