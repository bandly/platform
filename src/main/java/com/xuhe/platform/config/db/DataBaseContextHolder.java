package com.xuhe.platform.config.db;

/**
 * Created by liqiang on 2019-05-18.
 */
public class DataBaseContextHolder {

    /**
     * 用来存储当前线程的数据源类型
     */
    private static final ThreadLocal<DataSourceType>  contextHolder = new ThreadLocal<>();


    public static DataSourceType getDataSourceType() {
        return contextHolder.get();
    }

    public static void setDataSourceType(DataSourceType dataSourceType) {
        contextHolder.set(dataSourceType);
    }
}
