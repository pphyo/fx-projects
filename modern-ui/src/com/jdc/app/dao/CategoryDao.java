package com.jdc.app.dao;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.jdc.app.daoimpl.CategoryDaoImpl;
import com.jdc.app.entity.Category;

public interface CategoryDao {
	
	static CategoryDao getInstance() {
		return new CategoryDaoImpl();
	}
	
	void insert(Category c);
	void delete(int id);
	void upload(File file) throws IOException;
	Category findByName(String name);
	List<Category> findAll();
}
