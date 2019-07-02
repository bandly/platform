package com.xuhe.platform.web.config;


import com.xuhe.platform.web.interceptor.LoggerInterceptor;
import com.xuhe.platform.web.interceptor.ResponseResultInterceptor;
import com.xuhe.platform.web.interceptor.csrf.CsrfInterceptor;
import com.xuhe.platform.web.security.SentinelFilter;
import com.xuhe.platform.web.security.shiro.JwtFilter;
import com.xuhe.platform.web.security.xss.XssFilter;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


import javax.annotation.Resource;
import javax.servlet.Filter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author liqiang
 * @date 2019/05/23
 * @description:
 */
@ComponentScan(basePackages = {"com.xuhe.platform.web","com.xuhe.platform.service"})
@Configuration
public class WebConfig implements WebMvcConfigurer {


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //增加静态资源处理
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
    }

   @Resource
   private Environment environment;


    /**
     * 拦截器配置
     * @param registry
     */
    @Override
     public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(loggerInterceptor()).addPathPatterns("/**");
        registry.addInterceptor(responseResultInterceptor());
        registry.addInterceptor(csrfInterceptor());
     }

    /**
     * 字符编码过滤
     * @return
     */
/*    @Bean
    public FilterRegistrationBean characterFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(characterEncodingFilter());
        registration.addUrlPatterns("/*");
        registration.setName("characterEncodingFilter");
        registration.setOrder(0);
        return registration;
    }*/


    @Bean
    public FilterRegistrationBean sentinelFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(sentinelFilter());
        registration.addUrlPatterns("/*");
        registration.setName("sentinelFilter");
        registration.setOrder(1);
        return registration;
    }

    /**
     * xss 过滤器配置
     * @return
     */
    @Bean
    public FilterRegistrationBean xssFilterRegistration(){
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(xssFilter());
        registrationBean.addUrlPatterns("/*");
        registrationBean.setName("xssFilter");
        registrationBean.setOrder(2);
        return registrationBean;
    }




/*    @Bean
    public Filter characterEncodingFilter() {
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);
        return characterEncodingFilter;
    }*/



    /**
     * 请求日志拦截
     * @return
     */
    @Bean
    public LoggerInterceptor loggerInterceptor(){
        return new LoggerInterceptor();
    }

    /**
     * 接口统一响应格式拦截器
     * @return
     */
    @Bean
    public ResponseResultInterceptor responseResultInterceptor() {
        return new ResponseResultInterceptor();
    }

    /**
     * csrf 防跨域请求伪造 拦截器
     * @return
     */
    @Bean
    public CsrfInterceptor csrfInterceptor() {
        return new CsrfInterceptor(environment);
    }


    @Bean
    public Filter sentinelFilter() {
        return new SentinelFilter();
    }

    /**
     * xss 防脚本攻击过滤器
     * @return
     */
     @Bean
     public Filter xssFilter() {
        return new XssFilter();
     }




}
