package com.xyzj.crawler.utils.parsehtmlstring;

import com.xyzj.crawler.utils.gethtmlstring.BaseHttpCallBack;
import com.xyzj.crawler.utils.gethtmlstring.M3u8HttpClientUtil;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

/**
 *
 * @author liuyangyang
 * */
@Slf4j
public class DownloadTsFile implements BaseHttpCallBack {
    private List<String> tsUrlList;

    private Map<String,String> headerInfos;

    private String fileName;

    private FileOutputStream fileOutputStream=null;

    public DownloadTsFile(List<String> tsUrlList, Map<String,String> headerInfos, String fileName){
        this.tsUrlList=tsUrlList;
        this.headerInfos = headerInfos;
        this.fileName = fileName;
    }


    public void download(){
        log.info("开始生成文件，请等待......");
        if(!StringUtils.isEmpty(tsUrlList)){
            try {
                fileOutputStream = new FileOutputStream(new File(fileName));
            } catch (FileNotFoundException e) {
                log.info("输出流创建异常 e={}",e);
            }
            for (String url:tsUrlList) {
                M3u8HttpClientUtil.doGet(url,this,headerInfos);
            }
            if(fileOutputStream!=null){
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    log.info("输出流关闭异常 e={}",e);
                }
            }
        }
    }

    @Override
    public void httpCallBack(int responseCode, InputStream inputStream) {
        if(responseCode == 200){
            byte[] tempBytes = new byte[100];
            int byteRead = 0;
            try {
                while ((byteRead = inputStream.read(tempBytes)) != -1) {
                    fileOutputStream.write(tempBytes, 0, byteRead);
                }
            } catch (IOException e) {
                log.info("文件合并异常 e={}",e);
            }
        }
    }

}
