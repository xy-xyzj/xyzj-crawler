package com.xyzj.crawler.spidertask.zlr.docrawler;

import avro.shaded.com.google.common.collect.Maps;
import com.xyzj.crawler.framework.factory.SpiderRuleFactory;
import com.xyzj.crawler.framework.interfaces.ISpiderRule;
import com.xyzj.crawler.framework.runnable.SpiderRunnable;
import com.xyzj.crawler.spidertask.zlr.dorule.SsqDetailSpiderRule;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ZlrDoErrorMain {


	public static void main  (String[] args) throws Exception {
		System.out.println("开始抓取区域数据");

		List<String> list = new ArrayList<>();
		list.add("http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2018/14/01/22/140122204.html");
		list.add("http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2018/15/25/25/152525400.html");
		list.add("http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2018/15/05/21/150521200.html");
		list.add("http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2018/22/01/83/220183004.html");
		list.add("http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2018/13/08/130822.html");
		list.add("http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2018/23/11/24/231124409.html");
		list.add("http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2018/13/09/84/130984209.html");
		list.add("http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2018/32/13/02/321302110.html");
		list.add("http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2018/33/07/02/330702104.html");
		list.add("http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2018/34/11/24/341124109.html");
		list.add("http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2018/34/12/21/341221002.html");
		list.add("http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2018/35/01/04/350104008.html");
		list.add("http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2018/37/01/12/370112007.html");
		list.add("http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2018/37/02/15/370215100.html");
		list.add("http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2018/35/01/12/350112103.html");
		list.add("http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2018/37/07/83/370783106.html");
		list.add("http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2018/41/05/81/410581104.html");
		list.add("http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2018/35/01/24/350124110.html");
		list.add("http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2018/42/03/23/420323206.html");
		list.add("http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2018/35/01/25/350125206.html");
		list.add("http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2018/35/01/81/350181108.html");
		list.add("http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2018/42/13/81/421381109.html");
		list.add("http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2018/44/05/23/440523101.html");
		list.add("http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2018/44/12/24/441224116.html");
		list.add("http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2018/44/07/81/440781100.html");
		list.add("http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2018/45/04/22/450422106.html");
		list.add("http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2018/45/04/23/450423201.html");
		list.add("http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2018/45/10/22/451022100.html");
		list.add("http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2018/45/14/24/451424102.html");
		list.add("http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2018/51/01/31/510131101.html");
		list.add("http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2018/45/10/31/451031213.html");
		list.add("http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2018/53/23/23/532323102.html");
		list.add("http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2018/53/23/26/532326104.html");
		list.add("http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2018/61/01/14/610114101.html");
		list.add("http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2018/51/34/31/513431200.html");
		list.add("http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2018/64/02/21/640221200.html");
		list.add("http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2018/51/34/25/513425110.html");
		list.add("http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2018/65/30/22/653022400.html");


		ExecutorService executorService = Executors.newFixedThreadPool(3);
		for (String url: list) {
			HashMap<String, Object> param = Maps.newHashMap();
			param.put("webUrl", url);
			//抓取器
			SsqDetailSpiderRule ssqDetailSpiderRule = new SsqDetailSpiderRule();
			ISpiderRule spiderRule = new SpiderRuleFactory(ssqDetailSpiderRule).getInstance();
			//spiderRule 参数
			SpiderRunnable spiderRunnable = new SpiderRunnable(spiderRule,param);
			executorService.execute(spiderRunnable);
		}
		//等到任务执行完毕，关闭线程池。
		executorService.shutdown();
	}

}
