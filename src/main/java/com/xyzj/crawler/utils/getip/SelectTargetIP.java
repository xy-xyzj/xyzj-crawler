/*
package com.xyzj.crawler.utils.getip;

import com.xyzj.crawler.framework.abstracts.AbstractSpiderRule;
import com.xyzj.crawler.framework.entity.Goods;
import com.xyzj.crawler.utils.proxyip.IPModel.IPMessage;
import com.xyzj.crawler.framework.runnable.SpiderRunnable;
import com.xyzj.crawler.utils.proxyip.database.MyRedis;
import com.xyzj.crawler.utils.gethtmlstring.MyHttpResponse;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

*/
/**
 * @author liuyangyang@bshf360.com
 * @since 2018-01-31 09:10
 *//*

public class SelectTargetIP extends AbstractSpiderRule {

    @Override
    public void runSpider(Goods goods)  {
        try {

            // 设置代理IP
            MyRedis redis = new MyRedis();
            //从redis数据库中随机拿出一个IP
            IPMessage ipMessage = redis.getIpByList();
            redis.close();
            String htmlSource = HttpResponseUtil.getHtmlWithProxyIp(goods.getWebUrl(),"utf-8", ipMessage.getIPAddress(), ipMessage.getIPPort(),null);
            if (!StringUtils.isEmpty(htmlSource)) {
                goods.setName(ipMessage.getIPAddress());
                goods.setProvide(ipMessage.getIPPort());
                ServiceImpl goodsPOServiceImpl = new ServiceImpl();
                goodsPOServiceImpl.add("goods_ip", goods);
            }

        } catch (Exception e) {
            //不处理异常
        }


    }

    public static void main(String[] args) {

        // 存放目标连接
        List<Goods> goodsList = new ArrayList<>();
        for (int i = 0; i < 300000; i++) {
            String currentPage = "http://maoxian.baixing.com";
            Goods goods = new Goods();
            goods.setWebUrl(currentPage);
            goodsList.add(goods);
        }

        ExecutorService cachedThreadPool = Executors.newFixedThreadPool(100);
        for (final Goods goods : goodsList) {
            SelectTargetIP selectTargetIP = new SelectTargetIP();
            cachedThreadPool.execute(new SpiderRunnable(goods, selectTargetIP));
        }
        cachedThreadPool.shutdown();

    }
}
*/
