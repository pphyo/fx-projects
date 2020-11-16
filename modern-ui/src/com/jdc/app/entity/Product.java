package com.jdc.app.entity;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Product {
	
	private int id;
	private String name;
	private int price;
	private String description;
	private Category category;
	
}
