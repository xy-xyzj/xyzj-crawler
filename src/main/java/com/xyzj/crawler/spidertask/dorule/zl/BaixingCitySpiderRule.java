package com.xyzj.crawler.spidertask.dorule.zl;

import com.xyzj.crawler.framework.abstracts.SpiderRuleAbstract;
import com.xyzj.crawler.framework.entity.GoodsPO;
import com.xyzj.crawler.utils.parsehtmlstring.RegexUtil;
import com.xyzj.crawler.utils.gethtmlstring.MyHttpResponse;
import com.xyzj.crawler.framework.savetomysql.ServiceImpl;

import java.util.List;

/**
 * 百姓网 城市
 * http://www.baixing.com/?changeLocation=yes&return=
 *
 * */
public class BaixingCitySpiderRule extends SpiderRuleAbstract {
	@Override
	public void runSpider(GoodsPO goodsPO)  {
		try {

			String htmlSource = MyHttpResponse.getHtml(goodsPO.getWebUrl());

			//	1-2 输出查看效果
			System.out.println("htmlSource=============="+htmlSource);

			//	2-1 提取有效内容

			String rgex = "<a href='//(.*?)'>";
			List<String> stringList = RegexUtil.getSubUtil(htmlSource, rgex);
			System.out.println(stringList.toString());
			for (String mystr : stringList) {
				goodsPO.setName(mystr);
				ServiceImpl goodsPOServiceImpl = new ServiceImpl();
				goodsPOServiceImpl.add("goods_bx_city", goodsPO);

			}
		}catch (Exception e){
			//不处理异常
		}

	}



	public static void main(String[] args) {
		GoodsPO goodsPO = new GoodsPO();
		String srcUrl = "http://www.baixing.com/?changeLocation=yes&return=";
		goodsPO.setWebUrl(srcUrl);
		goodsPO.setOrderNum("1");
		BaixingCitySpiderRule spiderUtils = new BaixingCitySpiderRule();
		spiderUtils.runSpider(goodsPO);
	}
}

