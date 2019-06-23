package com.xuhe.platform.config;


import com.xuhe.platform.common.web.interceptor.ResponseResultInterceptor;
import com.xuhe.platform.common.web.interceptor.csrf.CsrfInterceptor;
import com.xuhe.platform.common.web.security.xss.XssFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import javax.servlet.Filter;
import javax.servlet.FilterRegistration;
import java.util.Arrays;

/**
 * @author liqiang
 * @date 2019/05/23
 * @description:
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

 /*   @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/");
    }*/

   @Resource
   private Environment environment;


    @Bean
    public ResponseResultInterceptor responseResultInterceptor() {
        return new ResponseResultInterceptor();
    }

     @Override
     public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(responseResultInterceptor());
        //csrfInterceptor.setExcludePathPatterns(Arrays.asList("/rest/api/.*/internal\\."));
        registry.addInterceptor(csrfInterceptor());
     }


     @Bean
     public FilterRegistrationBean xssFilterRegistration(){
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(xssFilter());
        registrationBean.addUrlPatterns("/*");
        registrationBean.setName("xssFilter");
        registrationBean.setOrder(4);
        return registrationBean;

     }

     @Bean
     public Filter xssFilter() {
        return new XssFilter();
     }


    @Bean
    public CsrfInterceptor csrfInterceptor() {
        return new CsrfInterceptor(environment);
    }


}
