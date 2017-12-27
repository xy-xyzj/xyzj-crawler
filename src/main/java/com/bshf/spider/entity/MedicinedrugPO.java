package com.bshf.spider.entity;

import java.io.Serializable;

/**
 * 
 *
 * @author 刘洋杨
 * @since 2017-07-25 19:49
 */
@SuppressWarnings("serial")
public class MedicinedrugPO implements Serializable {

	/** 主键 自增 */
	private Integer id;
	/** 商品名称 */
	private String name;
	/** 来源网站 */
	private String website;
	/** 描述 */
	private String content;

	/** 主键 自增 */
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	/** 商品名称 */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/** 来源网站 */
	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	/** 描述 */
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		StringBuffer ts = new StringBuffer(this.getClass().getSimpleName()).append("[");

		ts.append("id:").append(id).append(", ");
		ts.append("name:").append(name).append(", ");
		ts.append("website:").append(website).append(", ");
		ts.append("content:").append(content);

		return ts.append("]").toString();
	}

}
