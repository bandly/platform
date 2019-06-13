package com.xuhe.platform.config;


import com.xuhe.platform.common.web.interceptor.ResponseResultInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

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


    @Bean
    public ResponseResultInterceptor responseResultInterceptor() {
        return new ResponseResultInterceptor();
    }

     @Override
     public void addInterceptors(InterceptorRegistry registry){
          registry.addInterceptor(responseResultInterceptor());
     }

}
