package com.xuhe.platform.service.admin;

import com.xuhe.platform.common.result.Result;
import com.xuhe.platform.dal.model.SysUserDO;
import com.xuhe.platform.service.dto.SysUserDTO;
import com.xuhe.platform.service.form.user.AddUserForm;
import com.xuhe.platform.service.form.user.EditUserForm;
import com.xuhe.platform.service.page.Page;
import com.xuhe.platform.service.query.UserQuery;
import com.xuhe.platform.service.vo.user.UserVO;

/**
 * @author liqiang
 * @date 2019/06/06
 * @description:
 */
public interface SysUserService {

    /**
     * 分页查找用户列表
     * @param userQuery
     * @return
     */
    Page<UserVO> findUserListPagination(UserQuery userQuery);

    /**
     * 添加管理员用户
     * @param userDTO
     * @return
     */
    Result addUser(AddUserForm userDTO);

    /**
     *根据userId 获取用户信息
     * @param userId
     * @return
     */
    Result<UserVO> getUserById(Long userId);

    /**
     * 编辑保存用户
     * @param editUserDTO
     * @return
     */
    Result updateUser(EditUserForm editUserDTO);

    /**
     * 删除用户
     * @param userId
     * @return
     */
    Result deleteUser(Long userId);

    /**
     * 根据账号获取用户信息
     * @param account
     * @return
     */
    SysUserDTO getUseByAccount(String account);
}
