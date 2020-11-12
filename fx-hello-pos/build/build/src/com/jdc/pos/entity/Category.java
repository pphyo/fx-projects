package com.jdc.pos.entity;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Category {

	private int id;
	private String name;
	
	public Category() {}
	
	public Category(String line) {
		String[] arr = line.split("\t");
		name = arr[0];
	}
	
	@Override
	public String toString() {
		return name;
	}
}
