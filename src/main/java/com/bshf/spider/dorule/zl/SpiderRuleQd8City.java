package com.bshf.spider.dorule.zl;

import com.bshf.spider.dorule.abs.SpiderRuleAbstract;
import com.bshf.spider.entity.GoodsPO;
import com.bshf.util.RegexUtil;
import com.bshf.util.httpbrowser.MyHttpResponse;
import com.bshf.util.savetomysql.ServiceImpl;

import java.util.List;

/**
 * 快点8城市
 * http://www.qd8.com.cn/index.html
 *
 * */
public class SpiderRuleQd8City extends SpiderRuleAbstract {
	@Override
	public void runSpider(GoodsPO goodsPO)  {
		try {

			String htmlSource = MyHttpResponse.getMyHtml(goodsPO.getWebUrl());

			//	1-2 输出查看效果
			System.out.println("htmlSource=============="+htmlSource);

			//	2-1 提取有效内容
			//href="http://xiamen.qd8.com.cn/"
			String rgex = "<a href=\"(.*?)\"";
			List<String> stringList = RegexUtil.getSubUtil(htmlSource, rgex);
			System.out.println(stringList.toString());
			for (String mystr : stringList) {
				goodsPO.setName(mystr);
				ServiceImpl goodsPOServiceImpl = new ServiceImpl();
				goodsPOServiceImpl.add("goods_qd8_city", goodsPO);

			}
		}catch (Exception e){
			//不处理异常
		}

	}



	public static void main(String[] args) {
		GoodsPO goodsPO = new GoodsPO();
		String srcUrl = "http://www.qd8.com.cn/index.html";
		goodsPO.setWebUrl(srcUrl);
		goodsPO.setOrderNum("1");
		SpiderRuleQd8City spiderUtils = new SpiderRuleQd8City();
		spiderUtils.runSpider(goodsPO);
	}
}

