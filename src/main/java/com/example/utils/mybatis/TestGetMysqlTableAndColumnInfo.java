package com.example.utils.mybatis;

import com.example.enums.MysqlColumnTypeEnum;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @author zfl
 * @create 2022/1/28 10:12
 * @description
 */
public class TestGetMysqlTableAndColumnInfo {

    /**
     * mysql驱动类
     */
    private final static String MYSQL_DRIVER_CLASS = "com.mysql.jdbc.Driver";
    /**
     * mysql用户名
     */
    private final static String MYSQL_USER = "root";
    /**
     * mysql连接密码
     */
    private final static String MYSQL_PASSWORD = "123456";
    /**
     * mysql连接语句
     */
    private final static String MYSQL_CONNECT_URL = "jdbc:mysql://%s:%s/%s?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT";
    /**
     * mysql获取表名的字段
     */
    private final static String[] MYSQL_META_TABLE_KEY_ARRAY = {"TABLE"};
    /**
     * mysql获取表名的key
     */
    private final static String MYSQL_META_TABLE_NAME_KEY = "TABLE_NAME";
    /**
     * mysql表生成实体模型的文件夹位置
     */
    private final static String MYSQL_TABLE_POJO_LOCATION = "src/main/java/com/example/pojo";
    /**
     * mysql表生成实体模型的文件夹路径间隔符
     */
    private final static String MYSQL_TABLE_POJO_LOCATION_INTERVAL_LETTER = "/";
    /**
     * mysql表生成实体模型的java文件后缀
     */
    private final static String MYSQL_TABLE_POJO_CLASS_SUFFIX = ".java";
    /**
     * mysql表名下划线
     */
    private final static String MYSQL_SPECIAL_INTERVAL_LETTER = "_";
    /**
     * 空字符串
     */
    private final static String EMPTY_STRING = "";
    /**
     * 需要忽略的表名集合
     */
    private final static List<String> MYSQL_IGNORE_CREATE_TABLE_NAME_ARRAY = Arrays.asList("sys_config","show_log");
    /**
     * 创建好的java文件名
     */
    private static String MYSQL_POJO_JAVA_CLASS_FILE_NAME;
    /**
     * java的类名
     */
    private static String MYSQL_POJO_JAVA_CLASS_NAME;

    private static Connection connection;

    private static String simpleDateStr;

    private static ResultSet resultSet = null;

