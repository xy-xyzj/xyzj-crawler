package com.bshf.spider.dorule;

import com.bshf.spider.dorule.abs.SpiderRuleAbstract;
import com.bshf.spider.entity.GoodsPO;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * 用药参考网
 * http://drugs.medlive.cn/index.jsp
 * 根据关键字 取得链接 并取得其内容
 * */
public class SpiderCqvipDetail extends SpiderRuleAbstract {
	@Override
	public void runSpider(GoodsPO goodsPO)  {
		try {


			//页面源码
			StringBuffer htmlSourceBuffer = new StringBuffer();
			try {
				//HtmlUnit请求web页面
				WebClient wc = new WebClient();
				//启用JS解释器，默认为true
				wc.getOptions().setJavaScriptEnabled(true);
				//禁用css支持
				wc.getOptions().setCssEnabled(false);
				//js运行错误时，是否抛出异常
				wc.getOptions().setThrowExceptionOnScriptError(false);
				//设置连接超时时间 ，这里是10S。如果为0，则无限期等待
				wc.getOptions().setTimeout(10000);
				HtmlPage page = wc.getPage(goodsPO.getWebUrl());
				//以xml的形式获取响应文本
				htmlSourceBuffer.append(page.asXml());
				wc.close();
			} catch (Exception e) {
				//异常不做处理
			}
			//  第一步 取得源码
			String htmlSource = htmlSourceBuffer.toString();
			System.out.println(htmlSource);




			//	2-1 提取有效内容
			//  .blue-bar .blue-white 取出药品名称
		/*	List<String> targetListPattern = new LinkedList<String>();
			targetListPattern.add(".blue-bar .blue-white");
			List<String> targetList = JsoupHtmlParser.getNodeContentBySelector(htmlSource, targetListPattern, DataFormatStatus.CleanTxt, true);*/
			//  .info-content .info-left 取出药品详情
		/*	List<String> targetDetailListPattern = new LinkedList<String>();
			targetDetailListPattern.add(".info-content .info-left");
			List<String> targetDetailList = JsoupHtmlParser.getNodeContentBySelector(htmlSource, targetDetailListPattern, DataFormatStatus.CleanTxt, true);*/

			//  2-2输出查看
			//	System.out.println(targetList.toString());
			//  System.out.println(targetDetailList.toString().replace("：","：|"));

			//  3-1 往数据库中存
			//goodsPO.setName(targetList.toString());
			//  做字符替换 好在Excel表中分列
			//goodsPO.setProvide(targetDetailList.toString().replace("：", "：|"));
			//goodsPO.setType(ipMessage.getIPAddress()+":"+ipMessage.getIPPort());
			//ServiceImpl goodsPOServiceImpl = new ServiceImpl();
			//goodsPOServiceImpl.add("goods", goodsPO);

		}catch (Exception e){
			//不处理异常
		}

	}



	public static void main(String[] args) {
		GoodsPO goodsPO = new GoodsPO();
		String srcUrl = "http://lib.cqvip.com/zk/search.aspx?E=M%3D%E5%9B%BE%E5%83%8F%E5%A4%84%E7%90%86&M=&P=1&CP=0&CC=0&LC=0&H=&Entry=M&S=1&SJ=0&ZJ=&GC=&Type=&strChkRecord=&ChkRecordCount=0";
		goodsPO.setWebUrl(srcUrl);
		goodsPO.setOrderNum("1");
		SpiderCqvipDetail spiderUtils = new SpiderCqvipDetail();
		spiderUtils.runSpider(goodsPO);
	}
}

