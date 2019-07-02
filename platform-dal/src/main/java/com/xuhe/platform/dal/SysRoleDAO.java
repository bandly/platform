package com.xuhe.platform.dal;



import com.xuhe.platform.dal.model.SysRoleDO;
import com.xuhe.platform.dal.query.DalRoleQuery;

import java.util.List;

public interface SysRoleDAO {


    SysRoleDO selectByPrimaryKey(Long roleId);

    boolean deleteByPrimaryKey(Long roleId);

    int insert(SysRoleDO sysRoleDO);

    int insertSelective(SysRoleDO sysRoleDO);

    void updateByPrimaryKeySelective(SysRoleDO sysRoleDO);

    void updateByPrimaryKey(SysRoleDO sysRoleDO);

    List<SysRoleDO> findRoleList(DalRoleQuery dalRoleQuery);
}


