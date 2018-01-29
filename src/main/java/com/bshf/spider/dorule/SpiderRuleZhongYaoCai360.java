package com.bshf.spider.dorule;

import com.bshf.spider.dorule.abs.SpiderRuleAbstract;
import com.bshf.spider.entity.ExcelOut;
import com.bshf.util.ExportExcelUtil;
import com.bshf.util.HttpUtil;
import com.bshf.util.parser.JsoupHtmlParser;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class SpiderRuleZhongYaoCai360 extends SpiderRuleAbstract {
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
		//1-3. 打出来看看
		//System.out.println(htmlSource);

		//2-1. 提取页面的a标签
		List<String> hrefList = JsoupHtmlParser.getAllHref(htmlSource);
		//2-2.将a标签导出到Excel表
		List<ExcelOut> excelOutList = hrefList.stream().map(e -> {
			ExcelOut excelOut = new ExcelOut();
			excelOut.setName(e);
			excelOut.setClazz("123");
			return excelOut;
		}).collect(Collectors.toList());



		Map<String,String> titleMap = new LinkedHashMap<String,String>();
		titleMap.put("name", "姓名");
		titleMap.put("clazz", "组号");
		String sheetName = "信息导出";
		System.out.println("start导出");

		long start = System.currentTimeMillis();
		ExportExcelUtil.excelExport(excelOutList, titleMap, sheetName);
		long end = System.currentTimeMillis();

		System.out.println("end导出");
		System.out.println("耗时："+(end-start)+"ms");



	/*
		//本页面可以取得商品链接和商品名称

		// 取得商品名称规则
		List<String> nameList = new LinkedList<String>();
		nameList.add(".p-name a");
		//取得商品名称
		List<String> nameTxt = JsoupHtmlParser.getNodeContentBySelector(htmlSource, nameList, DataFormatStatus.CleanTxt, true);
		//System.out.println(nameTxt.toString());

		//取得链接名称
		List<String> linkTxt = JsoupHtmlParser.getNodeContentBySelector(htmlSource, nameList, DataFormatStatus.TagAllContent, true);
		*//*System.out.println(linkTxt.toString());*//*
		//正则取出链接
		*//* <atarget="_blank" title="" href="//item.jd.com/10365172840.html"> <em> 【东阿阿胶官方直营】东阿阿胶 OTC阿胶块250g可制作阿胶糕 补血滋阴 润燥止血 </em> <i class="promo-words"></i> </a>*//*



		for (int i = 0; i <linkTxt.size(); i++) {
			Matcher matcherLink = patternLink.matcher(linkTxt.get(i));
			Matcher matcherName = patternName.matcher(linkTxt.get(i));
			Matcher matcherNameId = patternNameId.matcher(linkTxt.get(i));

			if(matcherLink.find() && matcherName.find() && matcherNameId.find()){


				JdGoods jdGoods = new JdGoods();
				jdGoods.setName(matcherName.group(1).trim());



				jdGoods.setWebsite("https:"+matcherLink.group(1));

				//存储到数据库
				ServiceImpl jdGoodsServiceImpl = new ServiceImpl();
				jdGoodsServiceImpl.add("jd_goods", jdGoods);
			}
		}*/
	}






	public static void main(String[] args) {
		SpiderRuleZhongYaoCai360 spiderUtils = new SpiderRuleZhongYaoCai360();
		String srcUrl = "http://www.zhongyaocai360.com/zhongyaodacidian/";
		spiderUtils.runSpider(srcUrl);

	}
}