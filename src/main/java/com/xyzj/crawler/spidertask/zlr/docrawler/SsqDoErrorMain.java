package com.xyzj.crawler.spidertask.zlr.docrawler;

import com.xyzj.crawler.framework.entity.Param;
import com.xyzj.crawler.framework.factory.SpiderRuleFactory;
import com.xyzj.crawler.framework.interfaces.ISpiderRule;
import com.xyzj.crawler.framework.runnable.SpiderRunnable;
import com.xyzj.crawler.spidertask.zlr.dorule.SsqDetailSpiderRule;
import com.xyzj.crawler.utils.savetomysql.SaveToMysql;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Slf4j
public class SsqDoErrorMain {
    public static void main(String[] args) throws Exception {
        log.info("尝试从新拉去数据");
        reTry();
    }

    public static void reTry() {
        SaveToMysql query = new SaveToMysql();
        List<Map<String, Object>> mapList = query.queryBySql("select * from ungoods");
        if (CollectionUtils.isEmpty(mapList)) {
            log.info("无失败记录......");
            return;
        }
        // 删除
        query.executeBySql("delete from ungoods");
        //计数器锁
        CountDownLatch countDownLatch = new CountDownLatch(mapList.size());
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        for (Map<String, Object> map : mapList) {
            String url = String.valueOf(map.get("webUrl"));
            if (!StringUtils.isEmpty(url)) {
                // 存在数据
                log.info("url={}", url);
                Param param = new Param();
                param.setWebUrl(url);
                param.setCountDownLatch(countDownLatch);

                //抓取器
                SsqDetailSpiderRule ssqDetailSpiderRule = new SsqDetailSpiderRule();
                ISpiderRule spiderRule = new SpiderRuleFactory(ssqDetailSpiderRule).getInstance();

                //spiderRule 参数
                SpiderRunnable spiderRunnable = new SpiderRunnable(spiderRule, param);
                executorService.execute(spiderRunnable);
            }
        }
        //等到任务执行完毕，关闭线程池。
        executorService.shutdown();
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            log.info("出毛病......");
        }
        // 循环
        reTry();

    }

}
