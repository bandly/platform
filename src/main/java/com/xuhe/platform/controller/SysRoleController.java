package com.xuhe.platform.controller;

import com.xuhe.platform.common.result.Result;
import com.xuhe.platform.common.validator.EditGroup;
import com.xuhe.platform.entity.common.Page;
import com.xuhe.platform.entity.query.RoleQuery;
import com.xuhe.platform.entity.vo.layui.TableResult;
import com.xuhe.platform.entity.vo.sys.role.RoleVO;
import com.xuhe.platform.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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
    public Result add(RoleVO roleVO){
        return sysRoleService.addRole(roleVO);
    }

    @GetMapping(value = "get")
    public Result get(Long roleId){
        return sysRoleService.getRoleById(roleId);
    }

    @PostMapping(value = "edit")
    public Result edit(@Validated(EditGroup.class) RoleVO roleVO){
        return sysRoleService.updateRole(roleVO);
    }
}
