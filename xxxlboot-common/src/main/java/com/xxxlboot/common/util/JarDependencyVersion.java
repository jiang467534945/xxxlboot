package com.xxxlboot.common.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @auther: Easy
 * @Date: 18-9-7 17:46
 * @Description:
 */
public class JarDependencyVersion {
    public static Map<String, String> jarVersionMap = new HashMap<String, String>();
    static{
        jarVersionMap.put("${artifactId-parent}", "0.0.1-SNAPSHOT");
        jarVersionMap.put("${artifactId-config}", "0.0.1.0-SNAPSHOT");
        jarVersionMap.put("${artifactId-mapper}", "0.0.1.0-SNAPSHOT");
        jarVersionMap.put("${artifactId-model}", "0.0.1.0-SNAPSHOT");
        jarVersionMap.put("${artifactId-rest}", "0.0.1.0-SNAPSHOT");
        jarVersionMap.put("${artifactId-web}", "0.0.1.0-SNAPSHOT");
        jarVersionMap.put("${artifactId-service}", "0.0.1.0-SNAPSHOT");
    }
}
