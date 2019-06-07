package com.xuhe.platform.config.db;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Value("${mysql.datasource.type}")
    private Class<? extends DataSource> dataSourceType;

    @Bean(name = "orderDataSource")
    @ConfigurationProperties(prefix = "mysql.datasource.order")
    public DataSource orderDataSource(){
        return DataSourceBuilder.create().type(dataSourceType).build();
    }

    @Bean(name = "crmDataSource")
    @ConfigurationProperties(prefix = "mysql.datasource.crm")
    public  DataSource crmDataSource(){
        return DataSourceBuilder.create().type(dataSourceType).build();
    }



















}
