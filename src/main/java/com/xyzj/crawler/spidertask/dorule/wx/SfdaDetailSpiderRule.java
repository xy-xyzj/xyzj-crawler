package com.xyzj.crawler.spidertask.dorule.wx;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.xyzj.crawler.framework.abstracts.SpiderRuleAbstract;
import com.xyzj.crawler.framework.entity.GoodsPO;
import com.xyzj.crawler.framework.savetomysql.ServiceImpl;
import org.apache.commons.logging.LogFactory;
import org.slf4j.LoggerFactory;

import java.util.logging.Level;

/**
 * 标准数据库
 * http://app1.sfda.gov.cn
 *
 * */
public class SfdaDetailSpiderRule extends SpiderRuleAbstract {
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

			String htmlSource=page.asText();
			htmlSource = htmlSource.replaceAll("(\r\n|\r|\n|\n\r)+", "|").replaceAll("(\t)+", "~");

			//  3-1 往数据库中存
			goodsPO.setName(htmlSource);
			ServiceImpl goodsPOServiceImpl = new ServiceImpl();
			goodsPOServiceImpl.add("goods_sfda", goodsPO);

		}catch (Exception e){
			//不处理异常
			LoggerFactory.getLogger(SfdaDetailSpiderRule.class).error("出错了");
		}

	}



	public static void main(String[] args) {
		GoodsPO goodsPO = new GoodsPO();
		String srcUrl = "http://app1.sfda.gov.cn/datasearch/face3/content.jsp?tableId=30&tableName=TABLE30&tableView=%B9%FA%B2%FA%B1%A3%BD%A1%CA%B3%C6%B7&Id=13313";
		goodsPO.setWebUrl(srcUrl);
		goodsPO.setOrderNum("1");
		SfdaDetailSpiderRule spiderUtils = new SfdaDetailSpiderRule();
		spiderUtils.runSpider(goodsPO);
	}
}

