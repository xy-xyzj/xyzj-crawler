package com.bshf.spider.entity;

import java.io.Serializable;

/**
 * 
 *
 * @author 刘洋杨
 * @since 2017-07-28 10:51
 */
@SuppressWarnings("serial")
public class MedicinekadPO implements Serializable {

	/** 主键 自增 */
	private Integer id;
	/** 来源站点 */
	private String website;
	/** 商品介绍 */
	private String products;
	/** 说明书 */
	private String instructions;

	/** 主键 自增 */
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	/** 来源站点 */
	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	/** 商品介绍 */
	public String getProducts() {
		return products;
	}

	public void setProducts(String products) {
		this.products = products;
	}

	/** 说明书 */
	public String getInstructions() {
		return instructions;
	}

	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}

	@Override
	public String toString() {
		StringBuffer ts = new StringBuffer(this.getClass().getSimpleName()).append("[");

		ts.append("id:").append(id).append(", ");
		ts.append("website:").append(website).append(", ");
		ts.append("products:").append(products).append(", ");
		ts.append("instructions:").append(instructions);

		return ts.append("]").toString();
	}

}
