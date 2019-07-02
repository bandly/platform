package com.xuhe.platform.service.admin.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.xuhe.platform.common.enums.ResultCode;
import com.xuhe.platform.common.exception.DataConflictException;
import com.xuhe.platform.common.exception.DataNotFoundException;
import com.xuhe.platform.common.exception.ParameterInvalidException;
import com.xuhe.platform.common.result.PlatformResult;
import com.xuhe.platform.common.result.Result;
import com.xuhe.platform.dal.SysUserDAO;
import com.xuhe.platform.dal.model.SysUserDO;
import com.xuhe.platform.dal.query.DalUserQuery;
import com.xuhe.platform.service.admin.SysUserService;
import com.xuhe.platform.service.dto.SysUserDTO;
import com.xuhe.platform.service.form.user.AddUserForm;
import com.xuhe.platform.service.form.user.EditUserForm;
import com.xuhe.platform.service.page.Page;
import com.xuhe.platform.service.query.UserQuery;
import com.xuhe.platform.service.vo.user.UserVO;
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
    private SysUserDAO sysUserDAO;

    @Override
    public Page<UserVO> findUserListPagination(UserQuery userQuery) {
        DalUserQuery dalUserQuery = new DalUserQuery();
        dalUserQuery.setAccount(userQuery.getAccount());
        dalUserQuery.setStartCreateTime(userQuery.getStartCreateTime());
        dalUserQuery.setEndCreateTime(userQuery.getEndCreateTime());
        dalUserQuery.setStatus(userQuery.getStatus());
        List<UserVO> resultVOList = Lists.newArrayList();
        //分页查询
        PageHelper.startPage(userQuery.getPageNum(), userQuery.getPageSize());
        List<SysUserDO> userList = sysUserDAO.findUserList(dalUserQuery);
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
    public Result addUser(AddUserForm addUserForm) {
        //校验 两次密码是否一致
        if(!Objects.equals(addUserForm.getPassword(),addUserForm.getRepassword())){
            throw new ParameterInvalidException(ResultCode.TWICE_PASSWORD_NO_SAME);
        }

        //用户名是否已存在

        SysUserDO existUser = sysUserDAO.selectByAccount(addUserForm.getAccount());

        if(null == existUser){
            throw new DataConflictException(ResultCode.USER_NAME_HAS_EXISTED);
        }
        //判断手机号码是否重复
        if(StringUtils.isNotEmpty(addUserForm.getMobile())){
            existUser = sysUserDAO.selectByMobile(addUserForm.getMobile());
            if(null == existUser){
                throw new DataConflictException(ResultCode.MOBILE_HAS_EXISTED);
            }
        }

        //判断 邮箱是否重复
        if(StringUtils.isNotEmpty(addUserForm.getEmail())){
            existUser = sysUserDAO.selectByEmail(addUserForm.getEmail());
            if(null == existUser){
                throw new DataConflictException(ResultCode.EMAIL_HAS_EXISTED);
            }
        }

        SysUserDO user = new SysUserDO();
        user.setAccount(addUserForm.getAccount());
        user.setPassword(addUserForm.getPassword());
        user.setEmail(addUserForm.getEmail());
        user.setMobile(addUserForm.getMobile());
        user.setCreateTime(new Date());
        //默认禁用
        user.setStatus(0);
        user.setIsDelete(false);
        sysUserDAO.insertSelective(user);
        return PlatformResult.success();
    }

    @Override
    public Result<UserVO> getUserById(Long userId) {
        //校验
        SysUserDO user = sysUserDAO.selectByPrimaryKey(userId);
        if(null != user){
            UserVO userVO = assembyUserVO(user);
            return PlatformResult.success(userVO);
        }
        throw new DataNotFoundException(ResultCode.RESULE_DATA_NONE);
    }

    @Override
    public Result updateUser(EditUserForm editUserForm) {
        //校验
        SysUserDO user = sysUserDAO.selectByPrimaryKey(editUserForm.getUserId());
        if(null != user){
            SysUserDO newUser = new SysUserDO();
            newUser.setUserId(editUserForm.getUserId().intValue());
            newUser.setAccount(editUserForm.getAccount());
            newUser.setMobile(editUserForm.getEmail());
            newUser.setStatus(editUserForm.getStatus());
            newUser.setMobile(editUserForm.getMobile());
            sysUserDAO.updateByPrimaryKeySelective(newUser);
            return PlatformResult.success();
        }
        return PlatformResult.failure(ResultCode.RESULE_DATA_NONE);
    }

    @Override
    public Result deleteUser(Long userId) {
        SysUserDO user = sysUserDAO.selectByPrimaryKey(userId);
        if(null != user){
            SysUserDO newUser = new SysUserDO();
            newUser.setIsDelete(true);
            newUser.setUserId(userId.intValue());
            sysUserDAO.updateByPrimaryKeySelective(newUser);
            return PlatformResult.success();
        }
        throw new DataNotFoundException(ResultCode.RESULE_DATA_NONE);
    }

    @Override
    public SysUserDTO getUseByAccount(String account) {
        SysUserDO user = sysUserDAO.selectByAccount(account);
        if(null != user){
            SysUserDTO sysUserDTO = new SysUserDTO();
            sysUserDTO.setUserId(user.getUserId().longValue());
            sysUserDTO.setAccount(user.getAccount());
            sysUserDTO.setPassword(user.getPassword());
            sysUserDTO.setEmail(user.getEmail());
            sysUserDTO.setStatus(user.getStatus());
            return sysUserDTO;
        }
        return null;
    }

    private UserVO assembyUserVO(SysUserDO user) {
        UserVO userVO = new UserVO();
        userVO.setUserId(Long.valueOf(user.getUserId()));
        userVO.setAccount(user.getAccount());
        userVO.setEmail(user.getEmail());
        userVO.setMobile(user.getMobile());
        userVO.setStatus(user.getStatus().intValue());
        userVO.setCreateTime(user.getCreateTime());
        return userVO;
    }
}
