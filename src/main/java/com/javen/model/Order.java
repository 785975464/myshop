package com.javen.model;

import java.sql.Timestamp;

public class Order {

	private Integer id;
	private Integer uid;
	private Product product;	//一对一关系
	private Timestamp datetime;
	private double total;
	private Integer solve;
	private String solveremark;
	private boolean close;
	private String closeremark;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Timestamp getDatetime() {
		return datetime;
	}

	public void setDatetime(Timestamp datetime) {
		this.datetime = datetime;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public Integer getSolve() {
		return solve;
	}

	public void setSolve(Integer solve) {
		this.solve = solve;
	}

	public String getSolveremark() {
		return solveremark;
	}

	public void setSolveremark(String solveremark) {
		this.solveremark = solveremark;
	}

	public boolean isClose() {
		return close;
	}

	public void setClose(boolean close) {
		this.close = close;
	}

	public String getCloseremark() {
		return closeremark;
	}

	public void setCloseremark(String closeremark) {
		this.closeremark = closeremark;
	}

	@Override
	public String toString() {
		return "Order{" +
				"id=" + id +
				", uid=" + uid +
				", product=" + product +
				", datetime=" + datetime +
				", total=" + total +
				", solve=" + solve +
				", solveremark='" + solveremark + '\'' +
				", close=" + close +
				", closeremark='" + closeremark + '\'' +
				'}';
	}
}
