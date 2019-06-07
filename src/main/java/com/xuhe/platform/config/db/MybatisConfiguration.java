package com.xuhe.platform.config.db;


import com.xuhe.platform.utils.LogUtil;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Configuration
@AutoConfigureAfter(DataSourceConfig.class)
@MapperScan(basePackages = {"com.xuhe.demo.dal"})
public class MybatisConfiguration {

    private static Logger log = LoggerFactory.getLogger(MybatisConfiguration.class);


    @Value("${mysql.datasource.mapperLocations}")
    private String mapperLocations;

    @Value("${mysql.datasource.configLocation}")
    private String configLocation;


    @Qualifier(value = "orderDataSource")
    @Autowired
    private DataSource orderDataSource;

    @Qualifier(value = "crmDataSource")
    @Autowired
    private DataSource crmDataSource;


    /**
     * 数据源路由器，把数据源都放入路由中
     * @return
     */
    @Bean(name = "roundRobinDataSourceProxy")
    public AbstractRoutingDataSource getRoutingDataSource(){
        Map<Object,Object> targetDataSources = new HashMap<>(2);
        targetDataSources.put(DataSourceType.order,orderDataSource);
        targetDataSources.put(DataSourceType.crm,crmDataSource);

        //路由类，寻找对应的数据源
        AbstractRoutingDataSource proxy = new AbstractRoutingDataSource() {
            @Override
            protected Object determineCurrentLookupKey() {
                return DataBaseContextHolder.getDataSourceType();
            }
        };
        proxy.setTargetDataSources(targetDataSources);
        proxy.setDefaultTargetDataSource(orderDataSource);
        return proxy;
    }



    @Primary
    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(AbstractRoutingDataSource roundRobinDataSourceProxy){
        log.info("------------------ sqlSessionFactory init -----------------------------------");
        try{
            SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
            sqlSessionFactoryBean.setDataSource(roundRobinDataSourceProxy);

            // 读取配置
            sqlSessionFactoryBean.setTypeAliasesPackage("com.xuhe.platform.dal.model");

            //设置mapper.xml 文件所在位置
            Resource[] resources = new PathMatchingResourcePatternResolver().getResources(mapperLocations);
            sqlSessionFactoryBean.setMapperLocations(resources);
            //设置mybatis-config.xml 配置文件位置
            sqlSessionFactoryBean.setConfigLocation(new DefaultResourceLoader().getResource(configLocation));

            return sqlSessionFactoryBean.getObject();
        }catch (IOException e){
            log.error(LogUtil.getErrorKVLogData(this.getClass()+" mybatis resolver mapper*xml is error",e),e);
            return null;
        }catch (Exception e){
            log.error(LogUtil.getErrorKVLogData(this.getClass()+ "mybatis sqlSessionFactoryBean create error",e),e);
            return null;
        }
    }


    @Bean(name = "sqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory){
        return new SqlSessionTemplate(sqlSessionFactory);
    }


    /**
     * 事务管理器
     * @param roundRobinDataSourceProxy
     * @return
     */
    @Bean(name = "annotationDrivenTransactionManager")
    public PlatformTransactionManager annotationDriventransactionManager(AbstractRoutingDataSource roundRobinDataSourceProxy){
        return new DataSourceTransactionManager(roundRobinDataSourceProxy);
    }

    /**
     * 事务模版
     * @param transactionManager
     * @return
     */
    @Bean
    public TransactionTemplate transactionTemplate(PlatformTransactionManager transactionManager){
        TransactionTemplate template = new TransactionTemplate(transactionManager);
        return template;
    }











}
