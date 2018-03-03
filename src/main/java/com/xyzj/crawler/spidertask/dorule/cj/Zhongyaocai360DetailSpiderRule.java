package com.xyzj.crawler.spidertask.dorule.cj;

import com.xyzj.crawler.framework.abstracts.SpiderRuleAbstract;
import com.xyzj.crawler.framework.entity.GoodsPO;
import com.xyzj.crawler.framework.savetomysql.ServiceImpl;
import com.xyzj.crawler.utils.gethtmlstring.MyHttpResponse;
import com.xyzj.crawler.utils.parsehtmlstring.DataFormatStatus;
import com.xyzj.crawler.utils.parsehtmlstring.JsoupHtmlParser;
import com.xyzj.crawler.utils.parsehtmlstring.RegexUtil;

import java.util.LinkedList;
import java.util.List;

/**
 * 中医宝典 > 中药材 > 中药材 拼音：A
 * http://zhongyaocai360.com/a/aertaiduolangju.html#4600
 *
 * */
public class Zhongyaocai360DetailSpiderRule extends SpiderRuleAbstract {

	@Override
	public void runSpider(GoodsPO goodsPO)  {
		try {
			// 1-1 取得页面元素
			String htmlSource = MyHttpResponse.getMyHtml(goodsPO.getWebUrl());

			// 1-2 输出查看效果
			System.out.println("htmlSource=============="+htmlSource);


			// 2-1 提取爬取内容
			// .spidertask
			List<String> targetDetailListPattern = new LinkedList<String>();
			targetDetailListPattern.add(".spidertask");
			List<String> targetDetailList = JsoupHtmlParser.getNodeContentBySelector(htmlSource, targetDetailListPattern, DataFormatStatus.CleanTxt, true);

			// 2-2截取商品名称
			// <h1 class="post-title">阿尔太多榔菊<a
			String rgex = "<h1 class=\"post-title\">(.*?)<a";
			String subUtilSimple = RegexUtil.getSubUtilSimple(htmlSource, rgex);

			// 2-2输出查看
			System.out.println(targetDetailList.toString().replace("【","|【"));
			System.out.println(subUtilSimple);


			// 3-1 往数据库中存
			// 做字符替换 好在Excel表中分列
			goodsPO.setName(subUtilSimple);
			goodsPO.setProvide(targetDetailList.toString().replace("【","|【"));

			ServiceImpl goodsPOServiceImpl = new ServiceImpl();
			goodsPOServiceImpl.add("goods_zhongyaocai360", goodsPO);

		}catch (Exception e){
			//不处理异常
		}

	}

	public static void main(String[] args) {
		GoodsPO goodsPO = new GoodsPO();
		String srcUrl = "http://zhongyaocai360.com/a/aertaiduolangju.html#4600";
		goodsPO.setWebUrl(srcUrl);
		Zhongyaocai360DetailSpiderRule spiderUtils = new Zhongyaocai360DetailSpiderRule();
		spiderUtils.runSpider(goodsPO);
	}
}

