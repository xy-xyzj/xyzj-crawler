package com.bshf.spider.docrawl.zl;

import com.bshf.spider.dorule.zl.SpiderRuleBaixingZhaoping;
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

public class BaixingZhaopingDoMain {
	//0.1 基础准备
	/** 链接头部*/
	private static final String  TYPE="gongzuo/?page=";

	public static void main(String[] args) throws Exception {

		//	1-1 从Excel表中读取目标城市
		File file = new File("D:\\数据爬取\\百姓网city.xlsx");
		FileInputStream fileInputStream = new FileInputStream(file);

		//	1-2 将Excel表中的记录保存在map对象中
		Map<String, String> excel2Map = new HashMap<String, String>();
		excel2Map.put("网址", "webUrl");
		List<Map<String, Object>> excel2List = ImportExcelUtil.parseExcel(fileInputStream, file.getName(), excel2Map);

		// 1-3 将excel2List中的map封装到对象中
		List<GoodsPO> goodsPOList = new ArrayList<>();
		for (int i = 0; i < excel2List.size(); i++) {
			for(int j=1;j<101;j++) {
				GoodsPO goodsPO = new GoodsPO();
				goodsPO.setWebUrl(excel2List.get(i).get("webUrl").toString()+TYPE+Integer.toString(j));
				goodsPOList.add(goodsPO);
			}
		}
		System.out.println(goodsPOList.size());
		//2-1启动线程进行数据爬取
		ExecutorService cachedThreadPool = Executors.newFixedThreadPool(50);

		for (final GoodsPO goodsPO : goodsPOList) {
			SpiderRuleBaixingZhaoping spiderRuleBaixingZhaoping = new SpiderRuleBaixingZhaoping();
			cachedThreadPool.execute(new SpiderTaskMultThread(goodsPO, spiderRuleBaixingZhaoping));
		}
		cachedThreadPool.shutdown();
	}

}
