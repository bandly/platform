package com.xuhe.platform.controller;


import com.xuhe.platform.common.annotation.ApiResponseStyle;
import com.xuhe.platform.common.annotation.ResponseResult;
import com.xuhe.platform.common.enums.ApiStyleEnum;
import com.xuhe.platform.common.result.PlatformResult;
import com.xuhe.platform.common.result.Result;
import com.xuhe.platform.common.web.constants.HeaderConstants;
import com.xuhe.platform.common.web.interceptor.csrf.CsrfCheck;
import com.xuhe.platform.entity.common.Page;
import com.xuhe.platform.entity.query.UserQuery;
import com.xuhe.platform.entity.vo.layui.TableResult;
import com.xuhe.platform.entity.vo.sys.user.AddUserVO;
import com.xuhe.platform.entity.vo.sys.user.EditUserVO;
import com.xuhe.platform.entity.vo.sys.user.UserVO;
import com.xuhe.platform.service.SysUserService;
import com.xuhe.platform.utils.RequestContextHolderUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liqiang
 * @date 2019/06/06
 * @description:
 */
@ResponseResult
@RestController
@RequestMapping(value = "/sys/user/")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;


    @ApiResponseStyle(value = ApiStyleEnum.NONE)
    @GetMapping(value = "list")
    public TableResult<UserVO> list(UserQuery userQuery){
        HttpServletRequest request = RequestContextHolderUtil.getRequest();
        request.setAttribute(HeaderConstants.API_STYLE, ApiStyleEnum.NONE);
        Page<UserVO> page = sysUserService.findUserListPagination(userQuery);
        return new TableResult(page.getTotalCount(),page.getList());
    }


    @CsrfCheck
    @PostMapping(value = "add")
    public Result list(@Valid AddUserVO addUserVO/*,BindingResult bindingResult*/){
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
    public Result edit(EditUserVO editUserVO){
        return sysUserService.updateUser(editUserVO);
    }

    @PostMapping(value = "delete")
    public Result delete(@RequestParam(name = "userId", required = true) Long userId){
        return sysUserService.deleteUser(userId);
    }
}
