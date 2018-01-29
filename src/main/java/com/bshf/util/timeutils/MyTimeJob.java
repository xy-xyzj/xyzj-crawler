package com.bshf.util.timeutils;


import com.bshf.util.IPModel.IPMessage;
import com.bshf.util.ImportExcelUtil;
import com.bshf.util.database.MyRedis;
import com.bshf.util.htmlparse.URLFecter;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;

/**
 * Created by paranoid on 17-4-13.
 *
 * ip代理池里面最少保存200个代理ip
 *
 * 多线程主要考虑的就是合理的任务分配以及线程安全性。
 *
 * implements Job
 */

public class MyTimeJob extends TimerTask {
    MyRedis redis = new MyRedis();

    @Override
    public void run() {

        //存放爬取下来的ip信息
        List<IPMessage> ipMessages = new ArrayList<>();

        //首先使用本机ip爬取xici代理网第一页
        ipMessages = URLFecter.urlParse(ipMessages,3);

      /*  //对得到的IP进行筛选，将IP速度在两秒以内的并且类型是https的留下，其余删除
        ipMessages = IPFilter.Filter(ipMessages);

        //对拿到的ip进行质量检测，将质量不合格的ip在List里进行删除
        IPUtils.IPIsable(ipMessages);*/



        for(IPMessage ipMessage : ipMessages){
            System.out.println(ipMessage.getIPAddress());
            System.out.println(ipMessage.getIPPort());
            System.out.println(ipMessage.getIPType());
            System.out.println(ipMessage.getIPSpeed());
        }


        //首先清空redis数据库中的key
        redis.deleteKey("IPPool");

        //将爬取下来的ip信息写进Redis数据库中(List集合)
        redis.setIPToList(ipMessages);

        //从redis数据库中随机拿出一个IP
        IPMessage ipMessage = redis.getIPByList();
        System.out.println(ipMessage.getIPAddress());
        System.out.println(ipMessage.getIPPort());
        redis.close();
    }

    public static void main(String[] args) throws Exception{


        //	1-1 从Excel表中读取目标数据
        File file = new File("D:\\java\\workspace\\learn\\crawler\\record\\address.xlsx");
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
        redis.setIPToList(ipMessages);
        System.out.println(redis.getIPByList().getIPAddress()+":"+redis.getIPByList().getIPPort());
    }
}
