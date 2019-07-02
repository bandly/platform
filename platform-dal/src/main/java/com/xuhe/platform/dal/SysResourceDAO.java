package com.xuhe.platform.dal;



import com.xuhe.platform.dal.model.SysResourceDO;

public interface SysResourceDAO {


    SysResourceDO selectByPrimaryKey(Long resourceId);

    boolean deleteByPrimaryKey(Long resourceId);

    int insert(SysResourceDO sysResourceDO);

    int insertSelective(SysResourceDO sysResourceDO);

    void updateByPrimaryKeySelective(SysResourceDO sysResourceDO);

    void updateByPrimaryKey(SysResourceDO sysResourceDO);
}


