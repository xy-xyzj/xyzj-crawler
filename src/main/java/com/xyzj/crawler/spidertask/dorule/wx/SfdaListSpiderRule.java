package com.xyzj.crawler.spidertask.dorule.wx;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.xyzj.crawler.framework.abstracts.SpiderRuleAbstract;
import com.xyzj.crawler.framework.entity.GoodsPO;
import com.xyzj.crawler.utils.parsehtmlstring.RegexUtil;
import org.apache.commons.logging.LogFactory;

import java.util.List;
import java.util.logging.Level;

/**
 * 标准数据库
 * http://app1.sfda.gov.cn
 *
 * */
public class SfdaListSpiderRule extends SpiderRuleAbstract {
	@Override
	public void runSpider(GoodsPO goodsPO)  {
		try {

			LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log",    "org.apache.commons.logging.impl.NoOpLog");

			java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit")
					.setLevel(Level.OFF);

			java.util.logging.Logger.getLogger("org.apache.commons.httpclient")
					.setLevel(Level.OFF);
			// 1-1取得源码
			WebClient wc=new WebClient(BrowserVersion.FIREFOX_45);
			wc.setJavaScriptTimeout(5000);
			wc.getOptions().setUseInsecureSSL(true);//接受任何主机连接 无论是否有有效证书
			wc.getOptions().setJavaScriptEnabled(true);//设置支持javascript脚本
			wc.getOptions().setCssEnabled(false);//禁用css支持
			wc.getOptions().setThrowExceptionOnScriptError(false);//js运行错误时不抛出异常
			wc.getOptions().setTimeout(100000);//设置连接超时时间
			wc.getOptions().setDoNotTrackEnabled(false);

			HtmlPage page=wc.getPage(goodsPO.getWebUrl());

			//获取页面
			String htmlSource=page.asXml();
			System.out.println("htmlSource"+htmlSource);


			String regex = "amp;Id=(.*?)'";
			//截取出id
			List<String> stringList =RegexUtil.getSubUtil(htmlSource, regex);
			//System.out.println("stringList"+stringList.toString());




			//	2-1 提取有效内容
			//  table a



			for (int i=0;i<stringList.size();i++ ) {
				//  3-1 往数据库中存
				goodsPO.setType(Integer.toString(i+1));
				goodsPO.setWebUrl("http://app1.sfda.gov.cn/datasearch/face3/content.jsp?tableId=30&tableName=TABLE30&tableView=%B9%FA%B2%FA%B1%A3%BD%A1%CA%B3%C6%B7&Id="+stringList.get(i));
				SfdaDetailSpiderRule sfdaDetailSpiderRule = new SfdaDetailSpiderRule();
				sfdaDetailSpiderRule.runSpider(goodsPO);
			}
		}catch (Exception e){
			//不处理异常
		}

	}



	public static void main(String[] args) {
		GoodsPO goodsPO = new GoodsPO();
		String srcUrl = "http://app1.sfda.gov.cn/datasearch/face3/search.jsp?tableId=30&State=1&bcId=118103385532690845640177699192&State=1&curstart=1&State=1&tableName=TABLE30&State=1&viewtitleName=COLUMN233&State=1&viewsubTitleName=COLUMN235,COLUMN232&State=1&keyword=%25E5%2585%258D%25E7%2596%25AB%25E5%258A%259B&State=1&tableView=%25E5%259B%25BD%25E4%25BA%25A7%25E4%25BF%259D%25E5%2581%25A5%25E9%25A3%259F%25E5%2593%2581&State=1&cid=0&State=1&ytableId=0&State=1&searchType=search&State=1";
		goodsPO.setWebUrl(srcUrl);
		goodsPO.setOrderNum("1");
		SfdaListSpiderRule spiderUtils = new SfdaListSpiderRule();
		spiderUtils.runSpider(goodsPO);
	}
}

