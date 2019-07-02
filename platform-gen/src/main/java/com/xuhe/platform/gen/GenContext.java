package com.xuhe.platform.gen;

import com.xuhe.platform.gen.helper.CamelCaseUtils;

import java.io.File;

/**
 * @author liqiang
 * @date 2019/06/24
 * @description:
 */
public class GenContext {

    private String sqlDriver;
    private String sqlUser;
    private String sqlPassword;
    private String sqlUrl;


    private String tableNamePrefix;
    private String tableName;


    private String doName;
    private String doNameLower;
    private String doFile;
    private String doClassName;

    private String bizFile;
    private String bizImplFile;

    private String daoFile;
    private String mybatisXmlFile;
    private String sqlMapFile;


    private String formFile;
    private String editFormFile;
    private String formXMLFile;

    private String baseVMDir;
    /**
     * 生成代码输出路径
     */
    private String baseDir;

    /**
     * 代码包名
     */
    private String parentPackageName;


    public GenContext(String sqlDriver, String sqlUrl, String sqlUser, String sqlPassword, String baseDir
            , String parentPackageName) {
        this.sqlDriver = sqlDriver;
        this.sqlUser = sqlUser;
        this.sqlUrl = sqlUrl;
        this.sqlPassword = sqlPassword;
        this.parentPackageName = parentPackageName;
        this.baseDir = baseDir;
    }

    public GenContext(String baseDir, String clazz, String parentPackageName) throws ClassNotFoundException {
        this(baseDir, Class.forName(clazz), parentPackageName);
    }

    public String getSqlDriver() {
        return sqlDriver;
    }

    public void setSqlDriver(String sqlDriver) {
        this.sqlDriver = sqlDriver;
    }

    public String getSqlUser() {
        return sqlUser;
    }

    public void setSqlUser(String sqlUser) {
        this.sqlUser = sqlUser;
    }

    public String getSqlPassword() {
        return sqlPassword;
    }

    public void setSqlPassword(String sqlPassword) {
        this.sqlPassword = sqlPassword;
    }

    public String getSqlUrl() {
        return sqlUrl;
    }

    public void setSqlUrl(String sqlUrl) {
        this.sqlUrl = sqlUrl;
    }

    public String getTableNamePrefix() {
        return tableNamePrefix;
    }

    public void setTableNamePrefix(String tableNamePrefix) {
        this.tableNamePrefix = tableNamePrefix;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getDoName() {
        return doName;
    }

    public void setDoName(String doName) {
        this.doName = doName;
    }

    public String getDoNameLower() {
        return doNameLower;
    }

    public void setDoNameLower(String doNameLower) {
        this.doNameLower = doNameLower;
    }

    public String getDoFile() {
        return doFile;
    }

    public void setDoFile(String doFile) {
        this.doFile = doFile;
    }

    public String getDoClassName() {
        return doClassName;
    }

    public void setDoClassName(String doClassName) {
        this.doClassName = doClassName;
    }

    public String getBizFile() {
        return bizFile;
    }

    public void setBizFile(String bizFile) {
        this.bizFile = bizFile;
    }

    public String getBizImplFile() {
        return bizImplFile;
    }

    public void setBizImplFile(String bizImplFile) {
        this.bizImplFile = bizImplFile;
    }

    public String getDaoFile() {
        return daoFile;
    }

    public void setDaoFile(String daoFile) {
        this.daoFile = daoFile;
    }

    public String getMybatisXmlFile() {
        return mybatisXmlFile;
    }

    public void setMybatisXmlFile(String mybatisXmlFile) {
        this.mybatisXmlFile = mybatisXmlFile;
    }

    public String getSqlMapFile() {
        return sqlMapFile;
    }

    public void setSqlMapFile(String sqlMapFile) {
        this.sqlMapFile = sqlMapFile;
    }

    public String getFormFile() {
        return formFile;
    }

    public void setFormFile(String formFile) {
        this.formFile = formFile;
    }

    public String getEditFormFile() {
        return editFormFile;
    }

    public void setEditFormFile(String editFormFile) {
        this.editFormFile = editFormFile;
    }

    public String getFormXMLFile() {
        return formXMLFile;
    }

    public void setFormXMLFile(String formXMLFile) {
        this.formXMLFile = formXMLFile;
    }

    public String getBaseVMDir() {
        return baseVMDir;
    }

    public void setBaseVMDir(String baseVMDir) {
        this.baseVMDir = baseVMDir;
    }

    public String getBaseDir() {
        return baseDir;
    }

    public void setBaseDir(String baseDir) {
        this.baseDir = baseDir;
    }

    public String getParentPackageName() {
        return parentPackageName;
    }

    public void setParentPackageName(String parentPackageName) {
        this.parentPackageName = parentPackageName;
    }

    public GenContext(String baseDir, Class clazz, String parentPackageName){
        this.baseDir = baseDir;
        doName = clazz.getSimpleName();
        this.doClassName = clazz.getName();
        this.parentPackageName = parentPackageName;
        //通过DO 名称转换 成数据库表名  去掉 DO
        this.tableName = CamelCaseUtils.toUnderlineName(doName.substring(0,doName.length() - 2));
        //代码输出路径
        String packageDir = parentPackageName.replaceAll("\\.", File.separator + File.separator);

        //doName 去掉 DO
        doName = doName.substring(0,doName.length() - 2);
        //首字母转换为小写
        doNameLower = doName.substring(0,1).toLowerCase() + doName.substring(1);


        bizFile = baseDir + File.separator + GenConstants.javaDir + File.separator + packageDir
                + File.separator + doName + "Biz.java";

        bizFile = baseDir + File.separator + GenConstants.javaDir + File.separator + packageDir
                + File.separator + "impl" + File.separator + doName + "BizImpl.java";

        daoFile = baseDir + File.separator + GenConstants.javaDir + File.separator + packageDir
                + File.separator + "dao" + File.separator + doName + "DAO.java";


        mybatisXmlFile = baseDir + File.separator + GenConstants.resourceDir + File.separator
                + "mybatis" + File.separator + GenConstants.mybatisConfigXML;


        formFile = baseDir + File.separator + GenConstants.javaDir + File.separator + packageDir
                + File.separator + "form" + File.separator + doName + "Form.java";

        editFormFile = baseDir + File.separator + GenConstants.javaDir + File.separator + packageDir
                + File.separator + "form" + File.separator + doName + "EditForm.java";

        doFile = baseDir + File.separator + GenConstants.javaDir + File.separator + packageDir
                + File.separator + "model" + File.separator + doName + "DO.java";


        sqlMapFile = baseDir + File.separator + GenConstants.resourceDir + File.separator + "mapper" + File.separator;


    }



}
