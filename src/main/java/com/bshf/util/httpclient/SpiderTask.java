/*
package com.bshf.util.httpclient;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.util.CollectionUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

*/
/**
 * 爬虫工具类
 *
 * @author TongWei.Chen 2017-05-17 10:20:53
 *//*

@org.springframework.stereotype.Component
@Configurable
public class SpiderTask {

    public void run() {
        // 查询所有目录
//        List<CatalogVO> list = catalogService.list();
        List<CatalogVO> list = new ArrayList<>();
        CatalogVO catalogVO = new CatalogVO();
        catalogVO.setId(1);
        catalogVO.setName("小岳岳");
        list.add(catalogVO);
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        //ExecutorService cachedThreadPool = Executors.newFixedThreadPool(list.size());
        for (final CatalogVO catalog : list) {
            cachedThreadPool.execute(new SpiderThread(catalog, pictureService));
        }
        cachedThreadPool.shutdown();
    }
}

class SpiderThread implements Runnable {
    private static final Logger THREAD_LOG = LoggerFactory.getLogger(SpiderThread.class);

    //private CatalogVO catalog;
    //private IPictureService pictureService;

    //public SpiderThread(CatalogVO catalog, IPictureService pictureService) {
    //    this.catalog = catalog;
    //    this.pictureService = pictureService;
    //}

    @Override
    public void run() {
        int pages = 50;
        String image = "";
        String text = "";
        for (int i = pages; i > 0; i--) {
            String url = String.format("http://drugs.medlive.cn/drugref/drug_info_search.do?q=%s", "%E9%98%BF%E8%83%B6%E5%BD%93%E5%BD%92%E8%83%B6%E5%9B%8A");
            String result = HttpClientHelper.get(url);

        }
    }
}*/
