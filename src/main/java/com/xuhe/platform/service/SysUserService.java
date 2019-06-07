package com.xuhe.platform.service;

import com.xuhe.platform.common.result.Result;
import com.xuhe.platform.entity.common.Page;
import com.xuhe.platform.entity.query.UserQuery;
import com.xuhe.platform.entity.vo.sys.user.AddUserVO;
import com.xuhe.platform.entity.vo.sys.user.EditUserVO;
import com.xuhe.platform.entity.vo.sys.user.UserVO;

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
     * @param userVO
     * @return
     */
    Result addUser(AddUserVO userVO);

    /**
     *根据userId 获取用户信息
     * @param userId
     * @return
     */
    Result getUserById(Long userId);

    /**
     * 编辑保存用户
     * @param editUserVO
     * @return
     */
    Result updateUser(EditUserVO editUserVO);
}
