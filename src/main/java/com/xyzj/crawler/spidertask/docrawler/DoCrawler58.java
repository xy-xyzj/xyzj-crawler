package com.xyzj.crawler.spidertask.docrawler;

import com.xyzj.crawler.framework.factory.SpiderRuleFactory;
import com.xyzj.crawler.framework.interfaces.ISpiderRule;
import com.xyzj.crawler.framework.runnable.SpiderRunnable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import lombok.extern.slf4j.Slf4j;

/**
 * 线程池抓取
 * 单个线程抓取某一个页面
 * 翻页的情况,开启线程池
 *
 * 58页面。
 *
 * @author lyy
 * @since 2018-10-27 18:14
 */
@Slf4j
public class DoCrawler58 {

    private static final int THREAD_COUNT = 10;

    public static void main(String[] args) {

        //总记录数
        Integer totalCount =3199;

        //每页数
        Integer pageSize = 30;

        //目标数量 107
        Integer pageCount = totalCount / pageSize + 1;

        //开启一个线程池
        ExecutorService executorService =
                Executors.newFixedThreadPool(THREAD_COUNT);

        //计数器锁
        CountDownLatch countDownLatch = new CountDownLatch(pageCount);

        for(int i=1;i<=pageCount;i++) {
            Map<String, Object> params = new HashMap<>();
            //目标url
            String webUrl = "https://cq.58.com/shouji/pn"+i+"/?PGTID=0d300024-0002-5274-9167-f56e706b72b9&ClickID=1";
            //提取规则正则
            String regexPattern = "src=\"//img.58cdn.com.cn/n/images/list/noimg.gif\" alt=\"(.*?)\"/>";
            params.put("webUrl", webUrl);
            params.put("regexPattern", regexPattern);
            params.put("countDownLatch", countDownLatch);

            //打印爬到第几页了
            params.put("pageNum", i);

            //spiderRule 规则
            ISpiderRule spiderRule = new SpiderRuleFactory().getInstance();
            //spiderRule 参数
            SpiderRunnable spiderRunnable = new SpiderRunnable(spiderRule,params);
            executorService.execute(spiderRunnable);
        }


        //等到任务执行完毕，关闭线程池。
        executorService.shutdown();
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
           log.error("出毛病了{}",e);
        }
        log.info("main --爬完了");

    }


}
