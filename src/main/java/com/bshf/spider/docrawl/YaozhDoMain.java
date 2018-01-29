package com.bshf.spider.docrawl;

import com.bshf.spider.dorule.SpiderRuleYaozh;
import com.bshf.spider.entity.GoodsPO;
import com.bshf.util.ImportExcelUtil;
import com.bshf.util.SpiderTaskMultThread;
import com.bshf.util.UrlUtil;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class YaozhDoMain {
	public static void main  (String[] args) throws Exception {
		String baseUrl = "https://db.yaozh.com/instruct?name=";
		//从Excel表中读取对象
		File file = new File("D:\\yaozh.xlsx");
		FileInputStream fis = new FileInputStream(file);
		Map<String, String> m = new HashMap<String, String>();
		m.put("名称", "name");
		m.put("编号", "orderNum");
		List<Map<String, Object>> ls = ImportExcelUtil.parseExcel(fis, file.getName(), m);
		List<GoodsPO> goodsPOList = new ArrayList<>();
		for (int i = 0; i <ls.size(); i++) {
			GoodsPO  goodsPO = new GoodsPO();
			goodsPO.setOrderNum(ls.get(i).get("orderNum").toString());
			goodsPO.setName(ls.get(i).get("name").toString());
			goodsPO.setWebUrl(baseUrl+ UrlUtil.getURLEncoderString(ls.get(i).get("name").toString()));
			goodsPOList.add(goodsPO);
		}

		ExecutorService cachedThreadPool = Executors.newFixedThreadPool(3);
		for (final GoodsPO goodsPO : goodsPOList) {
			SpiderRuleYaozh spiderRuleYaozh = new SpiderRuleYaozh();
			cachedThreadPool.execute(new SpiderTaskMultThread( goodsPO,spiderRuleYaozh));
		}
		cachedThreadPool.shutdown();
	}

}
