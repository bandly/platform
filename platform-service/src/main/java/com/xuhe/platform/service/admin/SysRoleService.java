package com.xuhe.platform.service.admin;

import com.xuhe.platform.common.result.Result;
import com.xuhe.platform.service.form.role.RoleForm;
import com.xuhe.platform.service.page.Page;
import com.xuhe.platform.service.query.RoleQuery;
import com.xuhe.platform.service.vo.role.RoleVO;

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
     * @param roleDTO
     * @return
     */
    Result addRole(RoleForm roleDTO);

    /**
     * 根据角色ID 获取角色信息
     * @param roleId
     * @return
     */
    Result getRoleById(Long roleId);

    /**
     * 更新角色信息
     * @param roleDTO
     * @return
     */
    Result updateRole(RoleForm roleDTO);
}
