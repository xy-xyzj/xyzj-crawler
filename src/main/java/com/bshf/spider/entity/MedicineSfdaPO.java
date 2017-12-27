package com.bshf.spider.entity;

import java.io.Serializable;

/**
 * PO
 *
 * @author 刘洋杨
 * @since 2017-08-16 11:37
 */
@SuppressWarnings("serial")
public class MedicineSfdaPO implements Serializable {

	/** 主键自增 */
	private Integer id;
	/** 来源网站 */
	private String website;
	/** 网页内容 */
	private String content;

	/** 主键自增 */
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	/** 来源网站 */
	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	/** 网页内容 */
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
		ts.append("website:").append(website).append(", ");
		ts.append("content:").append(content);

		return ts.append("]").toString();
	}

}
