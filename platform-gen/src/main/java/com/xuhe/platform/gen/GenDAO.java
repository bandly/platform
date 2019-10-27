package com.xuhe.platform.gen;

import com.xuhe.platform.gen.helper.CamelCaseUtils;
import com.xuhe.platform.gen.helper.FreemakerHelper;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author liqiang
 * @date 2019/06/24
 * @description:
 */
public class GenDAO {

    public static final String COMMON_COLUMN_STR = "";

    // jdbc result set metadata collumn name
    public static final String RSMD_COLUMN_NAME = "rsmdColumnName";
    public static final String RSMD_COLUMN_CLASS_NAME = "columnClassName";
    public static final String RSMD_COLUMN_TYPE_NAME = "columnTypeName";
    public static final String RSMD_COLUMN_PRECISION = "Precision";
    public static final String RSMD_COLUMN_SCALE = "Scale";


    // velocity param
    public static final String VP_LIST = "list";
    //类名 不包含 后缀如 DAO DO
    public static final String VP_CLASS_NAME = "className";
    public static final String VP_MAIN_PACKAGE = "mainPackage";
    //DO 的包名
    public static final String VP_DO_PACKAGE = "doPackage";
    public static final String VP_DAO_PACKAGE = "daoPackage";
    public static final String VP_JAVA_TYPE = "javaType";
    public static final String VP_PROP_NAME = "propName";
    public static final String VP_GET_METHOD = "getMethod";
    public static final String VP_SET_METHOD = "setMethod";


    public static final String VP_COLUMN_NAME = "columnName";
    public static final String VP_TABLE_NAME = "tableName";
    public static final String VP_JDBC_TYPE = "jdbcType";
    public static final String VP_COLS = "cols";
    public static final String VP_COL_ID = "colId";
    public static final String VP_COLS_WITHOUT_COMMON_COLUMNS = "colsWithoutCommColumns";
    public static final String VP_SERIAL_VERSION_UID = "serialVersionUID";
    public static final String VP_SERIAL_VERSION_UID2 = "serialVersionUID2";


    private static final String JAVA_SQL_TIMESTAMP = "java.sql.Timestamp";
    private static final String JAVA_SQL = "java.sql.";
    private static final String JAVA_LANG_STRING = "java.lang.String";
    private static final String DECIMAL = "DECIMAL";
    private static final String BIGINT = "BIGINT";
    private static final String INT = "INT";
    private static final String TINYINT = "TINYINT";
    private static final String SMALLINT = "SMALLINT";
    private static final String NUMBER = "NUMBER";
    private static final String BIT = "BIT";
    private static final String ID = "ID";
    private static final String DATETIME = "DATETIME";
    private static final String GMT_CREATE = "GMT_CREATE";
    private static final String GMT_UPDATE = "GMT_UPDATE";
    private static final String VERSION = "VERSION";

    //分库分表 表后缀regex
    public static final String SHARDING_SUFIX_REG = "_[\\d]{4}";

    private String doVmName = "do.ftl";

    private String daoVmName = "dao.ftl";

    private String sqlmapVmName = "sqlmap.ftl";



    private GenContext genContext;

    public GenDAO(GenContext genContext){
        this.genContext = genContext;
    }


