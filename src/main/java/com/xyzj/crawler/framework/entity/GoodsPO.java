package com.xyzj.crawler.framework.entity;

import java.io.Serializable;

/**
 * PO
 *
 * @author liuyangyang@bshf360.com
 * @since 2017-12-05 11:49
 */
public class GoodsPO implements Serializable {

	    /**  */
	    private Integer id;
	    /** 类型 */
	    private String type;
	    /** 名称 */
	    private String name;
	    /** 来源网站 */
	    private String webUrl;
	    /** 提供方 */
	    private String provide;
		/** 排序列 */
	    private String orderNum;

		public String getOrderNum() {
			return orderNum;
		}

		public void setOrderNum(String orderNum) {
			this.orderNum = orderNum;
		}

		/**  */
	    public Integer getId() {
	        return id;
	    }
	    public void setId(Integer id) {
	        this.id = id;
	    }
	    /** 类型 */
	    public String getType() {
	        return type;
	    }
	    public void setType(String type) {
	        this.type = type;
	    }
	    /** 名称 */
	    public String getName() {
	        return name;
	    }
	    public void setName(String name) {
	        this.name = name;
	    }
	    /** 来源网站 */
	    public String getWebUrl() {
	        return webUrl;
	    }
	    public void setWebUrl(String webUrl) {
	        this.webUrl = webUrl;
	    }
	    /** 提供方 */
	    public String getProvide() {
	        return provide;
	    }
	    public void setProvide(String provide) {
	        this.provide = provide;
	    }



}
