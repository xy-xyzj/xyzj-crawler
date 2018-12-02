package com.xyzj.crawler.utils.parsehtmlstring;

import com.xyzj.crawler.utils.gethtmlstring.BaseHttpCallBack;
import com.xyzj.crawler.utils.gethtmlstring.M3u8HttpClientUtil;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;

/**
 * 解析ts路径
 * @author liuyangyang
 * */
@Slf4j
public class ParseTsUrls implements BaseHttpCallBack {

    private String httpUrl;

    private String fileName;

    private Map<String,String> headerInfos;

    private List<String> tsUrlList = new ArrayList<String>();

    public ParseTsUrls(String httpUrl, Map<String,String> headerInfos,String fileName){
        this.httpUrl = httpUrl;
        this.headerInfos = headerInfos;
        this.fileName = fileName;
    }

    public void httpRequestForTsUrls(){
        log.info("正在发送请求:httpUrl={}",httpUrl);
        M3u8HttpClientUtil.doGet(httpUrl,this,headerInfos);
        if (CollectionUtils.isNotEmpty(tsUrlList)) {
            new DownloadTsFile(tsUrlList, headerInfos, fileName).download();
        } else {
            log.info("没有拿到ts路径,请检查...");
        }
    }

    @Override
    public void httpCallBack(int responseCode,InputStream inputStream) {
        log.info("开始解析TS路径.....");
        if(responseCode == 200){
            try {
                // 封装输入流is，并指定字符集
                BufferedReader br  = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                String lineStr = null;
                while ((lineStr = br.readLine()) != null) {
                    if (lineStr.contains("http") && lineStr.contains(".ts")) {
                        tsUrlList.add(lineStr);
                    }
                }
            } catch (IOException e) {
                log.error("解析ts出错了 e={}",e);
            }

            log.info("解析TS路径完成.....");
        }
    }


}
