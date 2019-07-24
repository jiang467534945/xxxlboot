package com.xxxlboot.common.config;





import com.xxxlboot.common.gencode.bean.GeneratorInfo;
import com.xxxlboot.common.gencode.config.DatabaseConfig;
import com.xxxlboot.common.gencode.config.GeneratorConfig;
import com.xxxlboot.common.gencode.config.GlobalConfig;
import com.xxxlboot.common.gencode.core.GeneratorCode;

import java.util.List;

/**
 * Explain:文件生成测试
 * Date:2018/09/28
 * Coder:Easy
 * Version:1.0
 */
public class GeneratorConfigUtils {

    public static  void  gencode (GeneratorConfig generatorConfig, DatabaseConfig databaseConfig){
        GlobalConfig globalConfig = new GlobalConfig();
        GeneratorCode generatorCode = new GeneratorCode(databaseConfig, generatorConfig, globalConfig);//写法一，使用配置信息创建文件生成核心类
        generatorCode.generator();//执行指定表生成
        List<GeneratorInfo> buildData = generatorCode.getBuildData();//获取构建数据集
    }
    public static  void  batchgencode (GeneratorConfig generatorConfig,DatabaseConfig databaseConfig){

        GlobalConfig globalConfig = new GlobalConfig();
        GeneratorCode generatorCode = new GeneratorCode(databaseConfig, generatorConfig, globalConfig);//写法一，使用配置信息创建文件生成核心类
        generatorCode.batchGenerator();//执行批量生成//
        List<GeneratorInfo> buildData = generatorCode.getBuildData();//获取构建数据集
    }

}
