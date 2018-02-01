package com.bshf.spider.docrawl.cj;

import com.bshf.spider.dorule.cj.YaozhSpiderRule;
import com.bshf.util.entity.GoodsPO;
import com.bshf.util.orther.ImportExcelUtil;
import com.bshf.util.orther.SpiderTaskMultThread;
import com.bshf.util.orther.UrlUtil;

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
		// 1-1 定义baseUrl
		String baseUrl = "https://db.yaozh.com/instruct?name=";

		// 2-1从Excel表中读取对象
		File file = new File("D:\\java\\workspace\\learn\\crawler\\record\\cj\\进口药品2018年1月30日.xlsx");
		FileInputStream fileInputStream = new FileInputStream(file);
		Map<String, String> map = new HashMap<String, String>();
		map.put("名称", "name");
		map.put("编号", "orderNum");
		List<Map<String, Object>> ls = ImportExcelUtil.parseExcel(fileInputStream, file.getName(), map);
		List<GoodsPO> goodsPOList = new ArrayList<>();
		for (int i = 0; i <ls.size(); i++) {
			GoodsPO  goodsPO = new GoodsPO();
			goodsPO.setOrderNum(ls.get(i).get("orderNum").toString());
			goodsPO.setName(ls.get(i).get("name").toString());
			goodsPO.setWebUrl(baseUrl+ UrlUtil.getURLEncoderString(ls.get(i).get("name").toString()));
			goodsPOList.add(goodsPO);
		}

		ExecutorService cachedThreadPool = Executors.newFixedThreadPool(30);
		for (final GoodsPO goodsPO : goodsPOList) {
			YaozhSpiderRule yaozhSpiderRule = new YaozhSpiderRule();
			cachedThreadPool.execute(new SpiderTaskMultThread( goodsPO, yaozhSpiderRule));
		}
		cachedThreadPool.shutdown();
	}

}
