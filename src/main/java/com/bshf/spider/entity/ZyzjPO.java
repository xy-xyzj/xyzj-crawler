package com.bshf.spider.entity;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ZyzjPO implements Serializable {
	private Integer id;
	private String tymc;
	private String hypy;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTymc() {
		return tymc;
	}
	public void setTymc(String tymc) {
		this.tymc = tymc;
	}
	public String getHypy() {
		return hypy;
	}
	public void setHypy(String hypy) {
		this.hypy = hypy;
	}
	@Override
	public String toString() {
		return "ZyzjPO [id=" + id + ", tymc=" + tymc + ", hypy=" + hypy + "]";
	}
}
