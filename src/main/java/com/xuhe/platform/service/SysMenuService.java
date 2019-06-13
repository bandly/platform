package com.xuhe.platform.service;

import com.xuhe.platform.common.result.Result;
import com.xuhe.platform.dal.model.SysMenu;
import com.xuhe.platform.entity.vo.sys.menu.MenuTree;
import com.xuhe.platform.entity.vo.sys.menu.MenuVO;

import java.util.List;

/**
 * @author liqiang
 * @date 2019/06/06
 * @description:
 */
public interface SysMenuService {

    /**
     * 查询全部菜单列表
     * @return
     */
    List<MenuVO> findMenuList();

    Result addMenu(MenuVO menuVO);

    Result getMenuById(Long menuId);

    Result updateMenu(MenuVO menuVO);

    List<MenuTree> getMenuTreeData();

    Result deleteMenu(Long menuId);
}
