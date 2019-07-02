package com.xuhe.platform.web.security.shiro;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.SubjectContext;
import org.apache.shiro.web.mgt.DefaultWebSubjectFactory;

/**
 * @author liqiang
 * @date 2019/07/01
 * @description:
 */
public class StatelessDefaultSubjectFactory extends DefaultWebSubjectFactory {


    @Override
    public Subject createSubject(SubjectContext context) {
        //不创建session
        context.setSessionCreationEnabled(false);
        return super.createSubject(context);
    }
}
