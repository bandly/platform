package com.xuhe.platform.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.xuhe.platform.common.enums.ResultCode;
import com.xuhe.platform.common.exceptions.BusinessException;
import com.xuhe.platform.common.exceptions.DataConflictException;
import com.xuhe.platform.common.exceptions.DataNotFoundException;
import com.xuhe.platform.common.exceptions.ParameterInvalidException;
import com.xuhe.platform.common.result.PlatformResult;
import com.xuhe.platform.common.result.Result;
import com.xuhe.platform.dal.mapper.SysUserMapper;
import com.xuhe.platform.dal.model.SysUser;
import com.xuhe.platform.dal.model.SysUserExample;
import com.xuhe.platform.entity.common.Page;
import com.xuhe.platform.entity.query.UserQuery;
import com.xuhe.platform.entity.vo.sys.user.AddUserVO;
import com.xuhe.platform.entity.vo.sys.user.EditUserVO;
import com.xuhe.platform.entity.vo.sys.user.UserVO;
import com.xuhe.platform.service.SysUserService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author liqiang
 * @date 2019/06/06
 * @description:
 */
@Service
public class SysUserServiceImpl implements SysUserService {


    @Resource
    private SysUserMapper sysUserMapper;

    @Override
    public Page<UserVO> findUserListPagination(UserQuery userQuery) {
        List<UserVO> resultVOList = Lists.newArrayList();
        //分页查询
        PageHelper.startPage(userQuery.getPageNum(), userQuery.getPageSize());
        List<SysUser> userList = sysUserMapper.findUserList(userQuery);
        PageInfo pageInfo = new PageInfo(userList);

        if(CollectionUtils.isNotEmpty(userList)){
            userList.forEach(user -> {
                resultVOList.add(assembyUserVO(user));
            });
        }
        Page<UserVO> page = new Page<UserVO>(pageInfo.getPageNum(), pageInfo.getPageSize(), pageInfo.getPages(), pageInfo.getTotal(), resultVOList);
        return page;
    }

    @Override
    public Result addUser(AddUserVO addUserVO) {
        //校验 两次密码是否一致
        if(!Objects.equals(addUserVO.getPassword(),addUserVO.getRepassword())){
            throw new ParameterInvalidException(ResultCode.TWICE_PASSWORD_NO_SAME);
        }

        //用户名是否已存在
        SysUserExample sysUserExample = new SysUserExample();
        sysUserExample.createCriteria()
                .andUsernameEqualTo(addUserVO.getUsername())
                .andIsDeleteEqualTo(false);

        List<SysUser> oldUser = sysUserMapper.selectByExample(sysUserExample);
        if(CollectionUtils.isNotEmpty(oldUser)){
            throw new DataConflictException(ResultCode.USER_NAME_HAS_EXISTED);
        }
        //判断手机号码是否重复
        if(StringUtils.isNotEmpty(addUserVO.getMobile())){
            sysUserExample = new SysUserExample();
            sysUserExample.createCriteria()
                    .andMobileEqualTo(addUserVO.getMobile())
                    .andIsDeleteEqualTo(false);
            oldUser = sysUserMapper.selectByExample(sysUserExample);
            if(CollectionUtils.isNotEmpty(oldUser)){
                throw new DataConflictException(ResultCode.MOBILE_HAS_EXISTED);
            }
        }

        //判断 邮箱是否重复
        if(StringUtils.isNotEmpty(addUserVO.getEmail())){
            sysUserExample = new SysUserExample();
            sysUserExample.createCriteria()
                    .andEmailEqualTo(addUserVO.getEmail())
                    .andIsDeleteEqualTo(false);
            oldUser = sysUserMapper.selectByExample(sysUserExample);
            if(CollectionUtils.isNotEmpty(oldUser)){
                throw new DataConflictException(ResultCode.EMAIL_HAS_EXISTED);
            }
        }


        SysUser user = new SysUser();
        user.setUsername(addUserVO.getUsername());
        user.setPassword(addUserVO.getPassword());
        user.setEmail(addUserVO.getEmail());
        user.setMobile(addUserVO.getMobile());
        user.setCreateTime(new Date());
        //默认禁用
        user.setStatus((byte)0);
        user.setIsDelete(false);
        sysUserMapper.insertSelective(user);
        return PlatformResult.success();
    }

    @Override
    public Result<UserVO> getUserById(Long userId) {
        //校验
        SysUser user = sysUserMapper.selectByPrimaryKey(userId.intValue());
        if(null != user){
            UserVO userVO = assembyUserVO(user);
            return PlatformResult.success(userVO);
        }
        throw new DataNotFoundException(ResultCode.RESULE_DATA_NONE);
    }

    @Override
    public Result updateUser(EditUserVO editUserVO) {
        //校验
        SysUser user = sysUserMapper.selectByPrimaryKey(editUserVO.getUserId().intValue());
        if(null != user){
            SysUser newUser = new SysUser();
            newUser.setUserId(editUserVO.getUserId().intValue());
            newUser.setUsername(editUserVO.getUsername());
            newUser.setMobile(editUserVO.getEmail());
            newUser.setStatus(editUserVO.getStatus().byteValue());
            newUser.setMobile(editUserVO.getMobile());
            sysUserMapper.updateByPrimaryKeySelective(newUser);
            return PlatformResult.success();
        }
        return PlatformResult.failure(ResultCode.RESULE_DATA_NONE);
    }

    private UserVO assembyUserVO(SysUser user) {
        UserVO userVO = new UserVO();
        userVO.setUserId(Long.valueOf(user.getUserId()));
        userVO.setUsername(user.getUsername());
        userVO.setEmail(user.getEmail());
        userVO.setMobile(user.getMobile());
        userVO.setStatus(user.getStatus().intValue());
        userVO.setCreateTime(user.getCreateTime());
        return userVO;
    }
}
