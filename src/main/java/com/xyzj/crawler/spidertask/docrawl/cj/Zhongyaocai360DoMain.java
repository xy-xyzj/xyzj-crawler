package com.xyzj.crawler.spidertask.docrawl.cj;

import com.xyzj.crawler.framework.abstracts.SpiderRuleAbstract;
import com.xyzj.crawler.framework.entity.GoodsPO;
import com.xyzj.crawler.framework.threads.SpiderTaskMultThread;
import com.xyzj.crawler.spidertask.dorule.cj.Zhongyaocai360DetailSpiderRule;
import com.xyzj.crawler.utils.gethtmlstring.MyHttpResponse;
import com.xyzj.crawler.utils.parsehtmlstring.JsoupHtmlParser;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 中医宝典 > 中药材 > 《全国中草药汇编》取得列表
 * http://zhongyaocai360.com/quanguozhongcaoyaohuibian/
 *
 * */
public class Zhongyaocai360DoMain extends SpiderRuleAbstract {

	@Override
	public void runSpider(GoodsPO goodsPO)  {
		try {
			// 1-1 取得页面元素
			String htmlSource = MyHttpResponse.getMyHtml(goodsPO.getWebUrl());

			// 1-2 输出查看效果
			// System.out.println("htmlSource=============="+htmlSource);

			// 2-1 提取有效内容
			// 提取所有的a标签
			List<String> allHref = JsoupHtmlParser.getAllHref(htmlSource);



			// 2-2留下有效连接
			List<String> myAllHref = new ArrayList<>();
			for (String item:allHref) {
				if(!item.endsWith("/")){
					myAllHref.add(item);
				}
			}
			List<GoodsPO> goodsPOList = new ArrayList<>();
			for (int i = 0; i <myAllHref.size(); i++) {
				GoodsPO myGoodsPO = new GoodsPO();
				myGoodsPO.setOrderNum(String.valueOf(i+1));
				myGoodsPO.setWebUrl(myAllHref.get(i));
				goodsPOList.add(myGoodsPO);
			}

			//多线程爬取数据
			ExecutorService cachedThreadPool = Executors.newFixedThreadPool(5);
			for (final GoodsPO goodsPOItem : goodsPOList) {
				Zhongyaocai360DetailSpiderRule spiderRule = new Zhongyaocai360DetailSpiderRule();
				cachedThreadPool.execute(new SpiderTaskMultThread( goodsPOItem, spiderRule));
			}
			cachedThreadPool.shutdown();

		}catch (Exception e){
			//不处理异常
		}

	}

	public static void main(String[] args) {
		GoodsPO goodsPO = new GoodsPO();
		String srcUrl = "http://zhongyaocai360.com/quanguozhongcaoyaohuibian/";
		goodsPO.setWebUrl(srcUrl);
		Zhongyaocai360DoMain spiderUtils = new Zhongyaocai360DoMain();
		spiderUtils.runSpider(goodsPO);
	}
}

