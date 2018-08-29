package com.xyzj.crawler.spidertask.getip;

import com.xyzj.crawler.framework.abstracts.SpiderRuleAbstract;
import com.xyzj.crawler.framework.entity.GoodsPO;
import com.xyzj.crawler.utils.proxyip.IPModel.IPMessage;
import com.xyzj.crawler.framework.threads.SpiderTaskMultThread;
import com.xyzj.crawler.utils.proxyip.database.MyRedis;
import com.xyzj.crawler.utils.gethtmlstring.MyHttpResponse;
import com.xyzj.crawler.framework.savetomysql.ServiceImpl;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author liuyangyang@bshf360.com
 * @since 2018-01-31 09:10
 */
public class SelectTargetIP extends SpiderRuleAbstract {

    @Override
    public void runSpider(GoodsPO goodsPO)  {
        try {

            // 设置代理IP
            MyRedis redis = new MyRedis();
            //从redis数据库中随机拿出一个IP
            IPMessage ipMessage = redis.getIpByList();
            redis.close();
            String htmlSource = MyHttpResponse.getHtmlWithProxyIp(goodsPO.getWebUrl(),"utf-8", ipMessage.getIPAddress(), ipMessage.getIPPort(),null);
            if (!StringUtils.isEmpty(htmlSource)) {
                goodsPO.setName(ipMessage.getIPAddress());
                goodsPO.setProvide(ipMessage.getIPPort());
                ServiceImpl goodsPOServiceImpl = new ServiceImpl();
                goodsPOServiceImpl.add("goods_ip", goodsPO);
            }

        } catch (Exception e) {
            //不处理异常
        }


    }

    public static void main(String[] args) {

        // 存放目标连接
        List<GoodsPO> goodsPOList = new ArrayList<>();
        for (int i = 0; i < 300000; i++) {
            String currentPage = "http://maoxian.baixing.com";
            GoodsPO goodsPO = new GoodsPO();
            goodsPO.setWebUrl(currentPage);
            goodsPOList.add(goodsPO);
        }

        ExecutorService cachedThreadPool = Executors.newFixedThreadPool(100);
        for (final GoodsPO goodsPO : goodsPOList) {
            SelectTargetIP selectTargetIP = new SelectTargetIP();
            cachedThreadPool.execute(new SpiderTaskMultThread(goodsPO, selectTargetIP));
        }
        cachedThreadPool.shutdown();

    }
}
