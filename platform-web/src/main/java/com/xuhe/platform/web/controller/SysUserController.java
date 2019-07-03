package com.xuhe.platform.web.controller;


import com.xuhe.platform.common.annotation.ApiResponseStyle;
import com.xuhe.platform.common.annotation.ResponseResult;
import com.xuhe.platform.common.enums.ApiStyleEnum;
import com.xuhe.platform.common.result.Result;
import com.xuhe.platform.common.util.RequestContextHolderUtil;
import com.xuhe.platform.service.admin.SysUserService;
import com.xuhe.platform.service.form.user.AddUserForm;
import com.xuhe.platform.service.form.user.EditUserForm;
import com.xuhe.platform.service.page.Page;
import com.xuhe.platform.service.query.UserQuery;
import com.xuhe.platform.service.vo.user.UserVO;
import com.xuhe.platform.web.constants.HeaderConstants;
import com.xuhe.platform.web.constants.MySecurityConstants;
import com.xuhe.platform.web.interceptor.csrf.CsrfCheck;
import com.xuhe.platform.web.vo.layui.TableResult;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


/**
 * @author liqiang
 * @date 2019/06/06
 * @description:
 */
//ResponseResult
@RestController
@RequestMapping(value = "/sys/user/")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;


    //@ApiResponseStyle(value = ApiStyleEnum.NONE)
    @RequiresPermissions(value = "sys:user:list")
    @GetMapping(value = "list")
    public TableResult<UserVO> list(UserQuery userQuery){
        HttpServletRequest request = RequestContextHolderUtil.getRequest();
        request.setAttribute(HeaderConstants.API_STYLE, ApiStyleEnum.NONE);
        Page<UserVO> page = sysUserService.findUserListPagination(userQuery);
        return new TableResult(page.getTotalCount(),page.getList());
    }


    // @CsrfCheck
    @PostMapping(value = "add")
    public Result list(@Valid AddUserForm addUserVO/*,BindingResult bindingResult*/){
       /* if(bindingResult.hasErrors()){
            for(ObjectError error: bindingResult.getAllErrors()){
                System.out.println(error.getDefaultMessage());
            }
        }*/

        return sysUserService.addUser(addUserVO);
    }

    @GetMapping(value = "get")
    public Result<UserVO> get(@RequestParam(name = "userId", required = true) Long userId){
        return sysUserService.getUserById(userId);
    }

    @PostMapping(value = "edit")
    public Result edit(EditUserForm editUserForm){
        return sysUserService.updateUser(editUserForm);
    }

    @PostMapping(value = "delete")
    public Result delete(@RequestParam(name = "userId", required = true) Long userId){
        return sysUserService.deleteUser(userId);
    }
}
