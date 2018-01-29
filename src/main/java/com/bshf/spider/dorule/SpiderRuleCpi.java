package com.bshf.spider.dorule;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bshf.spider.dorule.abs.SpiderRuleAbstract;
import com.bshf.spider.entity.GoodsPO;
import com.bshf.util.HttpClientHelper;
import com.bshf.util.savetomysql.ServiceImpl;

/**
 * 用药参考网
 * http://drugs.medlive.cn/index.jsp
 * 根据关键字 取得链接 并取得其内容
 * */
public class SpiderRuleCpi extends SpiderRuleAbstract {




	@Override
	public void runSpider(GoodsPO goodsPO)  {
		try {
			String htmlSource = HttpClientHelper.post(goodsPO.getWebUrl(),null);
			//	1-2 输出查看效果
			//System.out.println("htmlSource=============="+htmlSource);
			JSONObject jsonObject = JSON.parseObject(htmlSource);
			JSONArray datas = jsonObject.getJSONArray("datas");
			for (int i = 0; i < datas.size(); i++) {
				//System.out.println(datas.get(i).toString().replace("[\"","").replace("\"]",""));
				goodsPO.setName(datas.get(i).toString().replace("[\"","").replace("\"]",""));
				ServiceImpl goodsPOServiceImpl = new ServiceImpl();
				goodsPOServiceImpl.add("goods_cpi", goodsPO);
			}
		}catch (Exception e){
			//不处理异常
		}

	}

	public static void main(String[] args) {
		GoodsPO goodsPO = new GoodsPO();
		String srcUrl = "http://www.cpi.gov.cn/admin/tripDataFront/TripSearchAction_findDataListToTrip.action?d=0.781862019862791&id=2013122314504819541&query=&dben=ZYK2009&start=1&pagesize=10&dbname=ZYK2009&idshow=2013122314504819541&sorts=time";
		goodsPO.setWebUrl(srcUrl);
		goodsPO.setOrderNum("1");
		SpiderRuleCpi spiderUtils = new SpiderRuleCpi();
		spiderUtils.runSpider(goodsPO);
	}
}

