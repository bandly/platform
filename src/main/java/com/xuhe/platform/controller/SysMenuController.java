package com.xuhe.platform.controller;

import com.xuhe.platform.service.SysMenuService;
import com.xuhe.platform.entity.vo.sys.menu.MenuVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
@RequestMapping(value = "/sys/menu/")
public class SysMenuController {

    @Autowired
    private SysMenuService sysMenuService;


    @GetMapping(value = "list")
    public List<MenuVO> list(){
        List<MenuVO> menuVOList = new ArrayList<>();
        MenuVO menuVO = new MenuVO();
        menuVO.setMenuId(1L);
        menuVO.setMenuName("菜单管理");
        menuVO.setUrl("");
        menuVO.setType(0);
        menuVO.setStatus(1);
        menuVO.setOrderNum(0);
        menuVO.setCreateTime("2019-05-27 18:59:35");
        menuVOList.add(menuVO);
        return menuVOList;
    }
}
