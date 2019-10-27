package com.xuhe.platform.gen;


import java.io.File;

public class GenDaoTest {

    private static final String jdbUrl = "jdbc:mysql://10.0.10.41:3306/smartauth?characterEncoding=UTF8";

    public static void main(String[] args){
        String baseDir = System.getProperties().get("user.dir") + File.separator + "platform-gen";
        //String baseDir= "/Users/liqiang/test";
        GenContext genContext = new GenContext("com.mysql.cj.jdbc.Driver", jdbUrl
                , "root", "shinemo123", baseDir, "com.shinemo.smartauth.dal");
        System.out.println(baseDir);
        GenDAO genDAO = new GenDAO(genContext);
        genDAO.genBatch("sa_org_deduction_detail,sa_org_recharge_record,sa_org_user_req_record,sa_outer_invoke_record", "");
    }
}
