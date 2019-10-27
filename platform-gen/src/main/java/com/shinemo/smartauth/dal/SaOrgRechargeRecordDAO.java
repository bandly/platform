package com.shinemo.smartauth.dal;

import com.shinemo.smartauth.dal.model.SaOrgRechargeRecordDO;

public interface SaOrgRechargeRecordDAO {


    SaOrgRechargeRecordDO selectByPrimaryKey(Long id);

    boolean deleteByPrimaryKey(Long id);

    int insert(SaOrgRechargeRecordDO saOrgRechargeRecordDO);

    int insertSelective(SaOrgRechargeRecordDO saOrgRechargeRecordDO);

    void updateByPrimaryKeySelective(SaOrgRechargeRecordDO  saOrgRechargeRecordDO);
        
    void updateByPrimaryKey(SaOrgRechargeRecordDO  saOrgRechargeRecordDO);
}


