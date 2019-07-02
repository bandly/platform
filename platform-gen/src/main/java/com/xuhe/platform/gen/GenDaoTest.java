package com.xuhe.platform.gen;


import java.io.File;

public class GenDaoTest {

    private static final String jdbUrl = "jdbc:mysql://localhost:3306/platform?useUnicode=true&characterEncoding=utf-8";

    public static void main(String[] args){
        String baseDir = System.getProperties().get("user.dir") + File.separator + "platform-gen";
        //String baseDir= "/Users/liqiang/test";
        GenContext genContext = new GenContext("com.mysql.cj.jdbc.Driver", jdbUrl
                , "root", "mysql123456", baseDir, "com.xuhe.platform.dal");
        System.out.println(baseDir);
        GenDAO genDAO = new GenDAO(genContext);
        genDAO.genBatch("sys_resource,sys_role,sys_role_resource,sys_user,sys_user_role", "");
    }
}
