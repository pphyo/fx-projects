package com.jdc.app.dao;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.jdc.app.daoimpl.ProductDaoImpl;
import com.jdc.app.entity.Product;

public interface ProductDao {
	
	static ProductDao getInstance() {
		return new ProductDaoImpl();
	}
	
	void save(Product p);
	void delete(Product p);
	List<Product> find(String category, String product, int price);
	List<Product> getAll();
	void upload(File file) throws IOException;

}
