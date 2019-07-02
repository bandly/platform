package com.xuhe.platform.web.config;

import com.google.common.collect.Lists;
import com.xuhe.platform.web.security.shiro.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author liqiang
 * @date 2019/06/27
 * @description: 权限配置
 */
@Configuration
public class ShiroConfig {


    /**
     *  用户名密码 shiro 验证
     * @return
     */
    @Bean
    public CustomUserRealm customUserRealm(){
        CustomUserRealm realm = new CustomUserRealm();
        realm.setCredentialsMatcher(new CustomCredentialsMatcher());
       return realm;
    }

    /**
     * jwt shiro 验证
     * @return
     */
    @Bean
    public JwtUserRealm jwtUserRealm(){
        return new JwtUserRealm();
    }


    /**
     * 权限管理 配置的主要是realm 的管理认证
     * @return
     */
    @Bean
    public SecurityManager securityManager(){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        Collection<Realm> list = Lists.newArrayList();
        list.add(jwtUserRealm());
        list.add(customUserRealm());
        securityManager.setRealms(list);

        //关闭shiro自带的session
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        securityManager.setSubjectDAO(subjectDAO);




        MyAuthenticator myAuthenticator = new MyAuthenticator();
        myAuthenticator.setRealms(list);
        securityManager.setAuthenticator(myAuthenticator);



        //绑定实例
        SecurityUtils.setSecurityManager(securityManager);

        securityManager.setSubjectFactory(new StatelessDefaultSubjectFactory());

        //securityManager.setSessionManager(new DefaultWebSessionManager());
        //securityManager.setRememberMeManager(rememberMeManager());

        return securityManager;
    }


    @Bean
    public RememberMeManager rememberMeManager(){
        return new MyRememberMeManager();
    }

    /**
     *  开启Shiro的注解(如@RequiresRoles,@RequiresPermissions),需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证
     * 配置以下两个bean(DefaultAdvisorAutoProxyCreator和AuthorizationAttributeSourceAdvisor)即可实现此功能
     * @return
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    /**
     * 开启aop注解支持
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager());
        return authorizationAttributeSourceAdvisor;
    }



    @Bean
    public ShiroFilterFactoryBean shiroFilter() {
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(securityManager());

        // 添加jwt过滤器
        Map<String, Filter> filterMap = new HashMap<>();
        filterMap.put("jwt", jwtFilter());
        shiroFilter.setFilters(filterMap);

        //拦截器
        Map<String,String> filterRuleMap = new LinkedHashMap<>();
        filterRuleMap.put("/logout", "logout");
        filterRuleMap.put("/sys/**", "jwt");
        shiroFilter.setFilterChainDefinitionMap(filterRuleMap);
        return shiroFilter;
    }

/**
     * jwt Token 过滤器配置
     * @return
     */

    @Bean
    public JwtFilter jwtFilter(){
        return new JwtFilter("/sys/login,/captcha.jpg");
    }

}
