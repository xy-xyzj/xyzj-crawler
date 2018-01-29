package com.bshf.spider.dorule;

import com.bshf.spider.dorule.abs.SpiderRuleAbstract;
import com.bshf.spider.entity.GoodsPO;
import com.bshf.util.DataFormatStatus;
import com.bshf.util.RegexUtil;
import com.bshf.util.httpbrowser.MyHttpResponse;
import com.bshf.util.parser.JsoupHtmlParser;
import com.bshf.util.savetomysql.ServiceImpl;

import java.util.LinkedList;
import java.util.List;

/**
 * 用药参考网
 * http://drugs.medlive.cn/index.jsp
 * 根据关键字 取得链接 并取得其内容
 * */
public class SpiderRuleJianke extends SpiderRuleAbstract {




	@Override
	public void runSpider(GoodsPO goodsPO)  {
		try {

			String htmlSource = MyHttpResponse.getHtml(goodsPO.getWebUrl());

			//	1-2 输出查看效果
			//System.out.println("htmlSource=============="+htmlSource);


			//	2-1 提取有效内容
			//  .contain .picBox 取出链接列表
			List<String> targetListPattern = new LinkedList<String>();
			targetListPattern.add(".contain .picBox");
			List<String> targetList = JsoupHtmlParser.getNodeContentBySelector(htmlSource, targetListPattern, DataFormatStatus.TagAllContent, true);

			//  2-2输出查看
			//System.out.println("targetList"+targetList.toString());

			String targetString = targetList.get(0);
			String rgex = "product/(.*?).html";
			String subUtilSimple = RegexUtil.getSubUtilSimple(targetString, rgex);
			String myUrl = "https://www.jianke.com/product/" + subUtilSimple + ".html";
			//System.out.println(subUtilSimple);
			//System.out.println(myUrl);

			//  3-1 往数据库中存
			goodsPO.setProvide(myUrl);
			ServiceImpl goodsPOServiceImpl = new ServiceImpl();
			goodsPOServiceImpl.add("goods_jianke", goodsPO);

		}catch (Exception e){
			//不处理异常
		}

	}



	public static void main(String[] args) {
		GoodsPO goodsPO = new GoodsPO();
		String srcUrl = "https://search.jianke.com/prod?wd=%e9%98%bf%e5%bd%92%e5%85%bb%e8%a1%80%e7%b3%96%e6%b5%86&lg=1";
		goodsPO.setWebUrl(srcUrl);
		goodsPO.setOrderNum("1");
		SpiderRuleJianke spiderUtils = new SpiderRuleJianke();
		spiderUtils.runSpider(goodsPO);
	}
}

