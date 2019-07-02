package com.xuhe.platform.dal;

import com.xuhe.platform.dal.model.SysRoleResourceDO;

public interface SysRoleResourceDAO {


    SysRoleResourceDO selectByPrimaryKey(Long roleId);

    boolean deleteByPrimaryKey(Long roleId);

    int insert(SysRoleResourceDO sysRoleResourceDO);

    int insertSelective(SysRoleResourceDO sysRoleResourceDO);

    void updateByPrimaryKeySelective(SysRoleResourceDO  sysRoleResourceDO);
        
    void updateByPrimaryKey(SysRoleResourceDO  sysRoleResourceDO);
}


