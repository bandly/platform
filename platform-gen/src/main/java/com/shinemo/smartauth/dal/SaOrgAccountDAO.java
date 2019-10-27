package com.shinemo.smartauth.dal;

import com.shinemo.smartauth.dal.model.SaOrgAccountDO;

public interface SaOrgAccountDAO {


    SaOrgAccountDO selectByPrimaryKey(Long id);

    boolean deleteByPrimaryKey(Long id);

    int insert(SaOrgAccountDO saOrgAccountDO);

    int insertSelective(SaOrgAccountDO saOrgAccountDO);

    void updateByPrimaryKeySelective(SaOrgAccountDO  saOrgAccountDO);
        
    void updateByPrimaryKey(SaOrgAccountDO  saOrgAccountDO);
}


