package com.bshf.util.timeutils;

import com.bshf.util.abs.SpiderRuleAbstract;
import com.bshf.util.entity.GoodsPO;
import com.bshf.util.IPModel.IPMessage;
import com.bshf.util.orther.SpiderTaskMultThread;
import com.bshf.util.database.MyRedis;
import com.bshf.util.httpbrowser.MyHttpResponse;
import com.bshf.util.savetomysql.ServiceImpl;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author liuyangyang@bshf360.com
 * @since 2018-01-31 09:10
 */
public class TargetIp extends SpiderRuleAbstract {

    @Override
    public void runSpider(GoodsPO goodsPO)  {
        try {

            // 设置代理IP
            MyRedis redis = new MyRedis();
            //从redis数据库中随机拿出一个IP
            IPMessage ipMessage = redis.getIPByList();
            redis.close();
            String htmlSource = MyHttpResponse.getHtml(goodsPO.getWebUrl(), ipMessage.getIPAddress(), ipMessage.getIPPort());
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
            TargetIp targetIp = new TargetIp();
            cachedThreadPool.execute(new SpiderTaskMultThread(goodsPO, targetIp));
        }
        cachedThreadPool.shutdown();

    }
}