    public static void main(String[] args) {
        simpleDateStr = new SimpleDateFormat().format(new Date());
        try {
            initConnect("localhost", "3306", "fl");
            //获取数据库信息
            DatabaseMetaData metaData = connection.getMetaData();
            //获取所有的表
            ResultSet tables = metaData.getTables(null, null, null, MYSQL_META_TABLE_KEY_ARRAY);
            //遍历
            while (tables.next()){
                //获取表名
                String tableName = tables.getString(MYSQL_META_TABLE_NAME_KEY);
                if(createEntityByMysqlTableName(tableName)){
                    //创建文件初始化文件内容
                    createMysqlPojoClassInfo(MYSQL_POJO_JAVA_CLASS_FILE_NAME,tableName);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            closeConnect();
        }
    }

    /**
     * 创建mysql实体类的信息
     * @param fileName   java文件的位置
     * @param tableName  表名
     */
    private static void createMysqlPojoClassInfo(String fileName,String tableName){
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(fileName));
            createMysqlPojoInfo(tableName,writer);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(writer != null){
                try {
                    writer.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 添加文档注释
     * @param sb 字符串
     * @throws Exception
     */
    private static void addComments(StringBuilder sb) throws Exception{
        sb.append("/**\r\n");
        sb.append(" * @author zfl \r\n");
        sb.append(" * @create ");
        sb.append(simpleDateStr);
        sb.append("\r\n");
        sb.append(" * @description \r\n");
        sb.append(" */");
    }

    /**
     * 创建mysql pojo的java文件
     * @param tableName
     * @param writer
     * @throws Exception
     */
    private static void createMysqlPojoInfo(String tableName,BufferedWriter writer) throws Exception{
        String packageName = MYSQL_TABLE_POJO_LOCATION.substring(14).replaceAll("/",".");
        StringBuilder sb = new StringBuilder();
        StringBuilder constructionStr = initConstructionStr();
        StringBuilder getSetStr = new StringBuilder();
        //创建包后，需要判断是否需要导包
        sb.append("package ");
        sb.append(packageName);
        sb.append(";\r\n\r\n");
        //获取表属性数据
        getMysqlTableAttrList(tableName);
        ResultSetMetaData data = resultSet.getMetaData();
        if(data != null){
            //判断是否需要import
            checkAndAddImport(sb,data);
        }
        //添加java文件的文档注释
        addComments(sb);
        sb.append("\r\n");
        sb.append("public class ");
        sb.append(MYSQL_POJO_JAVA_CLASS_NAME);
        sb.append(" {");
        sb.append("\r\n\r\n");
        List<String> result = getMysqlCommentResult(tableName);
        if(data != null){
            createTableAttrs(sb,constructionStr,getSetStr,data,result);
        }
        sb.append("\r\n");
        sb.append(constructionStr);
        sb.append("\r\n");
        sb.append(getSetStr);
        sb.append("\r\n");
        sb.append("}");
        writer.write(sb.toString());
    }

    private static StringBuilder initConstructionStr(){
        StringBuilder sb = new StringBuilder();
        sb.append("    public ");
        sb.append(MYSQL_POJO_JAVA_CLASS_NAME);
        sb.append("(){ }\r\n");
        sb.append("\r\n");
        sb.append("    public ");
        sb.append(MYSQL_POJO_JAVA_CLASS_NAME);
        sb.append("(");
        return sb;
    }

    /**
     * 检查mysql字段类型是否需要import 并拼接语句
     * @param sb 字符串
     * @param data mysql表字段属性结果集
     * @throws Exception
     */
    private static void checkAndAddImport(StringBuilder sb,ResultSetMetaData data) throws Exception{
        List<String> alreadyImportLineList = new ArrayList<>();
        int columnCount = data.getColumnCount();
        for (int i = 1 ; i <= columnCount ; i++){
            String importLine = MysqlColumnTypeEnum.valueOf(data.getColumnTypeName(i)).getImportLine();
            if(StringUtils.isNotBlank(importLine) && !alreadyImportLineList.contains(importLine)){
                alreadyImportLineList.add(importLine);
                sb.append(importLine);
                sb.append("\r\n");
            }
        }
        sb.append("\r\n");
    }

    /**
     * 获取musql 表的信息
     * @param tableName 表名
     * @return 结果集
     */
    private static void getMysqlTableAttrList(String tableName){
        String sql = "select * from " + tableName;
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 获取mysql属性注释结果集
     * @param tableName 表名
     * @return 所有字段的注释
     * @throws Exception
     */
    private static List<String> getMysqlCommentResult(String tableName) throws Exception{
        //保证返回结果有顺序
        List<String> result = new LinkedList<>();
        String sql = "select * from " + tableName;
        ResultSet resultSet = null;
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery("show full columns from " + tableName);
            while (resultSet.next()){
                result.add(resultSet.getString("Comment"));
            }
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            if(resultSet != null){
                resultSet.close();
            }
        }
        return result;
    }

    /**
     * 拼接java类的所有属性
     * @param sb     字符串
     * @param data   mysql表属性的结果集
     * @param result mysql表注释的结果集
     */
    private static void createTableAttrs(StringBuilder sb,StringBuilder constructionStr,StringBuilder getSetStr,ResultSetMetaData data,List<String> result){
        //是否添加注释标识
        boolean isCreateComment = true;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            int columnCount = data.getColumnCount();
            if(result.size() != columnCount){
                isCreateComment = false;
            }
            for (int i = 1 ; i <= columnCount ; i++){
                String columnName = data.getColumnName(i);
                System.out.println(columnName + "--" + data.getColumnTypeName(i));
                MysqlColumnTypeEnum typeEnum = MysqlColumnTypeEnum.valueOf(data.getColumnTypeName(i));
                if(isCreateComment){
                    sb.append("    /**\r\n");
                    sb.append("     * ");
                    sb.append(result.get(i - 1));
                    sb.append("\r\n");
                    sb.append("     */\r\n");
                }
                sb.append("    ");
                sb.append(typeEnum.getStatementModifier());
                sb.append(" ");
                sb.append(columnName);
                sb.append(";\r\n");

                createConstructionStr(constructionStr,typeEnum,i,columnName,columnCount);

                createGetterAndSetterStr(getSetStr,columnName,typeEnum);

                stringBuilder.append("        ");
                stringBuilder.append("this.");
                stringBuilder.append(columnName);
                stringBuilder.append(" = ");
                stringBuilder.append(columnName);
                stringBuilder.append(";\r\n");


            }
            constructionStr.append(stringBuilder);
            constructionStr.append("   }");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void createConstructionStr(StringBuilder constructionStr,MysqlColumnTypeEnum typeEnum,int i,String columnName,int columnCount){
        constructionStr.append(typeEnum.getJavaTypeName());
        constructionStr.append(" ");
        constructionStr.append(columnName);
        if(i == columnCount){
            constructionStr.append("){");
            constructionStr.append("\r\n");
        }else{
            constructionStr.append(",");
        }
    }

    private static void createGetterAndSetterStr(StringBuilder getSetStr,String columnName,MysqlColumnTypeEnum typeEnum){
        getSetStr.append("    public void set");
        getSetStr.append(columnName.substring(0,1).toUpperCase());
        getSetStr.append(columnName.substring(1));
        getSetStr.append("(");
        getSetStr.append(typeEnum.getJavaTypeName());
        getSetStr.append(" ");
        getSetStr.append(columnName);
        getSetStr.append("){\r\n");
        getSetStr.append("        this.");
        getSetStr.append(columnName);
        getSetStr.append(" = ");
        getSetStr.append(columnName);
        getSetStr.append(";\r\n");
        getSetStr.append("    }\r\n");
        getSetStr.append("\r\n");

        getSetStr.append("    public ");
        getSetStr.append(typeEnum.getJavaTypeName());
        getSetStr.append(" get");
        getSetStr.append(columnName.substring(0,1).toUpperCase());
        getSetStr.append(columnName.substring(1));
        getSetStr.append("(){\r\n");
        getSetStr.append("        return ");
        getSetStr.append(columnName);
        getSetStr.append(";\r\n");
        getSetStr.append("    }\r\n");
        getSetStr.append("\r\n");
    }

    /**
     * 根据mysql表名创建实体java类
     * @param tableName 表名
     * @return 是否创建成功
     */
    private static boolean createEntityByMysqlTableName(String tableName){
        assert StringUtils.isNotBlank(tableName);
        if(MYSQL_IGNORE_CREATE_TABLE_NAME_ARRAY.contains(tableName)){
           return false;
        }
        StringBuilder tableEntityName = new StringBuilder(EMPTY_STRING);
        //如果包含下划线，则以下划线为分割符，进行驼峰命名
        if(tableName.contains(MYSQL_SPECIAL_INTERVAL_LETTER)){
            String[] split = tableName.split(MYSQL_SPECIAL_INTERVAL_LETTER);
            for (String s : split) {
                tableEntityName.append(s.substring(0, 1).toUpperCase()).append(s.substring(1));
            }
        }else{
            //如果不包含下划线,将首字母变成大写即可
            tableEntityName = new StringBuilder(tableName.substring(0,1).toUpperCase() + tableName.substring(1));
        }
        MYSQL_POJO_JAVA_CLASS_NAME = tableEntityName.toString();
        MYSQL_POJO_JAVA_CLASS_FILE_NAME = MYSQL_TABLE_POJO_LOCATION + MYSQL_TABLE_POJO_LOCATION_INTERVAL_LETTER + tableEntityName + MYSQL_TABLE_POJO_CLASS_SUFFIX;
        //在指定位置创建文
        File file = new File(MYSQL_POJO_JAVA_CLASS_FILE_NAME);
        if(file.exists()){
            return true;
        }else{
            try {
                return file.createNewFile();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return false;
    }


    /**
     * 初始化mysql连接
     * @param ip            mysql ip
     * @param port          mysql port
     * @param databaseName  mysql 数据库名
     * @throws Exception
     */
    private static void initConnect(String ip, String port, String databaseName) throws Exception {
        Class.forName(MYSQL_DRIVER_CLASS);
        connection = DriverManager.getConnection(buildMysqlConnectUrl(ip, port, databaseName), MYSQL_USER, MYSQL_PASSWORD);
        if (connection.isClosed()) {
            throw new Exception("数据库连接状态关闭");
        }
    }

    /**
     * 关闭连接
     */
    private static void closeConnect() {
        try {
            if(resultSet != null){
                resultSet.close();
            }
            if (!connection.isClosed()) {
                connection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 构建mysql连接url
     *
     * @param ip           mysql服务器IP
     * @param port         mysql服务器地址
     * @param databaseName mysql数据库名
     * @return 连接url
     */
    private static String buildMysqlConnectUrl(String ip, String port, String databaseName) {
        return String.format(MYSQL_CONNECT_URL, ip, port, databaseName);
    }
}
