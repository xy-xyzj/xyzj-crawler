package com.xyzj.crawler.spidertask.getip;

import com.xyzj.crawler.utils.proxyip.IPModel.IPMessage;
import com.xyzj.crawler.utils.proxyip.database.MyRedis;
import com.xyzj.crawler.utils.proxyip.htmlparse.URLFecter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by paranoid on 17-4-13.
 *
 *
 */

public class SaveFromHtml {
    public static void main(String[] args) {
        MyRedis redis = new MyRedis();
        //存放爬取下来的ip信息
        List<IPMessage> ipMessages = new ArrayList<>();

        //首先使用本机ip爬取xici代理网第一页
        ipMessages = URLFecter.urlParse(ipMessages,50);

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
}
