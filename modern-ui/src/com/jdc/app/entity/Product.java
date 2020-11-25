package com.jdc.app.entity;

import com.jdc.app.dao.CategoryDao;
import com.jdc.app.util.CommonUtil;

public class Product {

	private int id;
	private String name;
	private int price;
	private String description;
	private String creator;
	private boolean stock;
	private int noOfItem;
	private Category category;
	
	public Product() {}
	
	public Product(String line) {
		String[] arr = line.split("\t");
		category = CategoryDao.getInstance().findByName(arr[0]).get(0);
		name = arr[1];
		price = Integer.parseInt(arr[2]);
		stock = arr[3].equals("In stock") ? true : false;
		if(arr.length == 4)
			description = "";
		else
			description = arr[4];
	}

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
	
	public String getCreator() {
		return creator;
	}
	
	public void setCreator(String creator) {
		this.creator = creator;
	}
	
	public boolean isStock() {
		return stock;
	}
	
	public void setStock(boolean stock) {
		this.stock = stock;
	}

	public int getNoOfItem() {
		return noOfItem;
	}
	
	public void setNoOfItem(int noOfItem) {
		this.noOfItem = noOfItem;
	}
	
	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
	
	public String getPriceWithFormat() {
		return CommonUtil.noFormatMMK(price);
	}
	
	public String getStockResult() {
		return stock ? "In stock" : "Out of stock";
	}
	
	public String getCategoryName() {
		return category.getName();
	}

}
