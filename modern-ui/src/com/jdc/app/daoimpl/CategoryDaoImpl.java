package com.jdc.app.daoimpl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.jdc.app.dao.CategoryDao;
import com.jdc.app.entity.Category;
import com.jdc.app.util.DatabaseConnection;
import com.jdc.app.util.StringUtil;

public class CategoryDaoImpl implements CategoryDao {
	
	private static final String SELECT = "select * from category where 1 = 1";
	
	@Override
	public void insert(Category c) {
		
		String sql = "insert into category (name, creation_date, creation_time, creator) values (?, ?, ?, ?)";
		
		try(Connection conn = DatabaseConnection.getDbConnection();
				PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			
			stmt.setString(1, c.getName());
			stmt.setDate(2, Date.valueOf(LocalDate.now()));
			stmt.setTime(3, Time.valueOf(LocalTime.now()));
			stmt.setString(4, "Pyae Phyo");
			stmt.executeUpdate();
			
			ResultSet rs = stmt.getGeneratedKeys();
			
			while(rs.next())
				c.setId(rs.getInt(1));
				
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void delete(int id) {
		
		String sql = "delete from category where id = ?";
		
		try(Connection conn = DatabaseConnection.getDbConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			
			stmt.setInt(1, id);
			stmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void upload(File file) throws IOException {
		Files.lines(file.toPath()).map(Category::new).forEach(this::insert);
	}
	
	@Override
	public Category findByName(String name) {
		
		try(Connection conn = DatabaseConnection.getDbConnection();
				PreparedStatement stmt = conn.prepareStatement(StringUtil.isEmpty(name) ? SELECT : SELECT.concat(" and name like ?"))) {
			
			ResultSet rs = stmt.executeQuery();
			while(rs.next())
				return getCategoryObject(rs);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	@Override
	public List<Category> getAll() {
		List<Category> result = new ArrayList<>();
		
		try(Connection conn = DatabaseConnection.getDbConnection();
				PreparedStatement stmt = conn.prepareStatement(SELECT)) {
			
			ResultSet rs = stmt.executeQuery();
			while(rs.next())
				result.add(getCategoryObject(rs));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Category getCategoryObject(ResultSet rs) throws SQLException {
		Category c = new Category();
		c.setId(rs.getInt(1));
		c.setName(rs.getString(2));
		c.setCreationDate(rs.getDate(3).toLocalDate());
		c.setCreationTime(rs.getTime(4).toLocalTime());
		c.setCreator(rs.getString(5));
		return c;
	}
	
}
