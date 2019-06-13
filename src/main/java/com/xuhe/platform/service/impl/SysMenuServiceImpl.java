package com.xuhe.platform.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xuhe.platform.common.enums.MenuTypeEnum;
import com.xuhe.platform.common.enums.ResultCode;
import com.xuhe.platform.common.result.PlatformResult;
import com.xuhe.platform.common.result.Result;
import com.xuhe.platform.dal.mapper.SysMenuMapper;
import com.xuhe.platform.dal.model.SysMenu;
import com.xuhe.platform.dal.model.SysMenuExample;
import com.xuhe.platform.entity.vo.sys.menu.MenuTree;
import com.xuhe.platform.entity.vo.sys.menu.MenuVO;
import com.xuhe.platform.service.SysMenuService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author liqiang
 * @date 2019/06/06
 * @description:
 */
@Service
public class SysMenuServiceImpl implements SysMenuService {

    @Resource
    private SysMenuMapper sysMenuMapper;

    @Override
    public List<MenuVO> findMenuList() {
        SysMenuExample sysMenuExample = new SysMenuExample();
        SysMenuExample.Criteria criteria=sysMenuExample.createCriteria();
        criteria.andIsDeleteEqualTo(false);
        List<SysMenu> menuList = sysMenuMapper.selectByExample(sysMenuExample);
        List<MenuVO> menuVOList = Lists.newArrayList();
        if(CollectionUtils.isNotEmpty(menuList)){
            menuList.forEach(menu ->{
                menuVOList.add(assembyMenuVO(menu));
            });
        }
        return menuVOList;
    }

    @Override
    public Result addMenu(MenuVO menuVO) {
        //校验
        SysMenu sysMenu = assembyMenuDO(menuVO);
        sysMenu.setCreateTime(new Date());
        sysMenu.setIsDelete(false);
        sysMenuMapper.insert(sysMenu);
        return PlatformResult.success();
    }

    @Override
    public Result getMenuById(Long menuId) {
        //校验
        SysMenu sysMenu = sysMenuMapper.selectByPrimaryKey(menuId.intValue());
        if(null != sysMenu){
            return PlatformResult.success(assembyMenuVO(sysMenu));
        }
        return PlatformResult.failure(ResultCode.RESULE_DATA_NONE);
    }

    @Override
    public Result updateMenu(MenuVO menuVO) {
        //校验
        SysMenu sysMenu = sysMenuMapper.selectByPrimaryKey(menuVO.getMenuId().intValue());
        if(null != sysMenu){
            SysMenu newSysMenu = assembyMenuDO(menuVO);
            sysMenuMapper.updateByPrimaryKeySelective(newSysMenu);
            return PlatformResult.success();

        }
        return PlatformResult.failure(ResultCode.RESULE_DATA_NONE);
    }

    @Override
    public List<MenuTree> getMenuTreeData() {
        SysMenuExample sysMenuExample = new SysMenuExample();
        SysMenuExample.Criteria criteria=sysMenuExample.createCriteria();
        criteria.andIsDeleteEqualTo(false);
        List<SysMenu> menuList = sysMenuMapper.selectByExample(sysMenuExample);
        List<MenuTree> menuTreeList = Lists.newArrayList();
        Map<Long,List<MenuTree>> menuChildrenMap = Maps.newHashMap();
        if(CollectionUtils.isEmpty(menuList)){
            return menuTreeList;
        }
        menuList.forEach(sysMenu -> {
            MenuTree menuTree = new MenuTree();
            menuTree.setId(sysMenu.getMenuId().longValue());
            menuTree.setName(sysMenu.getMenuName());

            //顶级目录存入列表
            if(sysMenu.getParentId().intValue() == 0){
                menuTreeList.add(menuTree);
            }else{
                //查看父ID 0 表示顶级菜单，否则就是有上级菜单，放入menuChildrenMap
                List<MenuTree> children = menuChildrenMap.getOrDefault(sysMenu.getParentId().longValue(), Lists.newArrayList());
                children.add(menuTree);
                menuChildrenMap.put(sysMenu.getParentId().longValue(),children);
            }
        });

        //递归构建树
        recursionStructureMenuTree(menuChildrenMap,menuTreeList);
        return menuTreeList;
    }

