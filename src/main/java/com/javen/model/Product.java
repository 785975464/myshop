package com.javen.model;

import java.sql.Timestamp;

/**
 * Product entity. @author MyEclipse Persistence Tools
 */
public class Product {

	// Constructors
    private Integer id;
    private String name;
    private Double price;
    private Integer number;
//    private String pic;
    private String remark;
    private String xremark;
//    private Timestamp date;
//    private Integer cid;
    private Category category;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

//    public String getPic() {
//        return pic;
//    }
//
//    public void setPic(String pic) {
//        this.pic = pic;
//    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getXremark() {
        return xremark;
    }

    public void setXremark(String xremark) {
        this.xremark = xremark;
    }

//    public Timestamp getDate() {
//        return date;
//    }
//
//    public void setDate(Timestamp date) {
//        this.date = date;
//    }

//    public Integer getCid() {
//        return cid;
//    }
//
//    public void setCid(Integer cid) {
//        this.cid = cid;
//    }


    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", number='" + number + '\'' +
//                ", pic='" + pic + '\'' +
                ", remark='" + remark + '\'' +
                ", xremark='" + xremark + '\'' +
//                ", date=" + date +
//                ", cid=" + cid +
                ", category=" +category +
                '}';
    }
}
