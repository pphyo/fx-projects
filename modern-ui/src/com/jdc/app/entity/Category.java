package com.jdc.app.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import com.jdc.app.util.CommonUtil;

public class Category implements Comparable<Category> {
	
	private int id;
	private String name;
	private LocalDate creationDate;
	private LocalTime creationTime;
	private String creator;
	
	public Category() {}
	
	public Category(String line) {
		this.name = line;
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

	public String getCreationDate() {
		return CommonUtil.format(creationDate);
	}

	public void setCreationDate(LocalDate creationDate) {
		this.creationDate = creationDate;
	}

	public String getCreationTime() {
		return CommonUtil.format(creationTime);
	}

	public void setCreationTime(LocalTime creationTime) {
		this.creationTime = creationTime;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	@Override
	public int compareTo(Category o) {
		return this.name.compareTo(o.getName());
	}
	
}
