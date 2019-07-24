package com.xxxlboot.common.gencode.util;


import com.xxxlboot.common.util.StringUtil;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLDecoder;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Explain:     []
 * Date:        [2018/09/28
 * Coder:       [Easy]
 * Version:     [1.0]
 */
public class FileUtil {

    /**
     * 遍历给定的根路径,将该路径下以及所有子路径的所有文件和文件夹保存到一个栈中返回.
     * 采用后进先出的遍历顺序保证了文件对象出现在父文件夹之前
     * 返回的栈不包含根目录
     * @param root 根路径
     * @return Stack
     */
    public static Stack<File> traversePathGetAllFileToStack(File root) {
        if (root == null) {
            return null;
        }
        Stack<File> pathStack = new Stack<File>();//存储文件夹
        Stack<File> fileStack = new Stack<File>();//存储遍历的所有数据
        pathStack.push(root);
        while (!pathStack.isEmpty()) {
            File pop = pathStack.pop();
            if (pop == null) {
                continue;
            }
            if (pop.isDirectory()) {
                File[] files = pop.listFiles();
                for (File file : files) {
                    if (file != null) {
                        if (file.isDirectory()) {
                            pathStack.push(file);
                        }
                        fileStack.push(file);
                    }
                }
            }
        }
        return fileStack;
    }

    /**
     * 遍历给定的根路径,将该路径下以及所有子路径的所有文件和文件夹保存到一个队列中返回.
     * 采用先进先出的遍历顺序保证了文件夹出现在子文件之前
     * 返回的队列不包含根目录
     * @param root 根路径
     * @return Queue
     */
    public static Queue<File> traversePathGetAllFileToQueue(File root) {
        if (root == null) {
            return null;
        }
        Queue<File> pathQueue = new LinkedList<File>();//存储文件夹
        Queue<File> fileQueue = new LinkedList<File>();//存储遍历的所有数据
        pathQueue.add(root);
        while (!pathQueue.isEmpty()) {
            File pop = pathQueue.poll();
            if (pop == null) {
                continue;
            }
            if (pop.isDirectory()) {
                File[] files = pop.listFiles();
                for (File file : files) {
                    if (file != null) {
                        if (file.isDirectory()) {
                            pathQueue.add(file);
                        }
                        fileQueue.add(file);
                    }
                }
            }
        }
        return fileQueue;
    }

    /**
     * 遍历给定的根路径,将该路径下以及所有子路径的所有文件和文件夹保存到一个队列中返回.
     * 采用先进先出的遍历顺序保证了文件夹出现在子文件之前
     * 返回的队列不包含根目录
     * @param root 根路径
     * @return Queue
     */
    public static Queue<File> traversePathGetAllFileToQueue(String root) {
        if (root == null) {
            return null;
        }
        return traversePathGetAllFileToQueue(new File(root));
    }

    /**
     * 遍历给定的根路径,将该路径下以及所有子路径的所有文件和文件夹保存到一个栈中返回.
     * 采用后进先出的遍历顺序保证了文件对象出现在父文件夹之前
     * 返回的栈不包含根目录
     * @param root 根路径
     * @return Stack
     */
    public static Stack<File> traversePathGetAllFileToStack(String root) {
        if (root == null) {
            return null;
        }
        return traversePathGetAllFileToStack(new File(root));
    }

    /**
     * 在路径前添加路径分隔符"/",返回处理成功的新字符串
     * 若path已经为分隔符起始则返回原字符串
     * 若path为null,返回null
     * @param path 待处理的路径字符串
     * @return
     */
    public static String addFrontSeparator(String path) {
        if (path == null) {
            return null;
        }
        if (path.startsWith("/")) {
            return path;
        }
        return "/" + path;
    }

    /**
     * 在路径后添加路径分隔符"/",返回处理成功的新字符串
     * 若path已经为分隔符结尾则返回原字符串
     * 若path为null,返回null
     * @param path 待处理的路径字符串
     * @return
     */
    public static String addLastSeparator(String path) {
        if (path == null) {
            return null;
        }
        if (path.endsWith("/")) {
            return path;
        }
        return path + "/";
    }

    /**
     * 在路径前后添加路径分隔符"/",返回处理成功的新字符串
     * 该方法不会重复添加分隔符
     * 若path为null,返回null
     * @param path 待处理的路径字符串
     * @return
     */
    public static String addSeparator(String path) {
        return addLastSeparator(addFrontSeparator(path));
    }

    /**
     * 打开文件管理器
     * @param path
     */
    public static void openFileManager(String path) {
        String osName = System.getProperty("os.name");
        if(osName != null) {
            try {
                if(osName.contains("Mac")) {
                    Runtime.getRuntime().exec("open " + path);
                } else if(osName.contains("Windows")) {
                    Runtime.getRuntime().exec("cmd /c start " + path);
                } else {
//                    throw new RuntimeException("不支持的操作系统:" + osName);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }


    /**
     * 遍历指定包,获取所有文件name(路径)
     * @param packageName
     * @return
     */
    public static List<String> traversePackageGetAllFileName(String packageName) {
        List<String> fileNames = null;
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        packageName = StringUtil.trim(packageName);
        if (packageName.startsWith("/")) {
            packageName = packageName.substring(1);
        }
        String packagePath = packageName.replace(".", "/");
        URL url = loader.getResource(packagePath);
        if (url != null) {
            String type = url.getProtocol();
            String path = null;
            try {
                path = URLDecoder.decode(url.getPath(), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            if ("file".equals(type)) {
                fileNames = fileGetAllName(path);//文件资源在文件系统中
            } else if ("jar".equals(type)) {
                fileNames = jarGetAllName(path);//文件资源在jar包中
            }
        } else {
            fileNames = jarGetAllNames(((URLClassLoader) loader).getURLs(), packagePath);
        }
        return fileNames;
    }

    private static List<String> jarGetAllNames(URL[] urls, String packagePath) {
        List<String> myClassName = new ArrayList<String>();
        if (urls != null) {
            for (int i = 0; i < urls.length; i++) {
                URL url = urls[i];
                String urlPath = null;
                try {
                    urlPath = URLDecoder.decode(url.getPath(), "UTF-8");
                } catch (Exception e) {
                }
                if (!urlPath.endsWith("jar")) {
                    continue;
                }
                String jarPath = urlPath + "!/" + packagePath;
                myClassName.addAll(jarGetAllName(jarPath));
            }
        }
        return myClassName;
    }

    private static List<String> jarGetAllName(String path) {
        List<String> names = new ArrayList<String>();
        String[] jarInfo = path.split("!");
        String jarFilePath = jarInfo[0].substring(jarInfo[0].indexOf("/"));
        String packagePath = jarInfo[1].substring(1);
        try {
            JarFile jarFile = new JarFile(jarFilePath);
            Enumeration<JarEntry> entrys = jarFile.entries();
            while (entrys.hasMoreElements()) {
                JarEntry jarEntry = entrys.nextElement();
                String entryName = jarEntry.getName();
                if (entryName.startsWith(packagePath)) {
                    names.add(entryName);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return names;
    }

    private static List<String> fileGetAllName(String path) {
        List<String> names = new ArrayList<String>();
        File file = new File(path);
        File[] childFiles = file.listFiles();
        for (File childFile : childFiles) {
            if (childFile.isDirectory()) {
                names.addAll(fileGetAllName(childFile.getPath()));
            } else {
                names.add(childFile.getPath());
            }
        }
        return names;
    }

    /**
     * 创建文件目录
     * @param file
     */
    public static boolean mkdirs(File file) {
        boolean b = false;
        if (file != null) {
            if (file.exists()) {
                return true;
            }
            b = file.mkdirs();
            file.delete();
        }
        return b;
    }
   /* public static void main(String[] args) {
        List<String> strings = traversePackageGetAllFileName("/");
        for (String s :
                strings) {


        }
    }*/
}
