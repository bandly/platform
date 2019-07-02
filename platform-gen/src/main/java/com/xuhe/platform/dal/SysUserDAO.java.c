package com.xuhe.platform.dal;

import com.xuhe.platform.dal.model.SysUserDO;

public interface SysUserDAO {


    SysUserDO selectByPrimaryKey(Long userId);

    boolean deleteByPrimaryKey(Long userId);

    int insert(SysUserDO sysUserDO);

    int insertSelective(SysUserDO sysUserDO);

    void updateByPrimaryKeySelective(SysUserDO  sysUserDO);
        
    void updateByPrimaryKey(SysUserDO  sysUserDO);
}


