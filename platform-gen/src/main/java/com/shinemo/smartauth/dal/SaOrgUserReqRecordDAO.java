package com.shinemo.smartauth.dal;

import com.shinemo.smartauth.dal.model.SaOrgUserReqRecordDO;

public interface SaOrgUserReqRecordDAO {


    SaOrgUserReqRecordDO selectByPrimaryKey(Long id);

    boolean deleteByPrimaryKey(Long id);

    int insert(SaOrgUserReqRecordDO saOrgUserReqRecordDO);

    int insertSelective(SaOrgUserReqRecordDO saOrgUserReqRecordDO);

    void updateByPrimaryKeySelective(SaOrgUserReqRecordDO  saOrgUserReqRecordDO);
        
    void updateByPrimaryKey(SaOrgUserReqRecordDO  saOrgUserReqRecordDO);
}


