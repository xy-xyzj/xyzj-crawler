package com.bshf.spider.entity;

import java.io.Serializable;

/**
 * 
 *
 * @author 刘洋杨
 * @since 2017-07-24 14:53
 */
@SuppressWarnings("serial")
public class MedicinepbwPO implements Serializable {

	/** 主键 */
	private Integer id;
	/** 药品名称 */
	private String name;
	/** 汉语拼音 */
	private String hypy;
	/** 英文名称 */
	private String englishname;
	/** 来源站点 */
	private String websit;
	/** 内容描述 */
	private String content;

	/** 主键 */
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	/** 药品名称 */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/** 汉语拼音 */
	public String getHypy() {
		return hypy;
	}

	public void setHypy(String hypy) {
		this.hypy = hypy;
	}

	/** 英文名称 */
	public String getEnglishname() {
		return englishname;
	}

	public void setEnglishname(String englishname) {
		this.englishname = englishname;
	}

	/** 来源站点 */
	public String getWebsit() {
		return websit;
	}

	public void setWebsit(String websit) {
		this.websit = websit;
	}

	/** 内容描述 */
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
		ts.append("hypy:").append(hypy).append(", ");
		ts.append("englishname:").append(englishname).append(", ");
		ts.append("websit:").append(websit).append(", ");
		ts.append("content:").append(content);

		return ts.append("]").toString();
	}

}
