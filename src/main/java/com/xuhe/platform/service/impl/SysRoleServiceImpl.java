package com.xuhe.platform.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.xuhe.platform.common.enums.ResultCode;
import com.xuhe.platform.common.result.PlatformResult;
import com.xuhe.platform.common.result.Result;
import com.xuhe.platform.dal.mapper.SysRoleMapper;
import com.xuhe.platform.dal.model.SysRole;
import com.xuhe.platform.dal.model.SysRoleExample;
import com.xuhe.platform.entity.common.Page;
import com.xuhe.platform.entity.query.RoleQuery;
import com.xuhe.platform.entity.vo.sys.role.RoleVO;
import com.xuhe.platform.service.SysRoleService;
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
    private SysRoleMapper sysRoleMapper;

    @Override
    public Page<RoleVO> findRoleListPagination(RoleQuery roleQuery) {
        List<RoleVO> resultVOList = Lists.newArrayList();
        //分页查询
        PageHelper.startPage(roleQuery.getPageNum(), roleQuery.getPageSize());
        List<SysRole> roleList = sysRoleMapper.findRoleList(roleQuery);
        PageInfo pageInfo = new PageInfo(roleList);

        if(CollectionUtils.isNotEmpty(roleList)){
            roleList.forEach(role -> {
                resultVOList.add(assembyRoleVO(role));
            });
        }
        Page<RoleVO> page = new Page<RoleVO>(pageInfo.getPageNum(), pageInfo.getPageSize(), pageInfo.getPages(), pageInfo.getTotal(), resultVOList);
        return page;
    }

    @Override
    public Result addRole(RoleVO roleVO) {
        //校验
        SysRole role = new SysRole();
        role.setRoleName(roleVO.getRoleName());
        role.setRemark(roleVO.getRemark());
        role.setCreateTime(new Date());
        sysRoleMapper.insert(role);
        return PlatformResult.success();
    }

    @Override
    public Result getRoleById(Long roleId) {
        SysRole role = sysRoleMapper.selectByPrimaryKey(roleId.intValue());
        if(null != role){
            RoleVO roleVO = assembyRoleVO(role);
            return PlatformResult.success(roleVO);
        }
        return PlatformResult.failure(ResultCode.RESULE_DATA_NONE);
    }

    @Override
    public Result updateRole(RoleVO roleVO) {
        //校验
        SysRole role = sysRoleMapper.selectByPrimaryKey(roleVO.getRoleId().intValue());
        if(null != role){
            SysRole newRole = new SysRole();
            newRole.setRoleName(roleVO.getRoleName());
            newRole.setRemark(roleVO.getRemark());
            sysRoleMapper.updateByPrimaryKeySelective(newRole);
            return PlatformResult.success();
        }
        return PlatformResult.failure(ResultCode.RESULE_DATA_NONE);
    }

    private RoleVO assembyRoleVO(SysRole role) {
        RoleVO roleVO = new RoleVO();
        roleVO.setRoleId(role.getRoleId().longValue());
        roleVO.setRoleName(role.getRoleName());
        roleVO.setRemark(role.getRemark());
        roleVO.setCreateTime(role.getCreateTime());
        return roleVO;

    }


}
