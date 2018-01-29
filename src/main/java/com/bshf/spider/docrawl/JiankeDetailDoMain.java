package com.bshf.spider.docrawl;

import com.bshf.spider.dorule.SpiderRuleJiankeDetail;
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

public class JiankeDetailDoMain {

	public static void main(String[] args) throws Exception {


		//	1-1 从Excel表中读取目标数据
		File file = new File("D:\\java\\workspace\\learn\\crawler\\record\\goods_jianke2.xlsx");
		FileInputStream fileInputStream = new FileInputStream(file);

		//	1-2 将Excel表中的记录保存在map对象中
		Map<String, String> excel2Map = new HashMap<String, String>();
		excel2Map.put("orderNum", "orderNum");
		excel2Map.put("name", "name");
		excel2Map.put("webUrl", "webUrl");
		List<Map<String, Object>> excel2List = ImportExcelUtil.parseExcel(fileInputStream, file.getName(), excel2Map);

		// 1-3 将excel2List中的map封装到对象中
		List<GoodsPO> goodsPOList = new ArrayList<>();
		for (int i = 0; i < excel2List.size(); i++) {
			GoodsPO goodsPO = new GoodsPO();
			goodsPO.setOrderNum(excel2List.get(i).get("orderNum").toString());
			goodsPO.setName(excel2List.get(i).get("name").toString());
			goodsPO.setWebUrl(excel2List.get(i).get("webUrl").toString());
			goodsPOList.add(goodsPO);
		}

		//2-1启动线程进行数据爬取
		ExecutorService cachedThreadPool = Executors.newFixedThreadPool(10);

		for (final GoodsPO goodsPO : goodsPOList) {
			SpiderRuleJiankeDetail spiderRuleJiankeDetail = new SpiderRuleJiankeDetail();
			cachedThreadPool.execute(new SpiderTaskMultThread(goodsPO, spiderRuleJiankeDetail));
		}
		cachedThreadPool.shutdown();


	}

}
