package com.xxxlboot.common.gencode.core;





import com.xxxlboot.common.gen.GeoUtils;
import com.xxxlboot.common.gencode.bean.*;
import com.xxxlboot.common.gencode.constant.DbConst;
import com.xxxlboot.common.gencode.constant.GeneratorConst;
import com.xxxlboot.common.gencode.database.impl.MySqlDatabaseInfoReader;
import com.xxxlboot.common.gencode.util.*;
import com.xxxlboot.common.util.StringUtil;
import org.beetl.core.Configuration;
import org.beetl.core.Template;
import com.xxxlboot.common.gencode.config.DatabaseConfig;
import com.xxxlboot.common.gencode.config.GeneratorConfig;
import com.xxxlboot.common.gencode.config.GlobalConfig;
import com.xxxlboot.common.gencode.database.DatabaseInfoReader;
import org.beetl.core.GroupTemplate;
import org.beetl.core.resource.ClasspathResourceLoader;
import org.beetl.core.resource.FileResourceLoader;

import java.io.*;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Explain:     []
 * Date:        [2018/09/28
 * Coder:       [Easy]
 * Version:     [1.0]
 */
public class GeneratorCode {

    /**
     * 数据库相关配置
     */
    private DatabaseConfig databaseConfig;
    /**
     * 生成文件相关配置
     */
    private GeneratorConfig generatorConfig;
    /**
     * 全局配置
     */
    private GlobalConfig globalConfig;

    /**
     * 数据信息读取对象
     */
    private DatabaseInfoReader dbReader;

    /**
     * 配置值占位符匹配表达式
     */
    private Pattern configValPattern;

    /**
     * 模板管理对象
     */
    private GroupTemplate groupTemplate;

    private long startTime;//程序开始时间
    private long endTime;//程序结束时间

    private PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));//缓冲控制台输出流

    public GeneratorCode() {}
    public GeneratorCode(DatabaseConfig databaseConfig, GeneratorConfig generatorConfig) {
        this(databaseConfig, generatorConfig, null);
    }
    public GeneratorCode(DatabaseConfig databaseConfig, GeneratorConfig generatorConfig, GlobalConfig globalConfig) {
        this.databaseConfig = databaseConfig;
        this.generatorConfig = generatorConfig;
        this.globalConfig = globalConfig;
    }

    /**
     * 单表生成
     */
    public void generator() {
        beforeGenerator();
        startGenerator();
        afterGenerator();
    }

    /**
     * 批量生成
     */
    public void batchGenerator() {
        beforeGenerator();
        startBatchGenerator();
        afterGenerator();
    }

    /**
     * 获取构建数据集(单次,指定表)
     * @return
     */
    public List<GeneratorInfo> getBuildData(){
        beforeBuildData();
        List<TableInfo> tableInfo = readTableInfo();
        return buildGeneratorInfoList(tableInfo);
    }
    /**
     * 获取构建数据集(批量,全库表)
     * @return
     */
    public List<GeneratorInfo> getBatchBuildData(){
        beforeBuildData();
        List<TableInfo> tableInfo = readTablesInfo();
        return buildGeneratorInfoList(tableInfo);
    }

    private void beforeBuildData() {
        analysisConfig();
        buildDatabaseInfoReader();
    }

    private void startBatchGenerator() {
        List<TableInfo> tableInfoList = readTablesInfo();

        List<GeneratorInfo> generatorInfolist = buildGeneratorInfoList(tableInfoList);
        processTemplate(generatorInfolist);
    }

    private void startGenerator() {
        List<TableInfo> tableInfo = readTableInfo();
        List<GeneratorInfo> generatorInfolist = buildGeneratorInfoList(tableInfo);
        processTemplate(generatorInfolist);
    }

    /**
     * 处理模板
     * @param generatorInfolist
     */
    private void processTemplate(List<GeneratorInfo> generatorInfolist) {
          String  functionName="";//数据库表名前缀
          String dbTableName="";//数据库表名前缀

          String moudelName="";//项目模块名
        List<TemplateInfo> templateInfoList = buildTemplateInfoList();//获取模板列表
        for (int i = 0; i < generatorInfolist.size(); i++) {
            GeneratorInfo generatorInfo = generatorInfolist.get(i);
            moudelName="tracer"+"-"+generatorInfo.getPackageName()+"-"+generatorInfo.getFunctionName();//项目模块名
            Map valMap = new HashMap(1);
            valMap.put("data", generatorInfo);
            groupTemplate.setSharedVars(valMap);//设置模板共享变量
            String path= GeoUtils.getPath("^(?!.*("+generatorInfo.getPackageName()+")).*$",moudelName);

            for (int j = 0; j < templateInfoList.size(); j++) {
                TemplateInfo templateInfo = templateInfoList.get(j);
                String outputPath = templateInfo.getOutputPath();
                System.out.println(path);
                if(outputPath.indexOf("sqlmap")!=-1){
                    String relativelyPath=System.getProperty("user.dir");
                    outputPath=path+"/"+moudelName+"-mapper"+"/src/main/resources/mapper/${className}Mapper.xml";
                }
                if(outputPath.indexOf("vue")!=-1){

                    outputPath=generatorConfig.getVuePath()+"/views/"+generatorConfig.getPackageName()+"/${className}/index.vue";
                }
                if(outputPath.indexOf("const")!=-1){
                    outputPath=generatorConfig.getVuePath()+"/const/"+generatorConfig.getPackageName()+"/${className}/${className}TableOption.js";
                }
                if(outputPath.indexOf("api")!=-1){
                    outputPath=generatorConfig.getVuePath()+"/api/"+generatorConfig.getPackageName()+"/${className}/${className}.js";
                }
                outputPath = replaceConfigVal(outputPath, generatorInfo);
                File outFile = new File(outputPath);
                boolean mkdirs = FileUtil.mkdirs(outFile);
                if (!mkdirs) {
                    throw new RuntimeException("无法创建目录：" + outputPath + "，请修改目录权限允许写入或换一个文件输出路径。");
                }
                Template template = templateInfo.getTemplate();
                if (template != null) {
                    try {
//                        BufferedWriter osw = new BufferedWriter(new FileWriter(outFile));
                        OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(outFile), "UTF-8");
                        BufferedWriter bw = new BufferedWriter(osw);
                        template.renderTo(bw);
                        osw.close();
                        StringBuilder logInfo = ReuseStringBuilder.getStringBuilder();
                        logInfo.append("[").append(outFile.getName()).append("]生成成功,文件保存路径 -> ").append(outFile.getAbsolutePath());
                        out.println(logInfo);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 替换字符串中的占位符
     * @param str
     * @param generatorInfo
     * @return
     */
    private String replaceConfigVal(String str, GeneratorInfo generatorInfo) {
        //匹配${}表达式,替换为配置的值
        Matcher matcher = configValPattern.matcher(str);
        while (matcher.find()) {
            String group = matcher.group();
            String cfgKey = group.substring(globalConfig.getTemplatePlaceholderStart().length(), group.length() - globalConfig.getTemplatePlaceholderEnd().length());
            Object configVal = ObjectUtil.getChainBeanProperty(generatorInfo, cfgKey);
            if (configVal != null) {
                String val = configVal.toString();
                if ("packageName".equals(cfgKey)) {//替换包为文件夹
                    val = val.replace(".", "/");
                }
                str = str.replace(group, val);
            }
        }
        return str;
    }

    /**
     * 构建模板信息列表
     * @return
     */
    private List<TemplateInfo> buildTemplateInfoList() {
        List<TemplateInfo> templateInfoList = null;
        String templateRoot = generatorConfig.getTemplateRoot();
        String templateId = generatorConfig.getTemplateId();
        if (templateRoot == null) {
            //从jar包中读取默认模板
            String templatePath = GeneratorConfig.DEFAULT_TEMPLATE_PATH;
            templateInfoList = processJarTemplateFiles(templatePath);
        } else {
            //读取用户设置的模板
            String templatePath = FileUtil.addLastSeparator(templateRoot) + templateId;
            if (templatePath.startsWith("~")) {


                //从classPath读取模板
                templatePath = templatePath.substring(1);


                URL resource = GeneratorCode.class.getResource(templatePath);
                if (resource == null) {
                    throw new RuntimeException("[异常]模板:" + templatePath + "路径不存在!");
                }
                try {
                    String path = URLDecoder.decode(resource.getPath(), "utf-8");
                    path = StringUtil.replaceStartStr(path, "/", "");//移除路径开始可能多出的“/”
                    templateInfoList = processTemplateFiles(path);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                //从文件系统中读取模板
                File rootPath = new File(templatePath);
                if (!rootPath.exists()) {
                    throw new RuntimeException("[异常]模板:" + templatePath + "路径不存在!");
                } else {
                    templateInfoList = processTemplateFiles(rootPath.getAbsolutePath());
                }
            }
        }
        return templateInfoList;
    }

    /**
     * 处理Jar包中的模板文件
     * @param path
     * @return
     */
    private List<TemplateInfo> processJarTemplateFiles(String path) {
        List<TemplateInfo> templateInfoList = null;
        try {
            ClasspathResourceLoader fileLoader = new ClasspathResourceLoader(path);
            Configuration cf = Configuration.defaultConfiguration();
            cf.setStatementStart(globalConfig.getTemplateStatementStart());
            cf.setStatementEnd(globalConfig.getTemplateStatementEnd());
            cf.setPlaceholderStart(globalConfig.getTemplatePlaceholderStart());
            cf.setPlaceholderEnd(globalConfig.getTemplatePlaceholderEnd());
            groupTemplate = new GroupTemplate(fileLoader, cf);
            List<String> templateFileList = FileUtil.traversePackageGetAllFileName(path);
            templateInfoList = new ArrayList<TemplateInfo>();
            String templateFileSuffix = generatorConfig.getTemplateFileSuffix();
            String outputPath = generatorConfig.getOutputPath();
            for (String templateFile : templateFileList) {
                if (templateFile != null) {
                    TemplateInfo templateInfo = new TemplateInfo();
                    String relativePath = templateFile.substring(path.length());//模板相对于fileLoader的路径
                    templateInfo.setTemplatePath(templateFile);
                    String fileSavePath = outputPath + FileUtil.addFrontSeparator(relativePath);
                    if (!"/".equals(templateFile.substring(templateFile.length() - 1))) {
                        Template template = groupTemplate.getTemplate(relativePath);
                        templateInfo.setTemplate(template);
                        fileSavePath = StringUtil.replaceEndStr(fileSavePath, templateFileSuffix, "");//清除模板后缀
                    }
                    templateInfo.setOutputPath(fileSavePath);
                    templateInfoList.add(templateInfo);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return templateInfoList;
    }

    /**
     * 处理模板文件
     * @param path
     * @return
     */
    private List<TemplateInfo> processTemplateFiles(String path) {
        List<TemplateInfo> templateInfoList = null;
        try {
            FileResourceLoader fileLoader = new FileResourceLoader(path);
            Configuration cf = Configuration.defaultConfiguration();
            cf.setStatementStart(globalConfig.getTemplateStatementStart());
            cf.setStatementEnd(globalConfig.getTemplateStatementEnd());
            cf.setPlaceholderStart(globalConfig.getTemplatePlaceholderStart());
            cf.setPlaceholderEnd(globalConfig.getTemplatePlaceholderEnd());
            groupTemplate = new GroupTemplate(fileLoader, cf);
            Queue<File> templateFileList = FileUtil.traversePathGetAllFileToQueue(path);
            templateInfoList = new ArrayList<TemplateInfo>();
            String templateFileSuffix = generatorConfig.getTemplateFileSuffix();
            String outputPath = generatorConfig.getOutputPath();
            for (File templateFile : templateFileList) {
                if (templateFile != null) {
                    TemplateInfo templateInfo = new TemplateInfo();
                    String templatePath = templateFile.getAbsolutePath();
                    String relativePath = templatePath.substring(path.length());//模板相对于fileLoader的路径
                    templateInfo.setTemplatePath(templatePath);
                    String fileSavePath = outputPath + FileUtil.addFrontSeparator(relativePath);
                    if (templateFile.isFile()) {
                        Template template = groupTemplate.getTemplate(relativePath);
                        templateInfo.setTemplate(template);
                        fileSavePath = StringUtil.replaceEndStr(fileSavePath, templateFileSuffix, "");//清除模板后缀
                    }
                    templateInfo.setOutputPath(fileSavePath);
                    templateInfoList.add(templateInfo);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return templateInfoList;
    }

    /**
     * 构建文件生成所需信息
     * @param tableInfos
     * @return
     */
    private List<GeneratorInfo> buildGeneratorInfoList(List<TableInfo> tableInfos) {
        List<GeneratorInfo> generatorInfos = new ArrayList<GeneratorInfo>();
        if (tableInfos != null && tableInfos.size() > 0) {
            for (int i = 0; i < tableInfos.size(); i++) {
                TableInfo tableInfo = tableInfos.get(i);
                if (tableInfo != null) {
                    GeneratorInfo gInfo = new GeneratorInfo();
                    /**
                     * 构建table信息
                     */
                    GeneratorTableInfo gTableInfo = buildGeneratorTableInfo(tableInfo);
                    gInfo.setTableName(tableInfo.getTable());
                    gInfo.setTableInfo(gTableInfo);
                    String table = gTableInfo.getTable();
                    String [] backB= table.split("_");
                    String simpleTableComment = gTableInfo.getSimpleTableComment();
                    databaseConfig.setTableName(table);
                    /**
                     * 构建column信息
                     */
                    List<ColumnInfo> columnsInfo = dbReader.getColumnsInfo(databaseConfig);
                    if (columnsInfo != null && columnsInfo.size() > 0) {
                        List<GeneratorColumnInfo> gColumnInfos = buildGeneratorColumnInfos(columnsInfo);
                        gInfo.setColumns(gColumnInfos);
                    } else {
                        throw new RuntimeException("异常!获取" + table + "表的column信息为空");
                    }
                    /**
                     * 设置用户配置的信息
                     */
                    gInfo.setAuthor(generatorConfig.getAuthor());
                    if (generatorConfig.getDescribe() == null) {
                        gInfo.setDescribe(simpleTableComment);
                    } else {
                        gInfo.setDescribe(generatorConfig.getDescribe());
                    }
                    if (tableInfos.size() > 1) {//多表生成时className要自动生成
                        gInfo.setClassName(buildClassName());
                    } else {
                        if (StringUtil.isEmpty(generatorConfig.getClassName())) {
                            gInfo.setClassName(buildClassName());
                        } else {
                            gInfo.setClassName(generatorConfig.getClassName());
                        }
                    }
                    gInfo.setLowerClassName(WordUtil.initialLow(gInfo.getClassName()));

                    if (generatorConfig.getModel() == null) {
                        gInfo.setModel(gInfo.getLowerClassName());
                    } else {
                        gInfo.setModel(generatorConfig.getModel());
                    }
                    if (generatorConfig.getDate() == null) {
                        gInfo.setDate(DateUtil.getNowDateYMDHMS());
                    } else {
                        gInfo.setDate(generatorConfig.getDate());
                    }
                    gInfo.setPackageName(generatorConfig.getPackageName());
                    gInfo.setProjectName(generatorConfig.getProjectName());
                    gInfo.setFunctionName(generatorConfig.getFunctionName());
                    gInfo.setSequence(i+1);
                    generatorInfos.add(gInfo);
                } else {
                    throw new RuntimeException("异常!tableInfo为null");
                }
            }
        } else {
            out.println("[Warn]获取表数据为空,请检查配置以及数据库信息是否准确");
        }
        return generatorInfos;
    }

    /**
     * 构建生成文件时的column信息
     * @param columnsInfo
     * @return
     */
    private List<GeneratorColumnInfo> buildGeneratorColumnInfos(List<ColumnInfo> columnsInfo) {
        List<GeneratorColumnInfo> infoList = new ArrayList<GeneratorColumnInfo>();
        for (int i = 0; i < columnsInfo.size(); i++) {
            ColumnInfo columnInfo = columnsInfo.get(i);
            GeneratorColumnInfo ginfo = new GeneratorColumnInfo();
            ginfo.setColumn(columnInfo.getColumn());
            String columnComment = columnInfo.getColumnComment();
            ginfo.setColumnComment(columnComment);
            ginfo.setColumnDefault(columnInfo.getColumnDefault());
            ginfo.setColumnLen(columnInfo.getColumnLen());
            ginfo.setDataType(columnInfo.getDataType());
            ginfo.setColumnType(columnInfo.getColumnType());
            ginfo.setNull(columnInfo.isNull());
            ginfo.setPrimaryKey(columnInfo.isPrimaryKey());
            ginfo.setUnique(columnInfo.isUnique());
            Map<String, String> columnCfg = ConfigUtil.analysisCommentCfg(columnComment);
            if (columnCfg.containsKey(GeneratorConst.COLUMN_BLANK_ORDER) || columnCfg.containsKey(GeneratorConst.COLUMN_SIMPLE_BLANK_ORDER)) {
                ginfo.setBlank(true);
            }
            if (columnCfg.containsKey(GeneratorConst.COLUMN_HIDDEN_ORDER) || columnCfg.containsKey(GeneratorConst.COLUMN_SIMPLE_HIDDEN_ORDER)) {
                ginfo.setHidden(true);
            }
            if (columnCfg.containsKey(GeneratorConst.COLUMN_LIKE_ORDER) || columnCfg.containsKey(GeneratorConst.COLUMN_SIMPLE_LIKE_ORDER)) {
                ginfo.setLike(true);
            }
            if (columnCfg.containsKey(GeneratorConst.COLUMN_INTERVAL_ORDER) || columnCfg.containsKey(GeneratorConst.COLUMN_SIMPLE_INTERVAL_ORDER)) {
                ginfo.setInterval(true);
            }
            String regex = columnCfg.get(GeneratorConst.COLUMN_REGEX_ORDER);
            if (StringUtil.isEmpty(regex)) {
                regex = columnCfg.get(GeneratorConst.COLUMN_SIMPLE_REGEX_ORDER);
            }
            if (StringUtil.isNotEmpty(regex)) {
                ginfo.setRegex(regex);
            }
            String conver = columnCfg.get(GeneratorConst.COLUMN_COVER_ORDER);
            if (StringUtil.isEmpty(conver)) {
                conver = columnCfg.get(GeneratorConst.COLUMN_SIMPLE_COVER_ORDER);
            }
            List<Map> converList = ConfigUtil.analysisConvertCfg(conver);
            ginfo.setCoverList(converList);
            ginfo.setColumnConfig(columnCfg);
            String property = WordUtil.converToProperty(ginfo.getColumn(), databaseConfig.getColumnNamingRule());
            ginfo.setProperty(property);
            ginfo.setUpperProperty(WordUtil.initialUp(property));
            JavaType javaType = dbReader.dbTypeCoverToJavaType(columnInfo.getDataType());
            ginfo.setJavaType(javaType.getJavaType());
            ginfo.setFullPathJavaType(javaType.getFullPathJavaType());
            ginfo.setBaseJavaType(javaType.getBaseJavaType());
            ginfo.setSimpleColumnComment(ConfigUtil.removeConfig(columnComment));
            infoList.add(ginfo);
        }
        return infoList;
    }

    /**
     * 构建生成文件时的table信息
     * @param tableInfo
     * @return
     */
    private GeneratorTableInfo buildGeneratorTableInfo(TableInfo tableInfo) {
        String table = tableInfo.getTable();
        String tableComment = tableInfo.getTableComment();
        GeneratorTableInfo gTableInfo = new GeneratorTableInfo();
        gTableInfo.setTable(table);
        gTableInfo.setTableComment(tableComment);
        Map<String, String> tableCfg = ConfigUtil.analysisCommentCfg(tableComment);
        if (tableCfg.containsKey(GeneratorConst.TABLE_LOGIC_DEL_ORDER) || tableCfg.containsKey(GeneratorConst.TABLE_SIMPLE_LOGIC_DEL_ORDER)) {
            String delFiled = tableCfg.get(GeneratorConst.TABLE_DEL_FILED_ORDER);
            if (StringUtil.isEmpty(delFiled)) {
                delFiled = tableCfg.get(GeneratorConst.TABLE_SIMPLE_DEL_FILED_ORDER);
            }
            if (StringUtil.isEmpty(delFiled)) {
                out.println("[Warn]" + table + "表使用了逻辑删除指令却没有设定实现逻辑删除的字段,此配置将被忽略,逻辑删除不会生效,如需开启,请在comment中配置delFiled属性");
            } else {
                gTableInfo.setLogicDel(true);
                gTableInfo.setDelFiled(delFiled);
                String property = WordUtil.converToProperty(delFiled, databaseConfig.getTableNamingRule());
                gTableInfo.setDelFiledProperty(property);
                gTableInfo.setUpperDelFiledProperty(WordUtil.initialUp(property));
            }
        }
        if (tableCfg.containsKey(GeneratorConst.TABLE_USE_LOCK_ORDER) || tableCfg.containsKey(GeneratorConst.TABLE_SIMPLE_USE_LOCK_ORDER)) {
            String lockFiled = tableCfg.get(GeneratorConst.TABLE_LOCK_FILED_ORDER);
            if (StringUtil.isEmpty(lockFiled)) {
                lockFiled = tableCfg.get(GeneratorConst.TABLE_SIMPLE_LOCK_FILED_ORDER);
            }
            if (StringUtil.isEmpty(lockFiled)) {
                out.println("[Warn]" + table + "表使用了乐观锁指令却没有设定实现乐观锁的字段,此配置将被忽略,启用乐观锁不会生效,如需开启,请在comment中配置lockFiled属性");
            } else {
                gTableInfo.setUseLock(true);
                gTableInfo.setLockFiled(lockFiled);
                String property = WordUtil.converToProperty(lockFiled, databaseConfig.getTableNamingRule());
                gTableInfo.setLockFiledProperty(property);
                gTableInfo.setUpperLockFiledProperty(WordUtil.initialUp(property));
            }
        }
        gTableInfo.setTableConfig(tableCfg);
        gTableInfo.setSimpleTableComment(ConfigUtil.removeConfig(tableComment));
        return gTableInfo;
    }

    private String buildClassName() {
        String tableName = databaseConfig.getTableName();
        String tablePrefix = databaseConfig.getTablePrefix();
        if (StringUtil.isNotEmpty(tablePrefix)) {
            //去除表前缀
            String[] perfixs = tablePrefix.split(",");
            for (String p : perfixs) {
                if (tableName != null && tableName.startsWith(p)) {
                    tableName = tableName.substring(p.length());
                    break;
                }
            }
        }
        String tableNamingRule = databaseConfig.getTableNamingRule();
        String property = WordUtil.converToProperty(tableName, tableNamingRule);

        return WordUtil.initialUp(property);
    }


    /**
     * 读取表信息
     */
    private List<TableInfo> readTableInfo() {
        if (dbReader != null) {
            return dbReader.getTableInfo(databaseConfig);
        } else {
            throw new RuntimeException("数据库读取器为null");
        }
    }

    private void buildDatabaseInfoReader() {
        String dbType = databaseConfig.getDbType();
        if (DbConst.DB_TYPE_MYSQL.equalsIgnoreCase(dbType)) {
            dbReader = new MySqlDatabaseInfoReader();
        } else {
            throw new RuntimeException("对不起,暂只支持MySQL数据库");
        }
    }

    private List<TableInfo> readTablesInfo() {
        if (dbReader != null) {
            return dbReader.getTablesInfo(databaseConfig);
        } else {
            throw new RuntimeException("数据库读取器为null");
        }
    }

    private void beforeGenerator() {
        this.startTime = System.currentTimeMillis();
        StringBuilder str = ReuseStringBuilder.getStringBuilder();
        str.append("[").append(DateUtil.getNowDateYMDHMS()).append("]开始生成文件...");
//
        out.println(str);
        analysisConfig();
        buildDatabaseInfoReader();
    }

    private void afterGenerator() {
        endTime = System.currentTimeMillis();
        double usedTime = (endTime - startTime) / 1000.0;
        StringBuilder str = ReuseStringBuilder.getStringBuilder();
        str.append("[").append(DateUtil.getNowDateYMDHMS()).append("]文件生成完毕,耗时").append(usedTime).append("秒");
//        System.out.printf("["+DateUtil.getNowDateYMDHMS() + "]文件生成完毕,耗时%s秒\n", usedTime);
        out.println(str);
        out.flush();
        /**
         * 此处关闭控制台输出流会导致主线程的后续print都无法输出
         * jdk1.7及以上版本可以自动关闭流，不关闭程序结束后也会释放，所以在此不做处理
         */
//        out.close();
        if (globalConfig.isOpenFileManager()) {
            FileUtil.openFileManager(generatorConfig.getOutputPath());
        }
    }

    /**
     * 解析配置信息
     */
    private void analysisConfig() {
        if (this.databaseConfig != null) {
            if (generatorConfig != null) {
                databaseConfig.verifyConfig();
                generatorConfig.verifyConfig();
            } else {
                throw new RuntimeException("[Exception]缺少generatorConfig");
            }
        } else {
            throw new RuntimeException("[Exception]缺少databaseConfig");
        }
        if (globalConfig == null) {
            globalConfig = new GlobalConfig();
        }
        String templatePlaceholderStart = globalConfig.getTemplatePlaceholderStart();
        String templatePlaceholderEnd = globalConfig.getTemplatePlaceholderEnd();
        configValPattern = Pattern.compile(StringUtil.escapeRegexChars(templatePlaceholderStart) + "[a-zA-Z0-9_\\.]+" + StringUtil.escapeRegexChars(templatePlaceholderEnd));
    }

    public DatabaseConfig getDatabaseConfig() {
        return databaseConfig;
    }

    public GeneratorCode setDatabaseConfig(DatabaseConfig databaseConfig) {
        this.databaseConfig = databaseConfig;
        return this;
    }

    public GeneratorConfig getGeneratorConfig() {
        return generatorConfig;
    }

    public GeneratorCode setGeneratorConfig(GeneratorConfig generatorConfig) {
        this.generatorConfig = generatorConfig;
        return this;
    }

    public GlobalConfig getGlobalConfig() {
        return globalConfig;
    }

    public GeneratorCode setGlobalConfig(GlobalConfig globalConfig) {
        this.globalConfig = globalConfig;
        return this;
    }
}
