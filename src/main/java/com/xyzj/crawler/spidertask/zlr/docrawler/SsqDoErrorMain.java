package com.xyzj.crawler.spidertask.zlr.docrawler;

import avro.shaded.com.google.common.collect.Maps;
import com.xyzj.crawler.framework.factory.SpiderRuleFactory;
import com.xyzj.crawler.framework.interfaces.ISpiderRule;
import com.xyzj.crawler.framework.runnable.SpiderRunnable;
import com.xyzj.crawler.spidertask.zlr.dorule.SsqDetailSpiderRule;
import com.xyzj.crawler.utils.savetomysql.SaveToMysql;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SsqDoErrorMain {


	public static void main  (String[] args) throws Exception {
		System.out.println("尝试从新拉去数据");
		reTry();
	}

	public static void reTry() {
		SaveToMysql query = new SaveToMysql();
		List<Map<String, Object>> mapList = query.queryBySql("select * from ungoods");
		// 删除
		query.executeBySql("delete from ungoods");
		//计数器锁
		CountDownLatch countDownLatch = new CountDownLatch(mapList.size());
		if (mapList.size() > 0) {
			ExecutorService executorService = Executors.newFixedThreadPool(3);
			for (Map<String, Object> map : mapList) {
				Object url = map.get("webUrl");
				if (url != null && url.toString().trim().length() > 0) {
					// 存在数据
					System.out.println(url);
					HashMap<String, Object> param = Maps.newHashMap();
					param.put("webUrl", url);
					param.put("countDownLatch", countDownLatch);
					//抓取器
					SsqDetailSpiderRule ssqDetailSpiderRule = new SsqDetailSpiderRule();
					ISpiderRule spiderRule = new SpiderRuleFactory(ssqDetailSpiderRule).getInstance();
					//spiderRule 参数
					SpiderRunnable spiderRunnable = new SpiderRunnable(spiderRule,param);
					executorService.execute(spiderRunnable);
				}
			}
			//等到任务执行完毕，关闭线程池。
			executorService.shutdown();
			try {
				countDownLatch.await();
			} catch (InterruptedException e) {
			}
			// 循环
			reTry();
		} else {
			System.out.println("无失败记录");
		}
	}

}
