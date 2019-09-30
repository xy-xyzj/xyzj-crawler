package com.xyzj.crawler.utils.importfrom;


import java.io.File;
import java.nio.file.Files;

/**
 * 文件夹拷贝(文件内含有文件和文件夹)
 */
public class FileCopyUtil {



    //复制方法
    public static void copy(String filePath, String srcPath, String targetPath) throws Exception {
        //初始化文件复制
        File srcFile=new File(srcPath + filePath);

        //初始化文件目标
        File targetFile=new File(targetPath+filePath);
        if(!targetFile.getParentFile().exists()){
            targetFile.getParentFile().mkdirs();
        }
        //调用文件拷贝的方法
        targetFile.delete();
        Files.copy(srcFile.toPath(), targetFile.toPath());
    }


    //主入口
    public static void main(String[] args) throws Exception {
        //复制方法
        String filePath = "/src/main/java/com/xyzj/crawler/framework/defaults/DefaultM3u8SpiderRule.java";
        String srcPath = "/Users/liuyangyang/workspace/xyzj/xyzj-crawler";
        String targetPath = "/Users/liuyangyang/Downloads";
        copy(filePath,srcPath,targetPath);
        //打印完成
        System.out.println("文件拷贝完成!");
    }
}