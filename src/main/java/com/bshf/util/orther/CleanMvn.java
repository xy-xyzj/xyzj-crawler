package com.bshf.util.orther;
import java.io.File;

public class CleanMvn {
    public static void main(String[] args){
        if(args.length != 1){
            print("使用方法错误，方法需要一个参数，参数为mvn本地仓库的路径");
        }
        findAndDelete(new File(args[0]));
    }

    public static boolean findAndDelete(File file){
        if(!file.exists()){
        } else if(file.isFile()){
            if(file.getName().endsWith("lastUpdated")){
                deleteFile(file.getParentFile());
                return true;
            }
        } else if(file.isDirectory()){
            File[] files = file.listFiles();
            for(File f : files){
                if(findAndDelete(f)){
                    break;
                }
            }
        }
        return false;
    }

    public static void deleteFile(File file){
        if(!file.exists()){
        } else if(file.isFile()){
            print("删除文件:" + file.getAbsolutePath());
            file.delete();
        } else if(file.isDirectory()){
            File[] files = file.listFiles();
            for(File f : files){
                deleteFile(f);
            }
            print("删除文件夹:" + file.getAbsolutePath());
            print("====================================");
            file.delete();
        }
    }

    public static void print(String msg){
        System.out.println(msg);
    }
}