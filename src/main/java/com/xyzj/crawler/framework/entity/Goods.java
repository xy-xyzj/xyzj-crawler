package com.xyzj.crawler.framework.entity;

import java.io.Serializable;
import lombok.Data;

/**
 * @author liuyangyang
 * @since 2017-12-05 11:49
 */
@Data
public class Goods implements Serializable {

    /** 主键 id*/
    private Integer id;

    /** 类型 */
    private String type;

    /**名称  详细内容*/
    private String name;

    /**来源网站*/
    private String webUrl;

    /**提供*/
    private String provide;

    /** 排序列*/
    private String orderNum;


}
