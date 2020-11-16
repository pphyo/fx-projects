package com.jdc.app.daoimpl;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.jdc.app.dao.CategoryDao;
import com.jdc.app.entity.Category;
import com.jdc.app.util.DatabaseConnection;

public class CategoryDaoImpl implements CategoryDao {
	
	@Override
	public void insert(Category c) {
		
		String sql = "insert into category (name) values (?)";
		
		try(Connection conn = DatabaseConnection.getDbConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			
			stmt.setString(1, c.getName());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void upload(File file) {
		
	}
	
	@Override
	public Category findByName(String name) {
		return null;
	}
	
	@Override
	public List<Category> findAll() {
		return null;
	}
	
	public Category getCategoryObject(ResultSet rs) throws SQLException {
		return null;
	}
	
}
