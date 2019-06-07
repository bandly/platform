package com.xuhe.platform.controller;


import com.xuhe.platform.common.result.PlatformResult;
import com.xuhe.platform.common.result.Result;
import com.xuhe.platform.entity.common.Page;
import com.xuhe.platform.entity.query.UserQuery;
import com.xuhe.platform.entity.vo.layui.TableResult;
import com.xuhe.platform.entity.vo.sys.user.AddUserVO;
import com.xuhe.platform.entity.vo.sys.user.EditUserVO;
import com.xuhe.platform.entity.vo.sys.user.UserVO;
import com.xuhe.platform.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liqiang
 * @date 2019/06/06
 * @description:
 */
@RestController
@RequestMapping(value = "/sys/user/")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    @GetMapping(value = "list")
    public TableResult<UserVO> list(UserQuery userQuery){
        Page<UserVO> page = sysUserService.findUserListPagination(userQuery);
        return new TableResult(page.getTotalCount(),page.getList());
    }

    @PostMapping(value = "add")
    public Result list(AddUserVO addUserVO){
        return sysUserService.addUser(addUserVO);
    }

    @GetMapping(value = "get")
    public Result get(Long userId){
        return sysUserService.getUserById(userId);
    }

    @PostMapping(value = "edit")
    public Result edit(EditUserVO editUserVO){
        return sysUserService.updateUser(editUserVO);
    }
}
