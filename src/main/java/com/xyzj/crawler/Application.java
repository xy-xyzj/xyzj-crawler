package com.xyzj.crawler;

import com.xyzj.crawler.spidertask.zlr.docrawler.SsqHandleMain;

/**
 * ==================================================
 * FileName: Application
 *
 * @author: jingyh
 * @create: 2019/6/27
 * @since: 1.0.0
 * @description: 启动类
 * ==================================================
 */
public class Application {

    public static void main(String[] args) {
        // 具体的方法
        try {
            SsqHandleMain.main(args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
