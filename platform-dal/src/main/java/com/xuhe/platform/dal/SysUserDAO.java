package com.xuhe.platform.dal;

import com.xuhe.platform.dal.model.SysUserDO;
import com.xuhe.platform.dal.query.DalUserQuery;

import java.util.List;

public interface SysUserDAO {


    SysUserDO selectByPrimaryKey(Long userId);

    boolean deleteByPrimaryKey(Long userId);

    int insert(SysUserDO sysUserDO);

    int insertSelective(SysUserDO sysUserDO);

    void updateByPrimaryKeySelective(SysUserDO sysUserDO);
        
    void updateByPrimaryKey(SysUserDO sysUserDO);

    List<SysUserDO> findUserList(DalUserQuery dalUserQuery);

    /**
     * 根据账号 查找用户
     * @param account
     * @return
     */
    SysUserDO selectByAccount(String account);

    /**
     * 根据手机号 查找用户
     * @param mobile
     * @return
     */
    SysUserDO selectByMobile(String mobile);

    /**
     * 根据邮箱 查找用户
     * @param email
     * @return
     */
    SysUserDO selectByEmail(String email);
}


