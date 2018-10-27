package com.xyzj.crawler.utils.getip;


import com.xyzj.crawler.utils.proxyip.IPModel.IPMessage;
import com.xyzj.crawler.utils.proxyip.database.MyRedis;
import com.xyzj.crawler.utils.importfrom.ImportExcelUtil;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by paranoid on 17-4-13.
 *
 * ip代理池里面最少保存200个代理ip
 *
 * 多线程主要考虑的就是合理的任务分配以及线程安全性。
 *
 * implements Job
 */

public class SaveIPFromExcel {

    public static void main(String[] args) throws Exception{
        //	1-1 从Excel表中读取目标数据
        File file = new File("/Volumes/life/workspace/IdeaProjects/my-crawler/record/address.xlsx");
        FileInputStream fileInputStream = new FileInputStream(file);

        //	1-2 将Excel表中的记录保存在map对象中
        Map<String, String> excel2Map = new HashMap<String, String>();
        excel2Map.put("ip", "ip");
        excel2Map.put("port", "port");
        List<Map<String, Object>> excel2List = ImportExcelUtil.parseExcel(fileInputStream, file.getName(), excel2Map);

        // 1-3 将excel2List中的map封装到对象中
        List<IPMessage> ipMessages = new ArrayList<>();
        for (int i = 0; i < excel2List.size(); i++) {
            IPMessage ipMessage = new IPMessage();
            ipMessage.setIPAddress(excel2List.get(i).get("ip").toString());
            ipMessage.setIPPort(excel2List.get(i).get("port").toString());
            ipMessage.setIPSpeed("0.11");
            ipMessage.setIPType("HTTPS");
            ipMessages.add(ipMessage);
        }

        MyRedis redis = new MyRedis();
        redis.deleteKey("IPPool");
        redis.setIpToList(ipMessages);
        System.out.println(redis.getIpByList().getIPAddress()+":"+redis.getIpByList().getIPPort());
    }
}
