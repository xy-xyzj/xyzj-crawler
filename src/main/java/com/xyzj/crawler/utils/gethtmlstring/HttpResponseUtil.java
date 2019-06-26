package com.xyzj.crawler.utils.gethtmlstring;


import com.alibaba.fastjson.JSONObject;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.util.CollectionUtils;

/**
 *
 */

@Slf4j
public class HttpResponseUtil {


    //代理
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
                entity = EntityUtils.toString(httpResponse.getEntity(), charset);
            }
            httpResponse.close();
            httpClient.close();
        } catch (Exception e) {
            log.error("Exception:{}", e);
        }

        return entity;
    }


    //取得html
    public static String getHtml(String url, String charset, Map<String, String> headerInfos) {

        //charset重置
        if(StringUtils.isEmpty(charset)) {
            charset = "utf-8";
        }

        String entity = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();

        RequestConfig config =
                RequestConfig.custom()
                        .setConnectTimeout(3000)
                        .setSocketTimeout(3000).
                        build();
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
                entity = EntityUtils.toString(httpResponse.getEntity(), charset);
            }
            httpResponse.close();
            httpClient.close();
        } catch (Exception e) {
            log.error("Exception:{}", e);
        }
        return entity;
    }


    //取得html
    public static String getJson(String url, String charset, Map<String, String> headerInfos,Map<String,String> bodyParams) {

        //charset重置
        if(StringUtils.isEmpty(charset)) {
            charset = "utf-8";
        }
        CloseableHttpClient httpClient = HttpClients.createDefault();

        RequestConfig config = RequestConfig.custom()
                        .setConnectTimeout(3000)
                        .setSocketTimeout(3000)
                        .build();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(config);

        // 遍历map 设置请求头信息
        if (!CollectionUtils.isEmpty(headerInfos)) {
            for (String key : headerInfos.keySet()) {
                httpPost.setHeader(key, headerInfos.get(key));
            }
        }
        //遍历BodyParams
        if (!CollectionUtils.isEmpty(bodyParams)) {
            JSONObject jsonParam = new JSONObject();
            for (String key : bodyParams.keySet()) {
                jsonParam.put(key, bodyParams.get(key));
            }
            //解决中文乱码问题
            StringEntity entity = new StringEntity(jsonParam.toString(),charset);
            entity.setContentEncoding("UTF-8");
            entity.setContentType("application/json");
            httpPost.setEntity(entity);
        }

        String httpResponseString = "";
        try {
            //客户端执行httpPost方法，返回响应
            HttpResponse httpResponse = httpClient.execute(httpPost);
            //得到服务响应状态码
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                httpResponseString = EntityUtils.toString(httpResponse.getEntity(), charset);
            }
            httpClient.close();
        } catch (Exception e) {
            log.error("Exception:{}", e);
        }
        return httpResponseString;
    }

}
