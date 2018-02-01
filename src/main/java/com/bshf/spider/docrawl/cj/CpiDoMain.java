package com.bshf.spider.docrawl.cj;

import com.bshf.spider.dorule.cj.CpiSpiderRule;
import com.bshf.util.entity.GoodsPO;
import com.bshf.util.orther.ImportExcelUtil;
import com.bshf.util.orther.SpiderTaskMultThread;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CpiDoMain {
	public static void main(String[] args) throws Exception {
		System.out.println("==========开始================");



		//	1-1 从Excel表中读取目标数据
		File file = new File("D:\\java\\workspace\\learn\\crawler\\record\\cpi目标.xlsx");
		FileInputStream fileInputStream = new FileInputStream(file);

		//	1-2 将Excel表中的记录保存在map对象中
		Map<String, String> excel2Map = new HashMap<String, String>();
		excel2Map.put("目标", "target");
		List<Map<String, Object>> excel2List = ImportExcelUtil.parseExcel(fileInputStream, file.getName(), excel2Map);

		// 1-3 将excel2List中的map封装到对象中
		List<GoodsPO> goodsPOList = new ArrayList<>();




		for (int i = 0; i <excel2List.size(); i++) {
			// 国产药品库(247420)

				String targetNum = excel2List.get(i).get("target").toString();
			String currentPage = "http://www.cpi.gov.cn/admin/tripDataFront/TripSearchAction_findDataListToTrip.action?d=0.781862019862791&id=2013122314504819541&query=&dben=ZYK2009&start="+targetNum+"&pagesize=10&dbname=ZYK2009&idshow=2013122314504819541&sorts=time";
			GoodsPO goodsPO = new GoodsPO();
			goodsPO.setOrderNum(targetNum);
			goodsPO.setWebUrl(currentPage);
			goodsPOList.add(goodsPO);
		}

		System.out.println("==========tomcat let's go ================");
		ExecutorService cachedThreadPool = Executors.newFixedThreadPool(10);

		for ( GoodsPO goodsPO : goodsPOList) {
			CpiSpiderRule spiderRuleCpi = new CpiSpiderRule();
			cachedThreadPool.execute(new SpiderTaskMultThread(goodsPO,spiderRuleCpi));
		}
		cachedThreadPool.shutdown();
	}
}
