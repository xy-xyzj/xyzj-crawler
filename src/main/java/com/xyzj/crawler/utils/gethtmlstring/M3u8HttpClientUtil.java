package com.xyzj.crawler.utils.gethtmlstring;


import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import org.springframework.util.CollectionUtils;

/**
 *
 * @author liuyangyang
 * */
public class M3u8HttpClientUtil {


    public static void doGet(String httpUrl, BaseHttpCallBack baseHttpCallBack, Map<String, String> headerInfos) {
        HttpURLConnection connection = null;
        try {
            // 创建远程url连接对象
            URL url = new URL(httpUrl);
            // 通过远程url连接对象打开一个连接，强转成httpURLConnection类
            connection = (HttpURLConnection) url.openConnection();

            // 设置连接主机服务器的超时时间：15000毫秒
            int connectTimeout = 15000;
            // 设置读取远程返回的数据时间：60000毫秒
            int readTimeout = 60000;

            // 设置连接方式：get
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(connectTimeout);
            connection.setReadTimeout(readTimeout);

            // 遍历map 设置请求头信息
            if (!CollectionUtils.isEmpty(headerInfos)) {
                for (String key : headerInfos.keySet()) {
                    connection.setRequestProperty(key, headerInfos.get(key));
                }
            }

            // 发送请求
            connection.connect();

            if (connection.getResponseCode() == 200) {
                baseHttpCallBack.httpCallBack(connection.getResponseCode(), connection.getInputStream());
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            baseHttpCallBack.httpCallBack(-1, null);
        } catch (IOException e) {
            e.printStackTrace();
            baseHttpCallBack.httpCallBack(-2, null);
        } finally {
            // 关闭远程连接
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
