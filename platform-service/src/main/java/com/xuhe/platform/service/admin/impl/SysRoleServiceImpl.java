package com.xuhe.platform.service.admin.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.xuhe.platform.common.enums.ResultCode;
import com.xuhe.platform.common.result.PlatformResult;
import com.xuhe.platform.common.result.Result;
import com.xuhe.platform.dal.SysRoleDAO;
import com.xuhe.platform.dal.model.SysRoleDO;
import com.xuhe.platform.dal.query.DalRoleQuery;
import com.xuhe.platform.service.admin.SysRoleService;
import com.xuhe.platform.service.form.role.RoleForm;
import com.xuhe.platform.service.page.Page;
import com.xuhe.platform.service.query.RoleQuery;
import com.xuhe.platform.service.vo.role.RoleVO;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author liqiang
 * @date 2019/06/06
 * @description:
 */
@Service
public class SysRoleServiceImpl implements SysRoleService {

    @Resource
    private SysRoleDAO sysRoleDAO;

    @Override
    public Page<RoleVO> findRoleListPagination(RoleQuery roleQuery) {
        List<RoleVO> resultVOList = Lists.newArrayList();
        DalRoleQuery dalRoleQuery = new DalRoleQuery();
        dalRoleQuery.setRoleName(roleQuery.getRoleName());
        //分页查询
        PageHelper.startPage(roleQuery.getPageNum(), roleQuery.getPageSize());
        List<SysRoleDO> roleList = sysRoleDAO.findRoleList(dalRoleQuery);
        PageInfo pageInfo = new PageInfo(roleList);

     /*   if(CollectionUtils.isNotEmpty(roleList)){
            roleList.forEach(role -> {
                resultVOList.add(assembyRoleVO(role));
            });
        }*/
        Page<RoleVO> page = new Page<RoleVO>(pageInfo.getPageNum(), pageInfo.getPageSize(), pageInfo.getPages(), pageInfo.getTotal(), resultVOList);
        return page;
    }

    @Override
    public Result addRole(RoleForm roleForm) {
        //校验
        SysRoleDO role = new SysRoleDO();
        role.setRoleName(roleForm.getRoleName());
        role.setRemark(roleForm.getRemark());
        role.setCreateTime(new Date());
        sysRoleDAO.insert(role);
        return PlatformResult.success();
    }

    @Override
    public Result getRoleById(Long roleId) {
        SysRoleDO role = sysRoleDAO.selectByPrimaryKey(roleId);
        if(null != role){
            RoleVO roleVO = assembyRoleVO(role);
            return PlatformResult.success(roleVO);
        }
        return PlatformResult.failure(ResultCode.RESULE_DATA_NONE);
    }

    @Override
    public Result updateRole(RoleForm roleForm) {
        //校验
        SysRoleDO role = sysRoleDAO.selectByPrimaryKey(roleForm.getRoleId());
        if(null != role){
            SysRoleDO newRole = new SysRoleDO();
            newRole.setRoleId(roleForm.getRoleId().intValue());
            newRole.setRoleName(roleForm.getRoleName());
            newRole.setRemark(roleForm.getRemark());
            sysRoleDAO.updateByPrimaryKeySelective(newRole);
            return PlatformResult.success();
        }
        return PlatformResult.failure(ResultCode.RESULE_DATA_NONE);
    }

    private RoleVO assembyRoleVO(SysRoleDO role) {
        RoleVO roleVO = new RoleVO();
        roleVO.setRoleId(role.getRoleId().longValue());
        roleVO.setRoleName(role.getRoleName());
        roleVO.setRemark(role.getRemark());
        roleVO.setCreateTime(role.getCreateTime());
        return roleVO;

    }


}
