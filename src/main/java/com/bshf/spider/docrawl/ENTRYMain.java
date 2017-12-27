package com.bshf.spider.docrawl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bshf.spider.dorule.SpiderRuleEntry;
import com.bshf.util.SpiderTaskMultThread;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class ENTRYMain {
	public static  List<String> getUrl(String srcUrlTarget) throws UnsupportedEncodingException {
		HttpClient httpClient = new HttpClient();
		GetMethod getMethod = new GetMethod(srcUrlTarget);
		getMethod.setRequestHeader("User-Agent",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:56.0) Gecko/20100101 Firefox/56.0");
		getMethod.setRequestHeader("Cookie","BAIDUID=E14495B1933DB5F2DF8EFFA9EA615AB9:FG=1; BIDUPSID=E14495B1933DB5F2DF8EFFA9EA615AB9; PSTM=1487556682; __cfduid=d36659848e5b08ff77ce0bc53547211cc1487726775; MCITY=131-131%3A; BDUSS=1VSeHpHT2I5dTRWd1BCdDRURy1WM1lhc0YzaEVsQ0tpS1NsNEJnZ2JObWs4eEJhSVFBQUFBJCQAAAAAAAAAAAEAAABLBCQ3eWVhcsDPw6jKx87SAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAKRm6VmkZulZd; H_PS_PSSID=1468_21089_24879_22160; BDRCVFR[Fc9oatPmwxn]=srT4swvGNE6uzdhUL68mv3; PSINO=1; pgv_pvi=4129233920; pgv_si=s4744712192; Hm_lvt_f4165db5a1ac36eadcfa02a10a6bd243=1507796682,1508464996,1508838850,1509441801; Hm_lpvt_f4165db5a1ac36eadcfa02a10a6bd243=1509441885");
		String htmlSource = null;
		try {
			httpClient.executeMethod(getMethod);
			htmlSource = getMethod.getResponseBodyAsString();
		} catch (IOException e) {
			e.printStackTrace();
		}

		//String htmlSource = HttpUtil.getPageCode(srcUrl, "utf-8");
		System.out.println(htmlSource);
		if (null == htmlSource || htmlSource.contains("Not Found") || htmlSource.contains("无法访问此网站")
				|| htmlSource.contains("抱歉，您所访问的页面不存在...") || htmlSource.contains("药品不存在！")) {
			return null;
		}

		List<String> resutList = new ArrayList<>();
		JSONObject jsonObject = JSON.parseObject(htmlSource);
		JSONObject pageObj = jsonObject.getJSONObject("data").getJSONObject("pages");
		JSONArray pageRange = jsonObject.getJSONObject("data").getJSONArray("range");
		int start = Integer.valueOf(pageRange.get(0).toString());
		int end = Integer.valueOf(pageRange.get(1).toString());
		for (int i = start; i < end + 1; i++) {
			JSONArray jsonArray = pageObj.getJSONArray("" + i);
			for (Object o : jsonArray) {

				JSONObject obj = (JSONObject) o;
				String urlBefore = obj.get("url").toString();
				String decode = URLDecoder.decode(urlBefore, "utf-8");

				resutList.add("https://baike.baidu.com"+decode);
			}
		}

		return resutList;
	}
	public static void main(String[] args) throws UnsupportedEncodingException {
		// 存放目标连接
		List<String> srcUrls = new ArrayList<String>();

		//构造srcUrlTarget
		for (int i =1; i <3170; i=i+5) {
			String srcUrlTarget = "https://gsp0.baidu.com/9rM4dzva2gU2pMbgoY3K/api/usercenter/getlemmaspublic?tk=8ca883d9ea1a59ccb8c4c89897f0edcf&type=passed&uid=1408192600&filter=all&from="+i+"&count=5&size=5&_=1508895202745";
			srcUrls.addAll(getUrl(srcUrlTarget));
		}


		// 实例化爬取单条数据的工具类
		SpiderRuleEntry spiderRule = new SpiderRuleEntry();

		//多线程执行
		ExecutorService cachedThreadPool = java.util.concurrent.Executors.newFixedThreadPool(100);
		for (final String srcUrl : srcUrls) {
			cachedThreadPool.execute(new SpiderTaskMultThread(srcUrl, spiderRule));
		}
		cachedThreadPool.shutdown();

	}



}
