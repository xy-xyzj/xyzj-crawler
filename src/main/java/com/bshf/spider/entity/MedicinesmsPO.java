
package com.bshf.spider.entity;

import java.io.Serializable;

/**
 * 
 *
 * @author 刘洋杨
 * @since 2017-07-24 17:07
 */
@SuppressWarnings("serial")
public class MedicinesmsPO implements Serializable {

	/** 主键 */
	private Integer id;
	/** 内容 */
	private String website;
	/** 内容 */
	private String content;

	/** 主键 */
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	/** 内容 */
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
		ts.append("content:").append(content);

		return ts.append("]").toString();
	}

	/**
	 * @return website
	 */
	public String getWebsite() {
		return website;
	}

	/**
	 * @param website
	 *            要设置的 website
	 */
	public void setWebsite(String website) {
		this.website = website;
	}

}
