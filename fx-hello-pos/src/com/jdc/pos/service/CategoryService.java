package com.jdc.pos.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.jdc.pos.entity.Category;
import com.jdc.pos.util.ConnectionManager;

public class CategoryService {
	
	private static CategoryService INSTANCE;
	
	private CategoryService() {}
	
	public static CategoryService getInstance() {
		return INSTANCE == null ? INSTANCE = new CategoryService() : INSTANCE;
	}
	
	public void create(Category c) {
		
		String sql = "insert into category (name) values (?)";
		
		try(Connection conn = ConnectionManager.getDbConnection();
				PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			
			stmt.setString(1, c.getName());
			stmt.executeUpdate();
			
			ResultSet rs = stmt.getGeneratedKeys();
			while(rs.next()) {
				c.setId(rs.getInt(1));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void delete(int index) {
		String sql = "delete from category where id = ?";
		
		try(Connection conn = ConnectionManager.getDbConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			
			stmt.setInt(1, index);
			stmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<Category> findByName(String name) {
		boolean isConcat = null != name && !name.isEmpty();

		String sql = "select * from category where 1 = 1";
		List<Category> result = new ArrayList<>();
		StringBuilder sb = new StringBuilder(sql);
		
		
		try(Connection conn = ConnectionManager.getDbConnection();
				PreparedStatement stmt = conn.prepareStatement(isConcat ? sb.toString().concat(" and lower(name) like ?") : sb.toString())) {
			
			if(isConcat)
				stmt.setString(1, "%".concat(name.toLowerCase()).concat("%"));
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				Category c = new Category();
				c.setId(rs.getInt("id"));
				c.setName(rs.getString("name"));
				result.add(c);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Category findByNameEqual(String name) {
		Category c = new Category();
		String sql = "select * from category where name = ?";
		StringBuilder sb = new StringBuilder(sql);
		
		
		try(Connection conn = ConnectionManager.getDbConnection();
				PreparedStatement stmt = conn.prepareStatement(sb.toString())) {
			
			stmt.setString(1, name.toLowerCase());
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				c.setId(rs.getInt("id"));
				c.setName(rs.getString("name"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return c;
	}
	
	public void upload(File file) throws IOException {
		Files.lines(file.toPath()).map(line -> new Category(line)).forEach(this::create);
	}

}