    @Override
    public Result deleteMenu(Long menuId) {
        //校验
        SysMenuExample sysMenuExample = new SysMenuExample();
        SysMenuExample.Criteria criteria=sysMenuExample.createCriteria();
        criteria.andIsDeleteEqualTo(false);
        List<SysMenu> menuList = sysMenuMapper.selectByExample(sysMenuExample);
        List<MenuTree> menuTreeList = Lists.newArrayList();
        Map<Long,List<MenuTree>> menuChildrenMap = Maps.newHashMap();
        if(CollectionUtils.isEmpty(menuList)){
            return PlatformResult.success();
        }

        MenuTree resultMenu = null;

        for(SysMenu sysMenu : menuList){
            MenuTree menuTree = new MenuTree();
            menuTree.setId(sysMenu.getMenuId().longValue());
            menuTree.setName(sysMenu.getMenuName());
            if(menuTree.getId().longValue() == menuId){
                resultMenu = menuTree;
            }
            //顶级目录存入列表
            if(sysMenu.getParentId().intValue() == 0){
                menuTreeList.add(menuTree);
            }else{
                //查看父ID 0 表示顶级菜单，否则就是有上级菜单，放入menuChildrenMap
                List<MenuTree> children = menuChildrenMap.getOrDefault(sysMenu.getParentId().longValue(), Lists.newArrayList());
                children.add(menuTree);
                menuChildrenMap.put(sysMenu.getParentId().longValue(),children);
            }
        }
        //递归构建树
        recursionStructureMenuTree(menuChildrenMap,menuTreeList);

        if(null == resultMenu){
            return PlatformResult.failure(ResultCode.RESULE_DATA_NONE);
        }

        List<Long> deleteIds = Lists.newArrayList();


        List<MenuTree> list = Lists.newArrayList();
        list.add(resultMenu);
        do{
            List<MenuTree> newList = Lists.newArrayList();
            for(MenuTree menuTree : list){
                deleteIds.add(menuTree.getId());
                List<MenuTree> children = menuChildrenMap.get(menuTree.getId());
                if(CollectionUtils.isNotEmpty(children)){
                    newList.addAll(children);
                }
                list = newList;
            }
        }while (CollectionUtils.isNotEmpty(list));

        sysMenuMapper.deleteByMenuIds(deleteIds);

        return PlatformResult.success();
    }


    private void recursionStructureMenuTree(Map<Long,List<MenuTree>> menuChildrenMap,List<MenuTree> menuTreeList){
        if(CollectionUtils.isEmpty(menuTreeList)){
            return;
        }
        menuTreeList.forEach(menuTree -> {
            List<MenuTree> children = null;
            children = menuChildrenMap.get(menuTree.getId());
            if(CollectionUtils.isNotEmpty(children)){
                menuTree.setChildren(children);
                recursionStructureMenuTree(menuChildrenMap,children);
            }
        });

    }



    private MenuVO assembyMenuVO(SysMenu menu) {
        MenuVO menuVO = new MenuVO();
        menuVO.setMenuId(menu.getMenuId().longValue());
        menuVO.setMenuName(menu.getMenuName());
        menuVO.setParentId(menu.getParentId().longValue());
        menuVO.setOrderNum(menu.getOrderNum());
        menuVO.setStatus(menu.getStatus().intValue());
        menuVO.setType(menu.getType());
        menuVO.setUrl(menu.getUrl());
        menuVO.setIcon(menu.getIcon());
        menuVO.setPerms(menu.getPerms());
        menuVO.setTypeName(MenuTypeEnum.getMenuTypeName(menuVO.getType()));
        return menuVO;
    }

    private SysMenu assembyMenuDO(MenuVO menuVO) {
        SysMenu sysMenu = new SysMenu();
        sysMenu.setMenuId(menuVO.getMenuId() == null ? null : menuVO.getMenuId().intValue());
        sysMenu.setMenuName(menuVO.getMenuName());
        sysMenu.setParentId(menuVO.getParentId() == null ? 0 : menuVO.getParentId().intValue());
        sysMenu.setOrderNum(menuVO.getOrderNum()== null ? 0 :  menuVO.getOrderNum().intValue());
        sysMenu.setStatus(menuVO.getStatus() == null ? null : menuVO.getStatus().byteValue());
        sysMenu.setType(menuVO.getType());
        sysMenu.setUrl(menuVO.getUrl());
        sysMenu.setIcon(menuVO.getIcon());
        sysMenu.setPerms(menuVO.getPerms());
        return sysMenu;
    }
}
