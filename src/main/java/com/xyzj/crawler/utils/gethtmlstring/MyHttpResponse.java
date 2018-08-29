package com.xyzj.crawler.utils.gethtmlstring;


import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.util.CollectionUtils;

/**
 * Created by paranoid on 17-4-10.
 * 进行代理访问
 * <p>
 * setConnectTimeout：设置连接超时时间，单位毫秒.
 * setConnectionRequestTimeout：设置从connect Manager获取Connection 超时时间，单位毫秒.
 * 这个属性是新加的属性，因为目前版本是可以共享连接池的.
 * setSocketTimeout：请求获取数据的超时时间，单位毫秒.如果访问一个接口，多少时间内无法返回数据，
 * 就直接放弃此次调用。
 */

@Slf4j
public class MyHttpResponse {

    public static String getHtmlWithProxyIp(String url, String ip, String port, String charset, Map<String, String> headerInfos) {

        if (StringUtils.isEmpty(charset)) {
            charset = "utf-8";
        }

        String entity = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();

        //设置代理访问和超时处理
        log.info("此时线程: " + Thread.currentThread().getName() + " 爬取所使用的代理为: "
                + ip + ":" + port);

        HttpHost proxy = new HttpHost(ip, Integer.parseInt(port));
        RequestConfig config = RequestConfig.custom().setProxy(proxy).setConnectTimeout(3000).
                setSocketTimeout(3000).build();
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(config);
        // 遍历map 设置请求头信息
        if (!CollectionUtils.isEmpty(headerInfos)) {
            for (String key : headerInfos.keySet()) {
                httpGet.setHeader(key, headerInfos.get(key));
            }
        }

        try {
            //客户端执行httpGet方法，返回响应
            CloseableHttpResponse httpResponse = httpClient.execute(httpGet);

            //得到服务响应状态码
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                entity = EntityUtils.toString(httpResponse.getEntity(), "utf-8");
            }
            httpResponse.close();
            httpClient.close();
        } catch (Exception e) {
            log.error("Exception:{}", e);
        }

        return entity;
    }

    public static String getHtml(String url, String charset, Map<String, String> headerInfos) {

        if (StringUtils.isEmpty(charset)) {
            charset = "utf-8";
        }

        String entity = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();

        RequestConfig config = RequestConfig.custom().setConnectTimeout(3000).
                setSocketTimeout(3000).build();
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(config);

        // 遍历map 设置请求头信息
        if (!CollectionUtils.isEmpty(headerInfos)) {
            for (String key : headerInfos.keySet()) {
                httpGet.setHeader(key, headerInfos.get(key));
            }
        }

        try {
            //客户端执行httpGet方法，返回响应
            CloseableHttpResponse httpResponse = httpClient.execute(httpGet);

            //得到服务响应状态码
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                entity = EntityUtils.toString(httpResponse.getEntity(), "utf-8");
            }
            httpResponse.close();
            httpClient.close();
        } catch (Exception e) {
            log.error("Exception:{}", e);
        }

        return entity;
    }
}
