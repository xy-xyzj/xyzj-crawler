package com.xyzj.crawler.utils.proxyip.IPModel;

import java.io.Serializable;
import lombok.Data;


@Data
public class IPMessage implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ip地址
     */
    private String ip;

    /**
     * 端口号
     */
    private String port;


    /**
     * 类型
     */
    private String type;

    /**
     * 延迟
     */
    private String speed;


}
