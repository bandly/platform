package com.shinemo.smartauth.dal.model;

import lombok.Data;

import java.util.Date;

@Data
public class SaOrgDeductionDetailDO {

    private Long id;
    private Integer accType;
    private Integer siteId;
    private Long orgId;
    private Integer chargeType;
    private Integer amount;
    private String chargeId;
    private Date gmtCreate;
    private Date gmtModified;



}

