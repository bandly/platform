package com.xuhe.platform.dal;

import com.xuhe.platform.dal.model.SysRoleDO;

public interface SysRoleDAO {


    SysRoleDO selectByPrimaryKey(Long roleId);

    boolean deleteByPrimaryKey(Long roleId);

    int insert(SysRoleDO sysRoleDO);

    int insertSelective(SysRoleDO sysRoleDO);

    void updateByPrimaryKeySelective(SysRoleDO  sysRoleDO);
        
    void updateByPrimaryKey(SysRoleDO  sysRoleDO);
}