    private Connection getConnection() {
        try {
            Class.forName(genContext.getSqlDriver());
            String url = genContext.getSqlUrl();
            String user = genContext.getSqlUser();
            String psw = genContext.getSqlPassword();
            return DriverManager.getConnection(url, user, psw);
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    /**
     * 去掉前缀
     * @param str
     * @param prefix
     * @return
     */
    private String cutPrefix(String str,String prefix){
        if(str == null || prefix == null || prefix.trim().length() ==0){
            return str;
        }
        if(str.startsWith(prefix)){
            return str.substring(prefix.length());
        }
        return str;
    }

    /**
     * 批量生成
     * @param tableName
     * @param tablePrefix
     */
    public void genBatch(String tableName,String tablePrefix)  {
        if(tableName == null){
            return;
        }
        String[] split = tableName.split(",");
        for(String tab : split){
            gen(tab,tablePrefix);
        }
    }

    public void gen(String tableName,String tablePrefix){
        String parentPackageName = genContext.getParentPackageName();
        genContext.setTableName(cutPrefix(tableName,tablePrefix));
        genContext.setTableNamePrefix(tablePrefix);


        genContext.setDoName(getClassName(tableName));

        genContext.setDoNameLower(genContext.getDoName().substring(0,1).toLowerCase() + genContext.getDoName().substring(1));

        String packageDir = parentPackageName.replaceAll("\\.", Matcher.quoteReplacement(File.separator));

        String doFile = genContext.getBaseDir() +  File.separator + GenConstants.javaDir + File.separator + packageDir
                + File.separator + "model" + File.separator + genContext.getDoName() + "DO.java";

        String daoFile = genContext.getBaseDir() + File.separator + GenConstants.javaDir + File.separator + packageDir
                + File.separator + genContext.getDoName() + "DAO.java";

        String sqlMapFile = genContext.getBaseDir() + File.separator + GenConstants.resourceDir + File.separator
                + "mybatis" + File.separator + genContext.getDoNameLower() + "DAO.xml";

        genContext.setDoFile(doFile);

        genContext.setDaoFile(daoFile);

        genContext.setSqlMapFile(sqlMapFile);

        Map<String,Object> params = new HashMap<String,Object>();


        params.put("parentPackageName",parentPackageName);
        params.put("doName",genContext.getDoName());

        params.put("doNameLower",genContext.getDoNameLower());
        params.put("tableNamePrefix",tablePrefix);


        params.put(VP_MAIN_PACKAGE,parentPackageName);

        params.put(VP_DO_PACKAGE, parentPackageName+".model");

        //类名 不包含 后缀如 DO
        params.put(VP_CLASS_NAME, genContext.getDoName());

        List<Map<String, String>> colInfoList = getColInfoList(genContext.getTableNamePrefix() + genContext.getTableName());
        List<Map<String, String>> paramList = makeParamList(colInfoList);

        //DO 类里边的属性
        params.put(VP_LIST, paramList);

        try {
            String render = FreemakerHelper.render(doVmName,params);
            String fileName = genContext.getDoFile();
            File file = new File(fileName);
            boolean rs = file.getParentFile().mkdirs();
            if(file.exists()){
                fileName += ".c";
            }
            file = new File(fileName);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(render.getBytes("utf-8"));
            fileOutputStream.close();
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException();
        }

        String vpTableName = genContext.getTableName().toLowerCase();
        boolean isShareding = Pattern.compile(SHARDING_SUFIX_REG).matcher(genContext.getTableName()).find();
        if(isShareding){
            vpTableName += "_$tabnum$";
        }

        params.put(VP_TABLE_NAME, vpTableName);
        params.put(VP_SERIAL_VERSION_UID, "" + (long) (Math.random() * 1000000000000000000L));
        params.put(VP_SERIAL_VERSION_UID2, "" + (long) (Math.random() * 1000000000000000000L));

        List<Map<String, String>> sqlmapParamList = getSqlmapParamList(paramList);
        // 获取字段名不包含 id
        params.put(VP_LIST, sqlmapParamList);

        params.put(VP_COL_ID,getColId(sqlmapParamList));

        params.put(VP_DAO_PACKAGE, parentPackageName+"");

        //字段名 按逗号分割
        String colsWithoutCommColumns = getColsStr(sqlmapParamList);

        params.put(VP_COLS_WITHOUT_COMMON_COLUMNS, colsWithoutCommColumns);
        String cols = COMMON_COLUMN_STR + colsWithoutCommColumns;
        params.put(VP_COLS, cols);

        try {
            String render = FreemakerHelper.render(daoVmName, params);
            String fileName = genContext.getDaoFile();
            File file = new File(fileName);
            file.getParentFile().mkdirs();
            if (file.exists()) {
                fileName += ".c";
            }
            file = new File(fileName);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(render.getBytes("UTF-8"));
            fileOutputStream.close();

            render = FreemakerHelper.render(sqlmapVmName, params);
            fileName = genContext.getSqlMapFile();
            file = new File(fileName);
            file.getParentFile().mkdirs();
            if (file.exists()) {
                fileName += ".c";
            }
            file = new File(fileName);
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(render.getBytes("UTF-8"));
            fileOutputStream.close();
        }catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }


    public static String getClassName(String tableName) {
        return CamelCaseUtils.toCapitalizeCamelCase(tableName);
    }


    /**
     * 根据表名查询 表字段
     * @param table
     * @return
     */
    public List<Map<String, String>> getColInfoList(String table)  {
        Connection cn = getConnection();
        try {
            return getColInfoList(cn, table);
        } finally {
            try {
                cn.close();
            } catch (Exception e2) {
            }
        }
    }

    /**
     * 连接数据库 查询表字段
     * @param cn
     * @param table
     * @return
     */
    public static List<Map<String, String>> getColInfoList(Connection cn, String table) {
        String sql = "select * from " + table + " where 1>2";
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = cn.createStatement();
            rs = stmt.executeQuery(sql);
            // 获取结果集元数据信息
            ResultSetMetaData rsmd = rs.getMetaData();
            int num = rsmd.getColumnCount();
            Map<String, String> map = null;
            List<Map<String, String>> list = new ArrayList<Map<String, String>>();
            for (int i = 1; i <= num; i++) {
                map = new HashMap<String, String>();
                //字段名称
                map.put(RSMD_COLUMN_NAME, rsmd.getColumnName(i));
                //java 类型名称
                map.put(RSMD_COLUMN_CLASS_NAME, rsmd.getColumnClassName(i));
                //数据库类型名称
                map.put(RSMD_COLUMN_TYPE_NAME, rsmd.getColumnTypeName(i));
                //字段类型长度大小 精密度
                map.put(RSMD_COLUMN_PRECISION, rsmd.getPrecision(i) + "");
                //获取小数点位数
                map.put(RSMD_COLUMN_SCALE, rsmd.getScale(i) + "");
                list.add(map);
            }
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e + ",table=" + table, e);
        } finally {
            try {
                stmt.close();
            } catch (Exception e2) {
            }
            try {
                rs.close();
            } catch (Exception e2) {
            }
        }
    }

