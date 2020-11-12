package com.jdc.pos.entity;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Product {
	
	private int id;
	private String name;
	private int price;
	private LocalDateTime creationDate;
	private String description;
	private Category category;

	public String getCategoryName() {
		return category.getName();
	}
	
	public String toString() {
		return name;
	}

}