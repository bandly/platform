package com.shinemo.smartauth.dal.model;

import lombok.Data;

import java.util.Date;

@Data
public class SaOrgAccountDO {

    private Long id;
    private Integer accType;
    private Integer siteId;
    private Long orgId;
    private String orgName;
    private Integer status;
    private java.lang.Long amount;
    private Date gmtCreate;
    private Date gmtModified;



}

