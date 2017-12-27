package com.bshf.spider.docrawl;

import com.bshf.spider.dorule.SpiderRuleBDBKYP;
import com.bshf.spider.entity.GoodsPO;
import com.bshf.util.ImportExcelUtil;
import com.bshf.util.SpiderTaskMultThread;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BDBKYPDoMain {
	public static void main  (String[] args) throws Exception {
		//从Excel表中读取对象
		File file = new File("D:\\基础数据-副本.xlsx");
		FileInputStream fis = new FileInputStream(file);
		Map<String, String> m = new HashMap<String, String>();
		m.put("序号","orderNum");
		m.put("类型", "type");
		m.put("名称", "name");
		m.put("网址", "webUrl");
		List<Map<String, Object>> ls = ImportExcelUtil.parseExcel(fis, file.getName(), m);

		List<GoodsPO> goodsPOList = new ArrayList<>();
		for (int i = 0; i <ls.size(); i++) {
			GoodsPO  goodsPO = new GoodsPO();
			goodsPO.setOrderNum(ls.get(i).get("orderNum").toString());
			goodsPO.setType(ls.get(i).get("type").toString());
			goodsPO.setName(ls.get(i).get("name").toString());
			goodsPO.setWebUrl(ls.get(i).get("webUrl").toString());
			goodsPOList.add(goodsPO);
		}



		ExecutorService cachedThreadPool = Executors.newFixedThreadPool(50);

		SpiderRuleBDBKYP spiderRule = new SpiderRuleBDBKYP();


		for ( GoodsPO goodsPO : goodsPOList) {
			System.out.println(goodsPO.toString());
			cachedThreadPool.execute(new SpiderTaskMultThread(goodsPO, spiderRule));
		}
		cachedThreadPool.shutdown();

	}

}
