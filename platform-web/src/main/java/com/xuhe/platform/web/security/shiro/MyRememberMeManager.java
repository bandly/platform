package com.xuhe.platform.web.security.shiro;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.mgt.AbstractRememberMeManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.SubjectContext;

import java.util.concurrent.ConcurrentMap;

/**
 * @author liqiang
 * @date 2019/07/01
 * @description:  记住当前登录用户
 */
@Slf4j
public class MyRememberMeManager extends AbstractRememberMeManager {


    private static final ConcurrentMap<String, String> map = Maps.newConcurrentMap();

    /**
     * 删除之前的身份认证信息
     * @param subject
     */
    @Override
    protected void forgetIdentity(Subject subject) {

    }

    /**
     * 认证成功后 存储 认证信息 以便稍后验证通过
     * @param subject
     * @param serialized
     */
    @Override
    protected void rememberSerializedIdentity(Subject subject, byte[] serialized) {

    }

    @Override
    protected byte[] getRememberedSerializedIdentity(SubjectContext subjectContext) {
        return null;

    }

    /**
     * 应该是
     * @param subjectContext
     */
    @Override
    public void forgetIdentity(SubjectContext subjectContext) {

    }
}
