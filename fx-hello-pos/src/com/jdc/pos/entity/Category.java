package com.jdc.pos.entity;

public class Category {

	private int id;
	private String name;
	
	public Category() {}
	
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

	public Category(String line) {
		String[] arr = line.split("\t");
		name = arr[0];
	}
	
	@Override
	public String toString() {
		return name;
	}
}