    /**
     * 获取参数列表
     *
     * @param colInfoList
     * @return
     * @throws Exception
     */
    public List<Map<String, String>> makeParamList(List<Map<String, String>> colInfoList)  {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        Map<String, String> map = null;
        int num = colInfoList.size();
        Map<String, String> mapNew = null;
        for (int i = 0; i < num; i++) {
            map = colInfoList.get(i);
            mapNew = new HashMap<String, String>();
            String columnName = (String) map.get(RSMD_COLUMN_NAME);
            String columnClassName = (String) map.get(RSMD_COLUMN_CLASS_NAME);
            String columnTypeName = (String) map.get(RSMD_COLUMN_TYPE_NAME);
            String scaleStr = (String) map.get(RSMD_COLUMN_SCALE);
            int scale = Integer.valueOf(scaleStr);
            String javaType = getJavaType(columnClassName, columnTypeName, scale);
            String jdbcType = getJdbcType(columnClassName, columnTypeName);
            String propName = getPropName(columnName);
            //get set 方法
            String setMethod = getSetMethod(propName);
            String getMethod = getGetMethod(propName);
            mapNew.put(VP_COLUMN_NAME, columnName.toLowerCase());
            mapNew.put(VP_PROP_NAME, propName);
            mapNew.put(VP_JAVA_TYPE, javaType);
            mapNew.put(VP_JDBC_TYPE, jdbcType);
            mapNew.put(VP_GET_METHOD, getMethod);
            mapNew.put(VP_SET_METHOD, setMethod);
            list.add(mapNew);
        }
        return list;
    }

