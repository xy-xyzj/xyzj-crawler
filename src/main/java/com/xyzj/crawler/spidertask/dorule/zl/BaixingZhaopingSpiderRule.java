package com.xyzj.crawler.spidertask.dorule.zl;

import com.xyzj.crawler.framework.abstracts.SpiderRuleAbstract;
import com.xyzj.crawler.framework.entity.GoodsPO;
import com.xyzj.crawler.utils.proxyip.IPModel.IPMessage;
import com.xyzj.crawler.utils.parsehtmlstring.RegexUtil;
import com.xyzj.crawler.utils.proxyip.database.MyRedis;
import com.xyzj.crawler.utils.gethtmlstring.MyHttpResponse;

import java.util.List;

/**
 * 用药参考网
 * http://drugs.medlive.cn/index.jsp
 * 根据关键字 取得链接 并取得其内容
 * */
public class BaixingZhaopingSpiderRule extends SpiderRuleAbstract {
	@Override
	public void runSpider(GoodsPO goodsPO)  {
		try {

			// 设置代理IP
			MyRedis redis = new MyRedis();
			//从redis数据库中随机拿出一个IP
			IPMessage ipMessage = redis.getIPByList();
			redis.close();
			String htmlSource = MyHttpResponse.getHtml(goodsPO.getWebUrl(), ipMessage.getIPAddress(), ipMessage.getIPPort());


			//	1-2 输出查看效果
			//System.out.println("htmlSource=============="+htmlSource);

			//	2-1 提取有效内容

			String rgex = "<div class='preview-hover'><a href='(.*?)' target='_blank' class='ad-title'>";
			List<String> stringList = RegexUtil.getSubUtil(htmlSource, rgex);
			//System.out.println(stringList.toString());
			for (String mystr : stringList) {
				goodsPO.setName(mystr);
				goodsPO.setType(ipMessage.getIPAddress()+":"+ipMessage.getIPPort());

				GoodsPO savePO = new GoodsPO();
				savePO.setWebUrl(mystr);
				savePO.setType(ipMessage.getIPAddress());
				savePO.setName(ipMessage.getIPPort());
				BaixingZhaopingDetailSpiderRule baixingZhaopingDetailSpiderRule = new BaixingZhaopingDetailSpiderRule();
				baixingZhaopingDetailSpiderRule.runSpider(savePO);


			}
		}catch (Exception e){
			//不处理异常
		}

	}



	public static void main(String[] args) {
		GoodsPO goodsPO = new GoodsPO();
		String srcUrl = "http://wuhan.baixing.com/gongzuo/?page=100";
		goodsPO.setWebUrl(srcUrl);
		goodsPO.setOrderNum("1");
		BaixingZhaopingSpiderRule spiderUtils = new BaixingZhaopingSpiderRule();
		spiderUtils.runSpider(goodsPO);
	}
}

