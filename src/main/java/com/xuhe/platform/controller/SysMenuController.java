package com.xuhe.platform.controller;

import com.xuhe.platform.common.result.Result;
import com.xuhe.platform.dal.model.SysMenu;
import com.xuhe.platform.entity.vo.layui.TableResult;
import com.xuhe.platform.entity.vo.sys.menu.MenuTree;
import com.xuhe.platform.service.SysMenuService;
import com.xuhe.platform.entity.vo.sys.menu.MenuVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author liqiang
 * @date 2019/06/06
 * @description:
 */
@RestController
@RequestMapping(value = "/sys/menu/")
public class SysMenuController {

    @Autowired
    private SysMenuService sysMenuService;


    @GetMapping(value = "list")
    public TableResult<MenuVO> list(){
        List<MenuVO>  menuVOList = sysMenuService.findMenuList();
        return new TableResult(1,menuVOList);
    }


    @PostMapping(value = "add")
    public Result add(MenuVO menuVO){
        return sysMenuService.addMenu(menuVO);
    }

    @GetMapping(value = "get")
    public Result get(Long menuId){
        return sysMenuService.getMenuById(menuId);
    }

    @PostMapping(value = "edit")
    public Result edit(MenuVO menuVO){
        return sysMenuService.updateMenu(menuVO);
    }

    @PostMapping(value = "delete")
    public Result delete(Long menuId){
        return sysMenuService.deleteMenu(menuId);
    }


    @GetMapping(value = "getMenuTreeData")
    public List<MenuTree> getMenuTreeData(){
        return sysMenuService.getMenuTreeData();
    }
}
