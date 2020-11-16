package com.jdc.app.daoimpl;

import java.util.List;

import com.jdc.app.dao.ProductDao;
import com.jdc.app.entity.Category;
import com.jdc.app.entity.Product;

public class ProductDaoImpl implements ProductDao {

	@Override
	public void save(Product p) {
		
	}

	@Override
	public void delete(Product p) {
		
	}

	@Override
	public List<Product> find(Category c, String product, int price) {
		return null;
	}

	@Override
	public List<Product> getAll() {
		return find(null, null, 0);
	}

}
