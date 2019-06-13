package com.xuhe.platform.service;

import com.xuhe.platform.common.result.Result;
import com.xuhe.platform.entity.common.Page;
import com.xuhe.platform.entity.query.RoleQuery;
import com.xuhe.platform.entity.vo.sys.role.RoleVO;

/**
 * @author liqiang
 * @date 2019/06/06
 * @description:
 */
public interface SysRoleService {

    /**
     * 分页查找角色列表
     * @param roleQuery
     * @return
     */
    Page<RoleVO> findRoleListPagination(RoleQuery roleQuery);

    /**
     * 添加角色
     * @param roleVO
     * @return
     */
    Result addRole(RoleVO roleVO);

    /**
     * 根据角色ID 获取角色信息
     * @param roleId
     * @return
     */
    Result getRoleById(Long roleId);

    /**
     * 更新角色信息
     * @param roleVO
     * @return
     */
    Result updateRole(RoleVO roleVO);
}
