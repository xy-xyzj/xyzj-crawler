package com.xyzj.crawler.framework.defaults;

import com.xyzj.crawler.framework.abstracts.SpiderRuleAbstract;
import com.xyzj.crawler.framework.entity.Param;
import com.xyzj.crawler.utils.parsehtmlstring.ParseTsUrls;
import lombok.extern.slf4j.Slf4j;

/**
 * @author lyy
 * @since 2018-11-25 19:40
 */
@Slf4j
public class DefaultM3u8SpiderRule extends SpiderRuleAbstract {
    @Override
    public void runSpider(Param param) {
        //执行解析
        new ParseTsUrls(param.getWebUrl(), param.getHeaderInfos(), param.getFileFullName()).httpRequestForTsUrls();
        log.info("文件生成成功......");
    }
}
