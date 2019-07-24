
package com.xxxlboot.common.gen;



import cn.hutool.core.io.FileUtil;
import com.xxxlboot.common.config.GeneratorConfigUtils;
import com.xxxlboot.common.file.FileGeneration;
import com.xxxlboot.common.gencode.config.DatabaseConfig;
import com.xxxlboot.common.gencode.config.GeneratorConfig;

import java.io.File;
import java.util.regex.Pattern;

/**
 * @author easy
 */
public class GeoUtils {
    public static void main(String[] args) {
    String vuePath ="/home/cqt/WebstormProjects/Tracer-avue-crowd/src";
    getTables(vuePath,"com.ow",
             "information",
             "drafts",
             "information",
                "tracer","drafts",2
      );
//     getTable(vuePath,"store_leavel","shop","store","tracer","shop");

    }

    /**
     *  根据表名前缀生成整个模块，通常在项目初始化时生成。
     * @param  groupId POM组织id 如com.ow
     * @param prefix 表名前缀如admin_user prefix就等于admin
     * @param modelName  子项目名称如tracer-admin-account modelName就等于admin
     * @param functionName 子项目中的模块名称 如tracer-admin-account functionName就等于 account
     * @param project  项目名前缀 如 tracer-admin-account project 就等于tracer
     * @param superName 模块上级文件名，用于查找生成路径
     */
    public static void getTables(String vuePath,String groupId,String prefix,String  modelName,String functionName,String project,String  superName,int type) {
        String  moudelName= project.toLowerCase()+"-"+modelName+"-"+functionName;

        String[] test2 = {"rest","vue","api","const"};
        String path= GeoUtils.getPath("^(?!.*"+superName+").*$",moudelName);

        for(int i=0;i<test2.length;i++){
            GeneratorConfig generatorConfig = new GeneratorConfig();
            generatorConfig.setVuePath(vuePath);
            generatorConfig.setAuthor("cqt");
            generatorConfig.setPackageName(modelName);
            generatorConfig.setFunctionName(functionName);
            generatorConfig.setProjectName(project);
            generatorConfig.setTemplateRoot(FileUtil.getParent(FileUtil.getAbsolutePath(""), 2) + "/src/main/resources/generator/defaultTemplate/SSM" );//使用~代表从classpath读取模板
            generatorConfig.setTemplateId("/${packageName}/"+test2[i]);
            generatorConfig.setOutputPath(path+"/"+moudelName+"-"+test2[i]+"/src/main/java/com/ow/tracer/"+modelName+"/"+functionName+"/"+test2[i]);
            DatabaseConfig databaseConfig = new DatabaseConfig();
            String dbName = "tracer_drafts";//数据库名
            databaseConfig.setTablePrefix(prefix+"_%");
            databaseConfig.setDbUrl("jdbc:mysql://60.208.57.115:10506/" + dbName + "?useUnicode=true&characterEncoding=UTF-8&allowMultiQueries=true&&useSSL=false");
            databaseConfig.setDbName(dbName);//设置数据库名
            databaseConfig.setUsername("ruitu");
            databaseConfig.setPassword("ruI115tU");
            GeneratorConfigUtils.batchgencode(generatorConfig,databaseConfig);

        }

    }

    /**
     * 示例：想要生成一个表到：tracer-admin-account
     * @param dbTableName 表名 admin_system
     * @param modelName 模块名admin
     * @param functionName 功能名account
     * @param project 总项目名 tracer
     * @param superName 副项目名 用于查找生成路径 admin
     */
        public static void getTable(String vuePath,String dbTableName,String modelName,String functionName,String project,String  superName) {
            String  moudelName= project.toLowerCase()+"-"+modelName+"-"+functionName;
            String[] test2 = {"mapper","dto","rest","web","service","vue","api","const"};
            String path= GeoUtils.getPath("^(?!.*"+superName+").*$",moudelName);
        for(int i=0;i<test2.length;i++){
            GeneratorConfig generatorConfig = new GeneratorConfig();
            generatorConfig.setVuePath(vuePath);
            generatorConfig.setAuthor("cqt");
            generatorConfig.setPackageName(modelName);
            generatorConfig.setFunctionName(functionName);
            generatorConfig.setProjectName(project);
            generatorConfig.setTemplateRoot(FileUtil.getParent(FileUtil.getAbsolutePath(""), 2) + "/src/main/resources/generator/defaultTemplate/SSM" );//使用~代表从classpath读取模板
            generatorConfig.setTemplateId("/${packageName}/"+test2[i]);
            generatorConfig.setOutputPath(path+"/"+moudelName+"-"+test2[i]+"/src/main/java/com/ow/tracer/"+modelName+"/"+functionName+"/"+test2[i]);
            DatabaseConfig databaseConfig = new DatabaseConfig();
            String dbName = "tracer-shop";//数据库名
            databaseConfig.setTableName(dbTableName);
            databaseConfig.setDbUrl("jdbc:mysql://60.208.57.115:10506/" + dbName + "?useUnicode=true&characterEncoding=UTF-8&allowMultiQueries=true&&useSSL=false");
            databaseConfig.setDbName(dbName);//设置数据库名
            databaseConfig.setUsername("ruitu");
            databaseConfig.setPassword("ruI115tU");
            GeneratorConfigUtils.gencode(generatorConfig,databaseConfig);
        }
    }
    public static  String getPath(String url,String moudelName){

        String path = "";
        String relativelyPath=System.getProperty("user.dir");
        System.out.println(relativelyPath);
        File file=new File(relativelyPath);
        File[] tempList = file.listFiles();
        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isFile()) { }
            if (tempList[i].isDirectory()) {
                if(StringMatchRule(tempList[i].getName(),url)==false){
                    path=tempList[i].getPath()+"/"+moudelName;
                    break;
                }
            }
        }

        return path;
    }
    public static  String getProjectPath(String url){

        String path = "";
        String relativelyPath=System.getProperty("user.dir");

        File file=new File(relativelyPath);
        File[] tempList = file.listFiles();


        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isFile()) { }
            if (tempList[i].isDirectory()) {
                if(StringMatchRule(tempList[i].getName(),url)==false){
                    path=tempList[i].getPath();
                    break;
                }
            }
        }
        return path;
    }
    public static boolean StringMatchRule(String souce, String regex) {
        boolean result = false;
        if (regex != null && souce != null) {
            result = Pattern.matches(regex, souce);
        }
        return result;
    }

}
