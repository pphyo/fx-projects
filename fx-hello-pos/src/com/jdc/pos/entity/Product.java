package com.jdc.pos.entity;

import java.time.LocalDateTime;

import com.jdc.pos.util.DateUtil;

public class Product {

	private int id;
	private String name;
	private int price;
	private LocalDateTime creationDate;
	private String description;
	private Category category;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}

	public String getCreationDate() {
		return DateUtil.format(creationDate);
	}

	public String getCategoryName() {
		return category.getName();
	}

	public String toString() {
		return name;
	}

}