package com.xxxlboot.common.file;

import cn.hutool.core.io.FileUtil;
import com.xxxlboot.common.util.JarDependencyVersion;

import java.io.*;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @auther: Easy
 * @Date: 18-9-7 01:13
 * @Description:文件生成页面
 */
public class FileGeneration {

    /**使用的模板文件夹名称*/
    private static final String sourceTemplate = "sourceTemplate";

    private   static  String GROUP_ID ="com.ow";

    private  static   String ARTIFACT_ID= "tracer-starter-community";

    private  static   String PROJECT_ID ="";

    /**
     * 源模板文件基础路径
     */
    private static final String sourceBasePath = FileUtil.getParent(FileUtil.getAbsolutePath(""), 2) + "/src/main/resources/generator/" + sourceTemplate;
    /**
     * 目标文件基础路径
     */
    private static  String targetBasePath ="";

    public static   void CreateFIle(String groupId,String artifactId,String supName,int type) {
        GROUP_ID=groupId;
        ARTIFACT_ID=artifactId;
        PROJECT_ID=supName;
        targetBasePath=getProjectPath("^(?!.*("+PROJECT_ID+")).*$",3,type)+"/"+ARTIFACT_ID;

        FileGeneration fileGeneration = new FileGeneration();
        fileGeneration.createFile(sourceBasePath);
    }
    /**
     * 功能描述:
     *
     * @param: 文件名称
     * @return: 创建状态
     * @auther: easy
     * @date: 18-9-7 上午1:14
     */
    public  String createFile(String pathName) {
        File[] files = FileUtil.ls(pathName);
        for (File file : files) {
            if (file.isDirectory()) {
                String sourceAbsolutePath = file.getAbsolutePath();
                String sourceFileName = null;
                String sourceDirPath = getReplacedSourceDirPath(sourceAbsolutePath, false, sourceFileName);
                String targetDirPath = getReplacedTargetDirPath(sourceAbsolutePath, sourceDirPath, sourceFileName, false);
                makeTargetDirectory(targetDirPath);
                createFile(sourceDirPath);
            }else if(file.isFile()){
                String sourceAbsolutePath = file.getAbsolutePath();
                String sourceFileName = file.getName();
                String sourceDirPath = getReplacedSourceDirPath(sourceAbsolutePath, true, sourceFileName);
                String targetDirPath = getReplacedTargetDirPath(sourceAbsolutePath, sourceDirPath, sourceFileName, true);
                String targetFileName = sourceFileName;
                makeDirectoryAndFile(sourceDirPath, sourceFileName, targetDirPath, targetFileName);
            }
        }
        return null;

    }

