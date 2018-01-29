package com.bshf.spider.dorule;

import com.bshf.spider.dorule.abs.SpiderRuleAbstract;
import com.bshf.spider.entity.GoodsPO;
import com.bshf.util.DataFormatStatus;
import com.bshf.util.HttpUtil;
import com.bshf.util.parser.JsoupHtmlParser;
import com.bshf.util.savetomysql.ServiceImpl;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

public class SpiderRuleZhongYaoCai360Detail extends SpiderRuleAbstract {
	private static 	Pattern  patternLink = Pattern.compile("href=\"(.*?)\">");
	private static  Pattern patternName = Pattern.compile("<em>(.*?)</em>");
	private static  Pattern patternNameId = Pattern.compile("item.jd.com/(.*?).html");
	private static  Pattern patternPrice = Pattern.compile("\"p\":\"(.*?)\"");

	@Override
	public void runSpider(String srcUrl) {
		//1-1. 取得网页源码
		String htmlSource = HttpUtil.getPageCode(srcUrl, "gb2312");
		//1-2. 页面不存在
		if (null == htmlSource || htmlSource.contains("Not Found") || htmlSource.contains("无法访问此网站")
				|| htmlSource.contains("你所访问的页面就如那些遇害的同道") || htmlSource.contains("药品不存在！")) {
			return;
		}


		//2-1. 取出名称
		List<String> nameList = new LinkedList<String>();
		nameList.add(".post-title");
		List<String> nameTxt = JsoupHtmlParser.getNodeContentBySelector(htmlSource, nameList, DataFormatStatus.CleanTxt, true);
		System.out.println(nameTxt.toString());

		//2-2. 取出内容
		List<String> contentList = new LinkedList<String>();
		contentList.add(".spider");
		List<String> contentTxt = JsoupHtmlParser.getNodeContentBySelector(htmlSource, contentList, DataFormatStatus.CleanTxt, true);
		System.out.println(contentTxt.toString());

		//  第三步 往数据库中存
		GoodsPO goodsPO = new GoodsPO();
		goodsPO.setName(nameTxt.toString());
		//  做字符替换 好在Excel表中分列
		goodsPO.setProvide(contentTxt.toString().replace("【","|【").replace("[","|[").replace("《中药大辞典》","^《中药大辞典》").replace("《中华本草》","^《中华本草》"));
		ServiceImpl goodsPOServiceImpl = new ServiceImpl();
		goodsPOServiceImpl.add("goods", goodsPO);
	}




	public static void main(String[] args) {
		SpiderRuleZhongYaoCai360Detail spiderUtils = new SpiderRuleZhongYaoCai360Detail();
		String srcUrl = "http://zhongyaocai360.com/a/aertaiziwan.html#6089";
		spiderUtils.runSpider(srcUrl);

	}
}