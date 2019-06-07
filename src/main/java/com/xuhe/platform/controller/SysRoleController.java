package com.xuhe.platform.controller;

import com.xuhe.platform.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
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

}
