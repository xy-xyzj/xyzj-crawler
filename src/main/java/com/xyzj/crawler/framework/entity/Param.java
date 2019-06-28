package com.xyzj.crawler.framework.entity;

import avro.shaded.com.google.common.collect.Maps;
import com.xyzj.crawler.framework.enums.FactionEnum;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import lombok.Data;

/**
 * ==================================================
 * <p>
 * FileName: Param
 *
 * @description:
 * @author: lyy
 * @create: 2019/6/28
 * @since: 1.0.0
 * <p>
 * ==================================================
 */
@Data
public class Param {

    /** 请求地址 */
    private String webUrl;

    /** 网页编码 */
    private String charset = "utf-8";

    /** 请求头信息 */
    private Map<String, String> headerInfos = Maps.newHashMap();

    /** 请求体信息*/
    private  Map<String, String> bodyParams = Maps.newHashMap();

    /** 指定源码获取方法 */
    private FactionEnum factionEnum= FactionEnum.getHtml;

    /** 计数器锁 */
    private CountDownLatch countDownLatch;

    /** 代理ip */
    private String proxyIp;

    /** 代理port */
    private String proxyPort;

    /** 页面加载延迟时间单位 毫秒 */
    private Integer delayTime;

    /** 文件保存路径 */
    private String fileFullName;
}
