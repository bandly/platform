package com.shinemo.smartauth.dal;

import com.shinemo.smartauth.dal.model.SaOuterInvokeRecordDO;

public interface SaOuterInvokeRecordDAO {


    SaOuterInvokeRecordDO selectByPrimaryKey(Long id);

    boolean deleteByPrimaryKey(Long id);

    int insert(SaOuterInvokeRecordDO saOuterInvokeRecordDO);

    int insertSelective(SaOuterInvokeRecordDO saOuterInvokeRecordDO);

    void updateByPrimaryKeySelective(SaOuterInvokeRecordDO  saOuterInvokeRecordDO);
        
    void updateByPrimaryKey(SaOuterInvokeRecordDO  saOuterInvokeRecordDO);
}


