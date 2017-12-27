
package com.bshf.spider.entity;
import java.io.Serializable;
import java.math.BigDecimal;

@SuppressWarnings("serial")
public class JdGoods implements Serializable {


    /** 主键 自增 */
    private Integer id;
    /** 商品名称 */
    private String name;
    /** 来源网站 */
    private String website;
    /** 价格 */
    private BigDecimal price;

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

    /** 价格 */
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
