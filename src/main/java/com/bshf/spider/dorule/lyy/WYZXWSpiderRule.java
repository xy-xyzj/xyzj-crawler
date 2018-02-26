package com.bshf.spider.dorule.lyy;

import com.bshf.util.IPModel.IPMessage;
import com.bshf.util.abs.SpiderRuleAbstract;
import com.bshf.util.database.MyRedis;
import com.bshf.util.entity.GoodsPO;
import com.bshf.util.httpbrowser.MyHttpResponse;
import com.bshf.util.orther.SpiderTaskMultThread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author lyy
 * @since 2018-02-08 19:28
 */
public class WYZXWSpiderRule extends SpiderRuleAbstract {

    @Override
    public void runSpider(GoodsPO goodsPO)  {
        try {

            // 设置代理IP
            MyRedis redis = new MyRedis();
            //从redis数据库中随机拿出一个IP
            IPMessage ipMessage = redis.getIPByList();
            redis.close();
            String htmlSource = MyHttpResponse.getHtml(goodsPO.getWebUrl(), ipMessage.getIPAddress(), ipMessage.getIPPort());

            System.out.println(htmlSource);
        }catch (Exception e){
            //不处理异常
        }
    }

    public static void main(String[] args) {

        //2-1启动线程进行数据爬取
        ExecutorService cachedThreadPool = Executors.newFixedThreadPool(50);

        for (int i=0;i<100;i++) {
            GoodsPO goodsPO = new GoodsPO();
            String srcUrl = "http://www.51zxw.net/study_2016.asp?bs=study2017&vipid=6641319&come_url=https%3A%2F%2Fwww.baidu.com%2F";
            goodsPO.setWebUrl(srcUrl);
            goodsPO.setOrderNum("1");
            WYZXWSpiderRule spiderUtils = new WYZXWSpiderRule();
            spiderUtils.runSpider(goodsPO);
            cachedThreadPool.execute(new SpiderTaskMultThread(goodsPO, spiderUtils));
        }
        cachedThreadPool.shutdown();


    }


}
