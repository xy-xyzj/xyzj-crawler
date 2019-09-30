package com.xyzj.crawler.utils.packageutil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author lyy
 * @since 2019-09-18 13:01
 * 读取excel里面的文件到指定目录
 */
public class PackageGetClassUtil {
    private static final String SRC_PATH = "C:\\Users\\quling\\Desktop\\java";
    private static final String TARGET_PATH = "D:\\IdeaProjects\\demo2\\out\\production\\classes";

    public static void main(String[] args) throws Exception {
        try{
            fileReplace(new File(SRC_PATH),SRC_PATH,TARGET_PATH);
        }catch(Exception e){
            e.printStackTrace();
        }
        System.out.println("类提取完成");

    }

    private static void fileReplace(File base, String sourcePath, String targetPath) throws IOException {
        if (!base.exists() || base.getName().contains(".txt")){
            return;
        }
        if (base.isDirectory()) {
            File[] files = base.listFiles();
            for (File file : files) {
                fileReplace(file, sourcePath, targetPath);
            }
        } else {
            String path = base.getPath();
            String tempPath = path = path.replace(".java", ".class");
            System.out.println(base.getName());
            base.delete();
            path = targetPath + path.substring(sourcePath.length());
            FileInputStream in = new FileInputStream(new File(path));
            FileOutputStream out = new FileOutputStream(new File(tempPath));
            int i;
            while ((i = in.read()) != -1) {
                out.write(i);
            }
            in.close();
            out.close();
        }
    }
}