    private String replacedSourceDirPath(String sourceDirPath) {


        String result = sourceDirPath
                .replace(sourceBasePath + "/rest", targetBasePath + "/" + ARTIFACT_ID +  "-rest")
                .replace(sourceBasePath + "/web", targetBasePath + "/" + ARTIFACT_ID +  "-web")
                .replace(sourceBasePath + "/config", targetBasePath + "/" + ARTIFACT_ID + "-config")
                .replace(sourceBasePath + "/service", targetBasePath + "/" + ARTIFACT_ID + "-service")
                .replace(sourceBasePath + "/dto", targetBasePath + "/" + ARTIFACT_ID + "-dto")
                .replace(sourceBasePath + "/mapper", targetBasePath + "/" + ARTIFACT_ID + "-mapper")
                .replace(sourceBasePath, targetBasePath);
        return result;
    }
    /**
     * 创建目录及文件
     * @param sourceDirPath
     * @param sourceFileName
     * @param targetDirPath
     * @param targetFileName
     */
    private void makeDirectoryAndFile(String sourceDirPath, String sourceFileName, String targetDirPath, String targetFileName){
        String sourceContent = readContentFromSourceFile(sourceDirPath, sourceFileName);
        String newContent = getReplacedContent(sourceContent);
        if ("pom.xml".equals(sourceFileName)) {
            newContent = getReplacedJarVersion(newContent);
        }
        if (makeTargetDirectory(targetDirPath)) {
            if (makeTargetFile(targetDirPath, targetFileName)) {
                writeNewContentToTargetFile(targetDirPath, targetFileName, newContent);
            }
        }
    }
    /**
     * 创建目录
     * @param dirPath
     */
    private boolean makeTargetDirectory(String dirPath){
        try {
            File file =new File(dirPath);
            if  (!file .exists()  && !file.isDirectory()){
                FileUtil.mkdir(file);
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    /**
     * 创建文件
     */
    private boolean makeTargetFile(String targetDirPath, String targetFileName){
        try {
            File file = new File(targetDirPath + "/" + targetFileName);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            return false;
        }
        return true;
    }
    private void writeNewContentToTargetFile(String targetDirPath, String targetFileName, String newContent){
        FileWriter fw = null;
        try {
            fw = new FileWriter(targetDirPath + "/" + targetFileName);
            fw.write(newContent);

        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            try {
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * 如果是pom.xml文件的话就需要替换里面的jar版本号
     * @param sourceContent
     * @return
     */
    private String getReplacedJarVersion(String sourceContent){
        String result = sourceContent;
        Set<Map.Entry<String, String>> set = JarDependencyVersion.jarVersionMap.entrySet();
        for(Map.Entry<String, String> entry : set){
            result =  result.replace(entry.getKey(), entry.getValue());
        }
        return result;
    }
    /**
     * 一次性读出文件中所有内容
     * @param sourceDirPath
     * @param sourceFileName
     * @return
     */
    private String readContentFromSourceFile(String sourceDirPath, String sourceFileName){
        String encoding = "utf-8";
        File file = new File(sourceDirPath + "/" + sourceFileName);
        Long filelength = file.length();
        byte[] filecontent = new byte[filelength.intValue()];
        try {
            FileInputStream in = new FileInputStream(file);
            in.read(filecontent);
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            return new String(filecontent, encoding);
        } catch (UnsupportedEncodingException e) {
            System.err.println("The OS does not support " + encoding);
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 获取源目录路径
     * @param sourceAbsolutePath
     * @param isFile
     * @param sourceFileName
     * @return
     */
    private String getReplacedSourceDirPath(String sourceAbsolutePath, boolean isFile, String sourceFileName){
        String sourceDirPath = null;
        if (isFile) {
            sourceDirPath = sourceAbsolutePath.replace("/" + sourceFileName, "");
        }else{
            sourceDirPath = sourceAbsolutePath;
        }
        return sourceDirPath;
    }
    public static  String getProjectPath(String url,int level,int type){

        String path = "";
        if(type==1){
            path=FileUtil.getParent(FileUtil.getAbsolutePath(""), level);

        }else{
        File[] tempList = FileUtil.ls(FileUtil.getParent(FileUtil.getAbsolutePath(""), level));

        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isFile()) { }
            if (tempList[i].isDirectory()) {
                if(StringMatchRule(tempList[i].getName(),url)==false){
                    path=tempList[i].getPath();
                    break;
                }
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
    /**
     * 获取目标目录路径
     * @param sourceAbsolutePath
     * @param sourceDirPath
     * @param sourceFileName
     * @param isFile
     * @return
     */
    private String getReplacedTargetDirPath(String sourceAbsolutePath, String sourceDirPath, String sourceFileName, boolean isFile){
        String targetDriPath = null;
        /**如果是文件*/
        if (isFile) {
            /**如果是读取的是java文件,由于需要根据java文件第一行的包路径来得到最终路径，所以需要单独处理*/
            if (isJavaFileDir(sourceDirPath)) {
                targetDriPath = replacedSourceDirPath(sourceDirPath) + "/" + getPackageDir(sourceDirPath, sourceFileName);

            }else{/**如果是非java文件，则直接根据源路径进行替换后得到目标路径*/
                targetDriPath = replacedSourceDirPath(sourceDirPath);
            }
        }else{/**如果是目录*/
            targetDriPath = replacedSourceDirPath(sourceDirPath);
        }
        return targetDriPath;
    }
    /**
     * 判断此目录路径是否是java文件目录路径
     * 引用注意：在正则表达式中的“\\”表示和后面紧跟着的那个字符构成一个转义字符（姑且先这样命名），代表着特殊的意义；所以如果你要在正则表达式中表示一个反斜杠\，应当写成“\\\\”
     * @param sourceDirPath
     * @return
     */
    private  boolean isJavaFileDir(String sourceDirPath){

        String regex = sourceBasePath.replaceAll("\\\\","\\\\\\\\") + "\\\\(rest|config|service|dto|web|mapper|vue|const|api)\\\\src\\\\main\\\\java";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(sourceDirPath);
        if (m.find()) {
            return true;
        }
        return false;
    }
    /**
     * 根据java文件的第一行获取包路径
     * @param sourceDirPath
     * @param sourceFileName
     * @return
     */
    private String getPackageDir(String sourceDirPath, String sourceFileName){
        String packageDir = null;
        File file = new File(sourceDirPath + "/" + sourceFileName);
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            String firstLine = br.readLine();
            packageDir = getReplacedContent(firstLine).replace(".", "/").replace("package ", "").replace(";", "");
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return packageDir;
    }
    /**
     * 将文件中的占位符替换为需要的格式
     * @param sourceContent
     * @return
     */
    private String getReplacedContent(String sourceContent){
        String result = sourceContent.replace("${groupId}", GROUP_ID).replace("${artifactId}", ARTIFACT_ID);
//		if ("sourceTemplate-client".equals(sourceTemplate)) {
//			result = result.replace("${server-groupId}", serverGroupId).replace("${server-artifactId}", serverArtifactId);
//		}
        return result;
    }
}
