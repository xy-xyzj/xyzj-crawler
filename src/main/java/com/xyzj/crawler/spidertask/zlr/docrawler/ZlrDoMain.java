package com.xyzj.crawler.spidertask.zlr.docrawler;

import avro.shaded.com.google.common.collect.Lists;
import avro.shaded.com.google.common.collect.Maps;
import com.xyzj.crawler.framework.entity.Goods;
import com.xyzj.crawler.framework.factory.SpiderRuleFactory;
import com.xyzj.crawler.framework.interfaces.ISpiderRule;
import com.xyzj.crawler.framework.runnable.SpiderRunnable;
import com.xyzj.crawler.spidertask.zlr.dorule.SsqDetailSpiderRule;
import com.xyzj.crawler.utils.gethtmlstring.HttpResponseUtil;
import com.xyzj.crawler.utils.parsehtmlstring.RegexUtil;
import com.xyzj.crawler.utils.savetomysql.SaveToMysql;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ZlrDoMain {


	public static void main  (String[] args) throws Exception {
		System.out.println("开始抓取区域数据");

		//第一步 取得源码
		String baseUrl = "http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2018/";
		Map<String, String> headerInfos = new HashMap<>();
		String htmlSource = HttpResponseUtil.getHtml(baseUrl,"gb2312",headerInfos);
		if (null == htmlSource || htmlSource.contains("Not Found") || htmlSource.contains("无法访问此网站")
				|| htmlSource.contains("你所访问的页面就如那些遇害的同道") || htmlSource.contains("药品不存在！")) {
			return;
		}


		//第二步
		//提取url
		String numberRegexString = "<a href='(.*?).html'>";
		String nameRegexString = ".html'>(.*?)<br/>";
		List<String> numUtil = RegexUtil.getSubUtil(htmlSource, numberRegexString);
		List<String> nameUtil = RegexUtil.getSubUtil(htmlSource, nameRegexString);
		System.out.println("subUtil========"+numUtil);
		System.out.println("nameUtil========"+nameUtil);


		ArrayList<Goods> goodsList = Lists.newArrayList();
		for (int i = 0; i < numUtil.size(); i++) {
			//设置提供方
			Goods goods = new Goods();
			goods.setName(nameUtil.get(i));
			goods.setWebUrl(baseUrl+numUtil.get(i)+".html");
			goods.setOrderNum(numUtil.get(i));
			//第三步 往数据库中存
			SaveToMysql saveToMysql = new SaveToMysql();
			saveToMysql.saveToMasql("goods", goods);
			goodsList.add(goods);
		}

		ExecutorService executorService = Executors.newFixedThreadPool(3);
		for (Goods goods: goodsList) {
			HashMap<String, Object> param = Maps.newHashMap();
			param.put("webUrl", goods.getWebUrl());

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
