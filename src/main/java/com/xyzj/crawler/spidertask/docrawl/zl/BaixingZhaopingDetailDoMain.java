package com.xyzj.crawler.spidertask.docrawl.zl;

import com.xyzj.crawler.spidertask.dorule.zl.BaixingZhaopingDetailSpiderRule;
import com.xyzj.crawler.framework.entity.GoodsPO;
import com.xyzj.crawler.utils.importfrom.ImportExcelUtil;
import com.xyzj.crawler.framework.threads.SpiderTaskMultThread;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BaixingZhaopingDetailDoMain {

	public static void main(String[] args) throws Exception {


		//	1-1 从Excel表中读取目标城市
		File file = new File("D:\\数据爬取\\goods_bx_zhaoping60-90.xlsx");
		FileInputStream fileInputStream = new FileInputStream(file);

		//	1-2 将Excel表中的记录保存在map对象中
		Map<String, String> excel2Map = new HashMap<String, String>();
		excel2Map.put("name", "webUrl");
		List<Map<String, Object>> excel2List = ImportExcelUtil.parseExcel(fileInputStream, file.getName(), excel2Map);

		// 1-3 将excel2List中的map封装到对象中
		List<GoodsPO> goodsPOList = new ArrayList<>();
		for (int i = 0; i < excel2List.size(); i++) {
			GoodsPO goodsPO = new GoodsPO();
			goodsPO.setWebUrl(excel2List.get(i).get("webUrl").toString());
			goodsPOList.add(goodsPO);
		}
		System.out.println(goodsPOList.size());
		//2-1启动线程进行数据爬取
		ExecutorService cachedThreadPool = Executors.newFixedThreadPool(5);

		for (final GoodsPO goodsPO : goodsPOList) {
			BaixingZhaopingDetailSpiderRule baixingZhaopingDetailSpiderRule = new BaixingZhaopingDetailSpiderRule();
			cachedThreadPool.execute(new SpiderTaskMultThread(goodsPO, baixingZhaopingDetailSpiderRule));
		}
		cachedThreadPool.shutdown();
	}

}
