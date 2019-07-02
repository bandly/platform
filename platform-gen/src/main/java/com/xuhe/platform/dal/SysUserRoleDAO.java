package com.xuhe.platform.dal;

import com.xuhe.platform.dal.model.SysUserRoleDO;

public interface SysUserRoleDAO {


    SysUserRoleDO selectByPrimaryKey(Long userId);

    boolean deleteByPrimaryKey(Long userId);

    int insert(SysUserRoleDO sysUserRoleDO);

    int insertSelective(SysUserRoleDO sysUserRoleDO);

    void updateByPrimaryKeySelective(SysUserRoleDO  sysUserRoleDO);
        
    void updateByPrimaryKey(SysUserRoleDO  sysUserRoleDO);
}


