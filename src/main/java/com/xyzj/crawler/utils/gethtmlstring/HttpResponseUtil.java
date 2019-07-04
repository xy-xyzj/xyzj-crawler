package com.xyzj.crawler.utils.gethtmlstring;


import com.alibaba.fastjson.JSONObject;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.ProxyConfig;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.xyzj.crawler.framework.entity.Goods;
import com.xyzj.crawler.framework.entity.Param;
import com.xyzj.crawler.framework.enums.FactionEnum;
import com.xyzj.crawler.utils.proxyip.IPModel.IPMessage;
import com.xyzj.crawler.utils.proxyip.config.RedisUtil;
import com.xyzj.crawler.utils.savetomysql.SaveToMysql;
import lombok.extern.slf4j.Slf4j;
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

    public static String getHtmlSource(Param param) {
        //获取html源文件
        param.getHeaderInfos().put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36");
        String htmlSource = "";
        FactionEnum factionEnum = param.getFactionEnum();
        switch (factionEnum) {
            case getHtml:
                log.info("走 getHtml");
                htmlSource = HttpResponseUtil.getHtml(param);
                break;
            case getHtmlWithJavaScript:
                log.info("走 getHtmlWithJavaScript");
                htmlSource = HttpResponseUtil.getHtmlWithJavaScript(param);
                break;
            case getJson:
                log.info("走 getJson");
                htmlSource = HttpResponseUtil.getJson(param);
                break;
        }
        if (org.springframework.util.StringUtils.isEmpty(htmlSource) || htmlSource.contains("Not Found") || htmlSource.contains("无法访问此网站") || htmlSource.contains("你所访问的页面就如那些遇害的同道") || htmlSource.contains("药品不存在！")) {
            log.info("本次爬取目标失败 webUrl={}", param.getWebUrl());
            //没拿到数据 存入ungoods表
            Goods unableGoods = new Goods();
            unableGoods.setWebUrl(param.getWebUrl());
            SaveToMysql saveToMysql = new SaveToMysql();
            saveToMysql.saveToMasql("ungoods", unableGoods);
            return null;
        }
        log.info("本次爬取目标 webUrl={}", param.getWebUrl());
        log.info(htmlSource);
        return htmlSource;
    }

    /**
     * ========================================
     *
     * @description: 取得网页html信息
     * @author: lyy
     * @param:
     * @return:
     * @exception:
     * @create: 2019/6/28 11:54
     * <p>
     * ========================================
     */
    public static String getHtml(Param param) {
        String entity = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //设置代理
        RequestConfig config = null;
        if (param.getIsProxy()) {
            IPMessage ipMessage = RedisUtil.getOneIp();
            config = RequestConfig.custom().setConnectTimeout(3000).setSocketTimeout(3000).setProxy(new HttpHost(ipMessage.getIp(), Integer.parseInt(ipMessage.getPort()))).build();
        } else {
            config = RequestConfig.custom().setConnectTimeout(3000).setSocketTimeout(3000).build();
        }
        HttpGet httpGet = new HttpGet(param.getWebUrl());
        httpGet.setConfig(config);
        // 遍历map 设置请求头信息
        if (!CollectionUtils.isEmpty(param.getHeaderInfos())) {
            for (String key : param.getHeaderInfos().keySet()) {
                httpGet.setHeader(key, param.getHeaderInfos().get(key));
            }
        }
        try {
            //客户端执行httpGet方法，返回响应
            CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
            //得到服务响应状态码
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                entity = EntityUtils.toString(httpResponse.getEntity(), param.getCharset());
            }
            httpResponse.close();
            httpClient.close();
        } catch (Exception e) {
            log.error("getHtml exception:{}", e);
        }
        return entity;
    }

    /**
     * ========================================
     *
     * @description: 取得执行javascript后的页面信息
     * @author: lyy
     * @param:
     * @return:
     * @exception:
     * @create: 2019/6/28 11:45
     * <p>
     * ========================================
     */
    public static String getHtmlWithJavaScript(Param param) {
        try {
            //HtmlUnit请求web页面
            WebClient wc = new WebClient();
            //启用JS解释器，默认为true
            wc.getOptions().setJavaScriptEnabled(true);
            //js运行错误时，是否抛出异常
            wc.getOptions().setThrowExceptionOnScriptError(false);
            //禁用css支持
            wc.getOptions().setActiveXNative(false);
            wc.getOptions().setCssEnabled(false);
            //设置支持AJAX
            wc.setAjaxController(new NicelyResynchronizingAjaxController());
            if (param.getIsProxy()) {
                IPMessage ipMessage = RedisUtil.getOneIp();
                wc.getOptions().setProxyConfig(new ProxyConfig(ipMessage.getIp(), Integer.parseInt(ipMessage.getPort())));
            }
            if (param.getDelayTime() != null) {
                Thread.sleep(param.getDelayTime());
            }
            HtmlPage page = wc.getPage(param.getWebUrl());
            //以xml的形式获取响应文本
            return page.asXml();
        } catch (Exception e) {
            //异常
            log.info("没有抓到数据......");
        }
        return null;
    }

    /**
     * ========================================
     *
     * @description: 取得json数据
     * @author: lyy
     * @param:
     * @return:
     * @exception:
     * @create: 2019/6/28 11:46
     * <p>
     * ========================================
     */
    public static String getJson(Param param) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //设置代理
        RequestConfig config = null;
        if (param.getIsProxy()) {
            IPMessage ipMessage = RedisUtil.getOneIp();
            config = RequestConfig.custom().setConnectTimeout(3000).setSocketTimeout(3000).setProxy(new HttpHost(ipMessage.getIp(), Integer.parseInt(ipMessage.getPort()))).build();
        } else {
            config = RequestConfig.custom().setConnectTimeout(3000).setSocketTimeout(3000).build();
        }
        HttpPost httpPost = new HttpPost(param.getWebUrl());
        httpPost.setConfig(config);
        // 遍历map 设置请求头信息
        if (!CollectionUtils.isEmpty(param.getHeaderInfos())) {
            for (String key : param.getHeaderInfos().keySet()) {
                httpPost.setHeader(key, param.getHeaderInfos().get(key));
            }
        }
        //遍历BodyParams
        if (!CollectionUtils.isEmpty(param.getBodyParams())) {
            JSONObject jsonParam = new JSONObject();
            for (String key : param.getBodyParams().keySet()) {
                jsonParam.put(key, param.getBodyParams().get(key));
            }
            //解决中文乱码问题
            StringEntity entity = new StringEntity(jsonParam.toString(), param.getCharset());
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
                httpResponseString = EntityUtils.toString(httpResponse.getEntity(), param.getCharset());
            }
            httpClient.close();
        } catch (Exception e) {
            log.error("Exception:{}", e);
        }
        return httpResponseString;
    }

}
