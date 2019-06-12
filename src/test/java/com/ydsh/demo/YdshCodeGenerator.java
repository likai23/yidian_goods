package com.ydsh.demo;


import com.ydsh.generator.entity.BasisInfo;
import com.ydsh.generator.util.DatabaseUtil;
import com.ydsh.generator.util.EntityInfoUtil;
import com.ydsh.generator.util.Generator;
import com.ydsh.generator.util.MySqlToJavaUtil;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.BiPredicate;

/**
 * @Auth yaozhongjie
 * @Date 2019/6/6 11:37
 **/
public class YdshCodeGenerator {
    // 基础信息：项目名、作者、版本
    public static final String PROJECT = "ydsh-saas-service-demo";
    public static final String AUTHOR = "姚仲杰";
    public static final String VERSION = "V1.0";
    // 数据库连接信息：连接URL、用户名、秘密、数据库名
    public static final String URL = "jdbc:mysql://stg.yidianlife.com:3306/ydsh_goods?useUnicode=true&characterEncoding=utf-8&autoReconnect=true&failOverReadOnly=false&useSSL=true&serverTimezone=UTC";
    public static final String NAME = "elife-dev";
    public static final String PASS = "Elife@2019";
    public static final String DATABASE = "ydsh_goods";
    // 路径信息，分开路径方便聚合工程项目，微服务项目
    public static final String BASE_PACKAGE="com.ydsh.goods.web";
    public static final String APPLICATION_DIR="/";

    //这些不用动
    public static final String ENTITY_URL = BASE_PACKAGE+".entity";
    public static final String DAO_URL = BASE_PACKAGE+".dao";
    public static final String XML_URL = BASE_PACKAGE+".dao.impl";
    public static final String SERVICE_URL = BASE_PACKAGE+".service";
    public static final String SERVICE_IMPL_URL = BASE_PACKAGE+".service.impl";
    public static final String CONTROLLER_URL = BASE_PACKAGE+".controller";

    public static void main(String[] args) {
        BasisInfo bi = new BasisInfo(PROJECT, AUTHOR, VERSION, URL, NAME, PASS, DATABASE, ENTITY_URL,
                DAO_URL, XML_URL, SERVICE_URL, SERVICE_IMPL_URL, CONTROLLER_URL);
        //全库生成
        generateByAll(bi);
        //单表生成
        //generateByTable(bi,"table","表注释");
    }

    public static void generateByTable(BasisInfo bi, String table, String classComment){
        bi.setEntityComment(classComment);
        baseGenerator(bi,table);
    }

    public static void generateByAll(BasisInfo bi){
        List<Map<String, String>> maps = DatabaseUtil.showTables(bi);
        maps.forEach(map->
                generateByTable(bi,map.keySet().toArray()[0].toString(),map.get(map.keySet().toArray()[0]))

        );
    }

    public static void baseGenerator(BasisInfo bi,String table){

        bi.setTable(table);
        bi.setEntityName(MySqlToJavaUtil.getClassName(table));
        bi.setObjectName(MySqlToJavaUtil.changeToJavaFiled(table));
        bi.setAgile(new Date().getTime()+"");
        bi.setCreateTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")));
        //bi.setCis();
        String projectPath = System.getProperty("user.dir");
        try {
            bi = EntityInfoUtil.getInfo(bi);
            String fileUrl =projectPath+APPLICATION_DIR+"src\\main\\java\\";// 生成文件存放位置
            String aa1 = Generator.createEntity(fileUrl, bi,true).toString();

            String aa2 = Generator.createDao(fileUrl, bi,false).toString();
            String aa3 = Generator.createDaoImpl(fileUrl, bi,false).toString();
            String aa4 = Generator.createService(fileUrl, bi,false).toString();
            String aa5 = Generator.createServiceImpl(fileUrl, bi,false).toString();
            String aa6 = Generator.createController(fileUrl, bi,false).toString();

            // 是否创建swagger配置文件
            // String aa7 = Generator.createSwaggerConfig(fileUrl, bi).toString();

            System.out.println(aa1);
            System.out.println(aa2);
            System.out.println(aa3);
            System.out.println(aa4);
            System.out.println(aa5);
            System.out.println(aa6);

            // System.out.println(aa7);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
