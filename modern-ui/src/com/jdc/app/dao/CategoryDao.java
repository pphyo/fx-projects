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
	void update(String name, int id);
	void delete(int id);
	void upload(File file) throws IOException;
	List<Category> findByName(String name);
	List<Category> getAll();
}
