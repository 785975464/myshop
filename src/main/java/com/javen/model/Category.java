package com.javen.model;

/**
 * Category entity. @author MyEclipse Persistence Tools
 */
public class Category {

	// Constructors
	private Integer id;
	private String type;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Category{" +
				"id=" + id +
				", type='" + type + '\'' +
				'}';
	}
}
