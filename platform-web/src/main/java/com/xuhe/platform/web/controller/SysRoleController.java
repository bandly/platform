package com.xuhe.platform.web.controller;

import com.xuhe.platform.common.result.Result;
import com.xuhe.platform.service.admin.SysRoleService;
import com.xuhe.platform.service.form.role.RoleForm;
import com.xuhe.platform.service.page.Page;
import com.xuhe.platform.service.query.RoleQuery;
import com.xuhe.platform.service.validator.EditGroup;
import com.xuhe.platform.service.vo.role.RoleVO;
import com.xuhe.platform.web.vo.layui.TableResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author liqiang
 * @date 2019/06/06
 * @description:
 */
@RestController
@RequestMapping(value = "/sys/role/")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;


    @GetMapping(value = "list")
    public TableResult<RoleVO> list(RoleQuery roleQuery){
        Page<RoleVO> page = sysRoleService.findRoleListPagination(roleQuery);
        return new TableResult(page.getTotalCount(),page.getList());
    }

    @PostMapping(value = "add")
    public Result add(RoleForm roleForm){
        return sysRoleService.addRole(roleForm);
    }

    @GetMapping(value = "get")
    public Result get(Long roleId){
        return sysRoleService.getRoleById(roleId);
    }

    @PostMapping(value = "edit")
    public Result edit(@Validated(EditGroup.class) RoleForm roleForm){
        return sysRoleService.updateRole(roleForm);
    }
}
