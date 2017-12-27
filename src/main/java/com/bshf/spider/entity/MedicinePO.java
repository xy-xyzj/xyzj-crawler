package com.bshf.spider.entity;

import java.io.Serializable;

@SuppressWarnings("serial")
public class MedicinePO implements Serializable {

	/** id主键自增 */
	private Integer id;
	/** 商品名称 */
	private String name;
	/** 通用名 */
	private String commname;
	/** 英文名称 */
	private String englishname;
	/** 性状 */
	private String character;
	/** 药品功能 */
	private String function;
	/** 药品试用症状 */
	private String disease;
	/** 药品用法用量 */
	private String useage;
	/** 药性分析 */
	private String analyze;
	/** 不良反应 */
	private String wrong;
	/** 禁忌 */
	private String taboo;
	/** 注意事项 */
	private String notice;
	/** 药品相互作用 */
	private String together;
	/** 药品存储方式 */
	private String storagestyle;
	/** 药品生产企业 */
	private String company;
	/** 参考资料 */
	private String reference;
	/** 药品信息来源网站 */
	private String website;
	/** 成分 */
	private String element;
	/** 毒理研究 */
	private String poisonstudy;
	/** 批准文号 */
	private String approvalnumber;
	/** 药物分类 */
	private String type;
	/** 规格 */
	private String standerd;
	/** 孕妇 */
	private String gravida;
	/** 儿童 */
	private String children;
	/** 老年人 */
	private String oldman;
	/** 哺乳分级 */
	private String lactationlevel;

	
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

	/** 通用名 */
	public String getCommname() {
		return commname;
	}

	public void setCommname(String commname) {
		this.commname = commname;
	}

	/** 英文名称 */
	public String getEnglishname() {
		return englishname;
	}

	public void setEnglishname(String englishname) {
		this.englishname = englishname;
	}

	/** 性状 */
	public String getCharacter() {
		return character;
	}

	public void setCharacter(String character) {
		this.character = character;
	}

	/** 药品功能 */
	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
	}

	/** 药品试用症状 */
	public String getDisease() {
		return disease;
	}

	public void setDisease(String disease) {
		this.disease = disease;
	}

	/** 药品用法用量 */
	public String getUseage() {
		return useage;
	}

	public void setUseage(String useage) {
		this.useage = useage;
	}

	/** 药性分析 */
	public String getAnalyze() {
		return analyze;
	}

	public void setAnalyze(String analyze) {
		this.analyze = analyze;
	}

	/** 不良反应 */
	public String getWrong() {
		return wrong;
	}

	public void setWrong(String wrong) {
		this.wrong = wrong;
	}

	/** 禁忌 */
	public String getTaboo() {
		return taboo;
	}

	public void setTaboo(String taboo) {
		this.taboo = taboo;
	}

	/** 注意事项 */
	public String getNotice() {
		return notice;
	}

	public void setNotice(String notice) {
		this.notice = notice;
	}

	/** 药品相互作用 */
	public String getTogether() {
		return together;
	}

	public void setTogether(String together) {
		this.together = together;
	}

	/** 药品存储方式 */
	public String getStoragestyle() {
		return storagestyle;
	}

	public void setStoragestyle(String storagestyle) {
		this.storagestyle = storagestyle;
	}

	/** 药品生产企业 */
	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	/** 参考资料 */
	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	/** 药品信息来源网站 */
	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	/** 成分 */
	public String getElement() {
		return element;
	}

	public void setElement(String element) {
		this.element = element;
	}

	/** 毒理研究 */
	public String getPoisonstudy() {
		return poisonstudy;
	}

	public void setPoisonstudy(String poisonstudy) {
		this.poisonstudy = poisonstudy;
	}

	/** 批准文号 */
	public String getApprovalnumber() {
		return approvalnumber;
	}

	public void setApprovalnumber(String approvalnumber) {
		this.approvalnumber = approvalnumber;
	}

	/** 药物分类 */
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	/** 规格 */
	public String getStanderd() {
		return standerd;
	}

	public void setStanderd(String standerd) {
		this.standerd = standerd;
	}

	/** 孕妇 */
	public String getGravida() {
		return gravida;
	}

	public void setGravida(String gravida) {
		this.gravida = gravida;
	}

	/** 儿童 */
	public String getChildren() {
		return children;
	}

	public void setChildren(String children) {
		this.children = children;
	}

	/** 老年人 */
	public String getOldman() {
		return oldman;
	}

	public void setOldman(String oldman) {
		this.oldman = oldman;
	}

	/** 哺乳分级 */
	public String getLactationlevel() {
		return lactationlevel;
	}

	public void setLactationlevel(String lactationlevel) {
		this.lactationlevel = lactationlevel;
	}

	@Override
	public String toString() {
		StringBuffer ts = new StringBuffer(this.getClass().getSimpleName()).append("[");

		ts.append("id:").append(id).append(", ");
		ts.append("name:").append(name).append(", ");
		ts.append("commname:").append(commname).append(", ");
		ts.append("englishname:").append(englishname).append(", ");
		ts.append("character:").append(character).append(", ");
		ts.append("function:").append(function).append(", ");
		ts.append("disease:").append(disease).append(", ");
		ts.append("useage:").append(useage).append(", ");
		ts.append("analyze:").append(analyze).append(", ");
		ts.append("wrong:").append(wrong).append(", ");
		ts.append("taboo:").append(taboo).append(", ");
		ts.append("notice:").append(notice).append(", ");
		ts.append("together:").append(together).append(", ");
		ts.append("storagestyle:").append(storagestyle).append(", ");
		ts.append("company:").append(company).append(", ");
		ts.append("reference:").append(reference).append(", ");
		ts.append("website:").append(website).append(", ");
		ts.append("element:").append(element).append(", ");
		ts.append("poisonstudy:").append(poisonstudy).append(", ");
		ts.append("approvalnumber:").append(approvalnumber).append(", ");
		ts.append("type:").append(type).append(", ");
		ts.append("standerd:").append(standerd).append(", ");
		ts.append("gravida:").append(gravida).append(", ");
		ts.append("children:").append(children).append(", ");
		ts.append("oldman:").append(oldman).append(", ");
		ts.append("lactationlevel:").append(lactationlevel);

		return ts.append("]").toString();
	}

}
