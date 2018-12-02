package com.xyzj.crawler.utils.gethtmlstring;

import java.io.InputStream;

/**
 * http回调
 * */
public interface BaseHttpCallBack {

    void httpCallBack(int responseCode, InputStream inputStream);

}
