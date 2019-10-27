package com.shinemo.smartauth.dal;

import com.shinemo.smartauth.dal.model.SaOrgDeductionDetailDO;

public interface SaOrgDeductionDetailDAO {


    SaOrgDeductionDetailDO selectByPrimaryKey(Long id);

    boolean deleteByPrimaryKey(Long id);

    int insert(SaOrgDeductionDetailDO saOrgDeductionDetailDO);

    int insertSelective(SaOrgDeductionDetailDO saOrgDeductionDetailDO);

    void updateByPrimaryKeySelective(SaOrgDeductionDetailDO  saOrgDeductionDetailDO);
        
    void updateByPrimaryKey(SaOrgDeductionDetailDO  saOrgDeductionDetailDO);
}


