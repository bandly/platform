package com.xuhe.platform.web.config.db;

/**
 * Created by liqiang on 2019-05-18.
 */
public enum DataSourceType {

    order("order","订单库"),
    crm("crm","会员库");


    private final String type;

    private final String name;

    DataSourceType(String type,String name){
        this.type = type;
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}
