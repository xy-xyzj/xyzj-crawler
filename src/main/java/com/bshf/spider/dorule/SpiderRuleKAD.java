package com.bshf.spider.dorule;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;

import com.bshf.spider.dorule.abs.SpiderRuleAbstract;
import com.bshf.spider.entity.MedicinekadPO;
import com.bshf.util.DataFormatStatus;
import com.bshf.util.parser.JsoupHtmlParser;
import com.bshf.util.savetomysql.ServiceImpl;

public class SpiderRuleKAD extends SpiderRuleAbstract {
	@Override
	public void runSpider(String srcUrl) {
		HttpClient httpClient = new HttpClient();
		GetMethod getMethod = new GetMethod(srcUrl);
		getMethod.setRequestHeader("User-Agent",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.133 Safari/537.36");
		String htmlSource = null;
		try {
			httpClient.executeMethod(getMethod);
			htmlSource = getMethod.getResponseBodyAsString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 测试页面源代码是否取得
		// System.out.println(htmlSource);
		if (null == htmlSource || htmlSource.contains("Not Found") || htmlSource.contains("主人，找不到您要的网页")
				|| htmlSource.contains("抱歉，康康没有找到")) {
			return;
		}
		// 取得products
		List<String> productsList = new LinkedList<String>();
		productsList.add("#wrap990list1"); // 商品名称
		List<String> productsTxt = JsoupHtmlParser.getNodeContentBySelector(htmlSource, productsList,
				DataFormatStatus.CleanTxt, true);
		System.out.println(productsTxt.toString());

		// 取得instructions
		List<String> instructionsList = new LinkedList<String>();
		instructionsList.add("#wrap990list8");
		List<String> instructionsTxt = JsoupHtmlParser.getNodeContentBySelector(htmlSource, instructionsList,
				DataFormatStatus.CleanTxt, true);
		System.out.println(instructionsTxt.toString());

		MedicinekadPO medicinekadPO = new MedicinekadPO();
		medicinekadPO.setWebsite(srcUrl);
		medicinekadPO.setProducts(productsTxt.toString());
		medicinekadPO.setInstructions(instructionsTxt.toString());
		ServiceImpl medicinepbwServiceImpl = new ServiceImpl();
		medicinepbwServiceImpl.add("medicinekad", medicinekadPO);

	}

	public static void main(String[] args) {
		SpiderRuleKAD spiderUtils = new SpiderRuleKAD();
		String srcUrl = "http://www.360kad.com/product/1377.shtml";
		spiderUtils.runSpider(srcUrl);
	}
}