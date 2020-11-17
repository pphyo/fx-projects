package com.jdc.app.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import com.jdc.app.util.Security;

public class Category {
	
	private int id;
	private String name;
	private LocalDate creationDate;
	private LocalTime creationTime;
	private String creator;
	
	public Category() {}
	
	public Category(String line) {
		this.name = line;
		creationDate = LocalDate.now();
		creationTime = LocalTime.now();
		creator = Security.getEmployee().getUsername();
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

	public LocalDate getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDate creationDate) {
		this.creationDate = creationDate;
	}

	public LocalTime getCreationTime() {
		return creationTime;
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
	
}
