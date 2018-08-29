package com.xyzj.crawler.spidertask.dorule.cj;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xyzj.crawler.framework.abstracts.SpiderRuleAbstract;
import com.xyzj.crawler.framework.entity.GoodsPO;
import com.xyzj.crawler.utils.gethtmlstring.HttpClientHelper;
import com.xyzj.crawler.framework.savetomysql.ServiceImpl;

/**
 * 中药cpi
 * http://www.cpi.gov
 * 根据关键字 取得链接 并取得其内容
 * */
public class CpiSpiderRule extends SpiderRuleAbstract {




	@Override
	public void runSpider(GoodsPO goodsPO)  {
		try {
			String htmlSource = HttpClientHelper.post(goodsPO.getWebUrl(),null);
			//	1-2 输出查看效果
			System.out.println("htmlSource=============="+htmlSource);
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
		String srcUrl = "https://coding.imooc.com/lesson/223.html#mid=14557";
		goodsPO.setWebUrl(srcUrl);
		goodsPO.setOrderNum("1");
		CpiSpiderRule spiderUtils = new CpiSpiderRule();
		spiderUtils.runSpider(goodsPO);
	}
}

