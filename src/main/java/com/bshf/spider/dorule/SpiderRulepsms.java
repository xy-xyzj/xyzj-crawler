package com.bshf.spider.dorule;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

import com.bshf.spider.dorule.abs.SpiderRuleAbstract;
import com.bshf.spider.entity.MedicinesmsPO2;
import com.bshf.util.DataFormatStatus;
import com.bshf.util.parser.JsoupHtmlParser;
import com.bshf.util.savetomysql.ServiceImpl;

public class SpiderRulepsms extends SpiderRuleAbstract {
	@Override
	public void runSpider(String srcUrl) {

		HttpClient httpClient = new HttpClient();
		GetMethod getMethod = new GetMethod(srcUrl);
		PostMethod postMethod = new PostMethod(srcUrl);
		// getMethod.setRequestHeader("cookie", LoginUtil.login(loginUrl));
		postMethod.setRequestHeader("Referer", "https://www.yaozh.com");
		postMethod.setRequestHeader("user-agent",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.133 Safari/537.36");
		String htmlSource = null;
		try {
			httpClient.executeMethod(getMethod);
			htmlSource = getMethod.getResponseBodyAsString();
			// 打印出返回数据，检验一下是否成功
			// System.out.println(htmlSource);
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (null == htmlSource || htmlSource.contains("Not Found") || htmlSource.contains("无法访问此网站")) {
			return;
		}
		// 取得含有的字段名
		List<String> zdmList = new LinkedList<String>();
		zdmList.add(".detail-main .title"); // 选择器选取商品名称
		List<String> zdmTxt = JsoupHtmlParser.getNodeContentBySelector(htmlSource, zdmList, DataFormatStatus.CleanTxt,
				true);

		List<String> zdzList = new LinkedList<String>();
		zdzList.add(".manual");
		List<String> zdzTxt = JsoupHtmlParser.getNodeContentBySelector(htmlSource, zdzList, DataFormatStatus.CleanTxt,
				true);

		MedicinesmsPO2 medicinesmsPO = new MedicinesmsPO2();
		medicinesmsPO.setWebsite(srcUrl);
		medicinesmsPO.setName(zdmTxt.toString());
		medicinesmsPO.setContent(zdzTxt.toString());
		ServiceImpl medicinesmsServiceImpl = new ServiceImpl();
		medicinesmsServiceImpl.add("medicinesms", medicinesmsPO);
	}

	@Override
	public void runSpider(String srcUrl, String loginCookie) {
		try {

			HttpClient httpClient = new HttpClient();
			GetMethod getMethod = new GetMethod(srcUrl);
			PostMethod postMethod = new PostMethod(srcUrl);
			getMethod.setRequestHeader("cookie", loginCookie);
			postMethod.setRequestHeader("Referer", "https://www.yaozh.com");
			postMethod.setRequestHeader("user-agent",
					"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.133 Safari/537.36");
			String htmlSource = null;
			try {
				httpClient.executeMethod(getMethod);
				htmlSource = getMethod.getResponseBodyAsString();
				// 打印出返回数据，检验一下是否成功
				// System.out.println(htmlSource);
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println(htmlSource);
			if (null == htmlSource || htmlSource.contains("Not Found") || htmlSource.contains("无法访问此网站")
					|| htmlSource.contains("") || !(htmlSource.contains("【药品名称】"))) {
				return;
			}
			// 取得含有的字段名
			List<String> zdmList = new LinkedList<String>();
			zdmList.add(".detail-main .title"); // 选择器选取商品名称

			List<String> zdmTxt = JsoupHtmlParser.getNodeContentBySelector(htmlSource, zdmList,
					DataFormatStatus.CleanTxt, true);
			List<String> zdzList = new LinkedList<String>();
			zdzList.add(".manual");
			List<String> zdzTxt = JsoupHtmlParser.getNodeContentBySelector(htmlSource, zdzList,
					DataFormatStatus.CleanTxt, true);
			System.out.println(zdmTxt.toString());
			if (zdmTxt.toString().length() > 0) {
				MedicinesmsPO2 medicinesmsPO = new MedicinesmsPO2();
				medicinesmsPO.setWebsite(srcUrl);
				medicinesmsPO.setName(zdmTxt.toString());
				medicinesmsPO.setContent(zdzTxt.toString());
				ServiceImpl medicinesmsServiceImpl = new ServiceImpl();
				medicinesmsServiceImpl.add("medicinesms", medicinesmsPO);
			} else {
				return;
			}

		} catch (Exception e) {
			throw e;
		}

	}

	public static void main(String[] args) {
		SpiderRulepsms spiderUtils = new SpiderRulepsms();
		String srcUrl = "https://db.yaozh.com/instruct/47057.html";
		spiderUtils.runSpider(srcUrl);

	}

}