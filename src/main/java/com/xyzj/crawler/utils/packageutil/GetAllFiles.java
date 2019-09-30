package com.xyzj.crawler.utils.packageutil;
 
import java.io.File;

/**
 * 遍历文件夹
 */
public class GetAllFiles {
 
    public static void main(String[] args) {
        //路径   这里写一个路径进去
        String path="F:\\QQ文档";
        //调用方法
        getFiles(path);
    }
 
    /**
     * 递归获取某路径下的所有文件，文件夹，并输出
     */
 
    public static void getFiles(String path) {
        File file = new File(path);
        // 如果这个路径是文件夹
        if (file.isDirectory()) {
            // 获取路径下的所有文件
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                // 如果还是文件夹 递归获取里面的文件 文件夹
                if (files[i].isDirectory()) {
                    System.out.println("目录：" + files[i].getPath());
                    getFiles(files[i].getPath());
                } else {
                    System.out.println("文件：" + files[i].getPath());
                }
 
            }
        } else {
            System.out.println("文件：" + file.getPath());
        }
    }
}