    public String getJavaType(String columnClassName,String columnTypeName,int scale){
        if (JAVA_SQL_TIMESTAMP.equals(columnClassName)) {
            return "Date";
        }
        if (JAVA_LANG_STRING.equals(columnClassName)) {
            return "String";
        }
        if (DECIMAL.equals(columnTypeName) && scale < 1) {
            return "Integer";
        }
        if (DECIMAL.equals(columnTypeName) && scale > 0) {
            return "Double";
        }
        if (BIGINT.startsWith(columnTypeName)) {
            return "Long";
        }
        if (INT.startsWith(columnTypeName)) {
            return "Integer";
        }
        if (TINYINT.startsWith(columnTypeName)) {
            return "Integer";
        }
        if (SMALLINT.startsWith(columnTypeName)) {
            return "Integer";
        }
        if (BIT.startsWith(columnTypeName)) {
            return "Boolean";
        }
        return columnClassName;
    }


    public String getJdbcType(String columnClassName, String columnTypeName) {
        if (JAVA_LANG_STRING.equals(columnClassName)) {
            return "VARCHAR";
        }
        if (INT.equalsIgnoreCase(columnTypeName)) {
            return "INTEGER";
        }
        if (columnClassName.startsWith(JAVA_SQL)) {
            return "TIMESTAMP";
        }
        if(DATETIME.equalsIgnoreCase(columnTypeName)){
            return "TIMESTAMP";
        }
        if (NUMBER.startsWith(columnTypeName)) {
            return "DECIMAL";
        }
        return columnTypeName;
    }

    /**
     * 数据库字段 to DO 属性 role_id to roleId
     * @param columnName
     * @return
     */
    public String getPropName(String columnName) {
        String t = columnName.toLowerCase();
        String[] arr = t.split("_");
        int num = arr.length;
        String s = "";
        for (int i = 0; i < num; i++) {
            if (i > 0) {
                s = s + makeFisrtCharUpperCase(arr[i]);
            } else {
                s = s + arr[i];
            }
        }
        return s;
    }

    public String getSetMethod(String propName) {
        return "set" + makeFisrtCharUpperCase(propName);
    }

    public String getGetMethod(String propName) {
        return "get" + makeFisrtCharUpperCase(propName);
    }


    public static String makeFisrtCharUpperCase(String str) {
        if (StringUtils.isEmpty(str)) {
            throw new RuntimeException("str is blank");
        }
        String firstCharStr = str.charAt(0) + "";
        return firstCharStr.toUpperCase() + str.substring(1);
    }
    public static List<Map<String, String>> getSqlmapParamList(List<Map<String, String>> paramList) {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        Map<String, String> tmp = null;
        Map<String, String> map = null;
        int num = paramList.size();
        for (int i = 0; i < num; i++) {
            tmp = paramList.get(i);
            String columnName = (String) tmp.get(VP_COLUMN_NAME);
/*            if (ID.equalsIgnoreCase(columnName)) {
                continue;
            }*/
           /* if (GMT_CREATE.equalsIgnoreCase(columnName)) {
                continue;
            }*/
         /*   if (GMT_UPDATE.equalsIgnoreCase(columnName)) {
                continue;
            }*/
        /*    if (VERSION.equalsIgnoreCase(columnName)) {
                continue;
            }*/
            map = new HashMap<String, String>();
            map.putAll(tmp);
            list.add(map);
        }
        return list;
    }

    public static String getColId(List<Map<String, String>> list) {
        String s = "";
        int num = list.size();
        Map<String, String> map = null;
        String colName = null;
        for (int i = 0; i < num; i++) {
            map = list.get(i);
            colName = (String) map.get(VP_PROP_NAME);
            if( colName.indexOf("Id")>-1 ){
                return colName;
            }
        }
        return s;
    }

    public static String getColsStr(List<Map<String, String>> list) {
        String s = "";
        int num = list.size();
        Map<String, String> map = null;
        String colName = null;
        for (int i = 1; i < num; i++) {
            map = list.get(i);
            colName = (String) map.get(VP_COLUMN_NAME);
            if (i > 1) {
                s = s + ",";
            }
            s = s + colName;
        }
        return s;
    }


}
