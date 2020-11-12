package com.jdc.pos.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.jdc.pos.entity.Category;
import com.jdc.pos.entity.Product;
import com.jdc.pos.util.ConnectionManager;
import com.jdc.pos.util.StringUtil;

public class ProductService {

	private static ProductService INSTANCE;
	
	private ProductService() {}
	
	public static ProductService getInstance() {
		return INSTANCE == null ? INSTANCE = new ProductService() : INSTANCE;
	}
	
	public void save(Product p) {
		if(p.getId() == 0) {
			create(p);
		} else {
			update(p);
		}
	}
	
	public void create(Product p) {
		String sql = "insert into product (name, price, creation_date, description, category_id) values (?, ?, ?, ?, ?)";
		
		try(Connection conn = ConnectionManager.getDbConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			
			stmt.setString(1, p.getName());
			stmt.setInt(2, p.getPrice());
			stmt.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
			stmt.setString(4, p.getDescription());
			stmt.setInt(5, p.getCategory().getId());
			stmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void update(Product p) {
		String sql = "update product set name = ?, price = ?, creation_date = ?, description = ?, category_id = ? where id = ?";
		
		try(Connection conn = ConnectionManager.getDbConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			
			stmt.setString(1, p.getName());
			stmt.setInt(2, p.getPrice());
			stmt.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
			stmt.setString(4, p.getDescription());
			stmt.setInt(5, p.getCategory().getId());
			stmt.setInt(6, p.getId());
			stmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void delete(int id) {
		String sql = "delete from product where id = ?";
		
		try(Connection conn = ConnectionManager.getDbConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			
			stmt.setInt(1, id);
			stmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<Product> find(Category c, String name, String price) {
		String sql = "select c.name cname, p.id pid, p.name pname, p.price pprice, p.creation_date pcreation_date, p.description pdescription, p.category_id cid"
				+ " from product p join category c on p.category_id = c.id where 1 = 1";
		
		List<Product> result = new ArrayList<>();
		List<Object> params = new LinkedList<>();
		StringBuilder sb = new StringBuilder(sql);
		
		if(null != c) {
			sb.append(" and p.category_id = ?");
			params.add(c.getId());
		}
		
		if(StringUtil.check(name)) {
			sb.append(" and lower(p.name) like ?");
			params.add("%".concat(name.toLowerCase()).concat("%"));
		}
		
		if(StringUtil.check(price)) {
			sb.append(" and p.price >= ?");
			params.add(Integer.parseInt(price));
		}
		
		try(Connection conn = ConnectionManager.getDbConnection();
				PreparedStatement stmt = conn.prepareStatement(sb.toString())) {
			
			for(int i = 0; i < params.size(); i++) {
				stmt.setObject(i + 1, params.get(i));
			}
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				result.add(getObject(rs));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	private Product getObject(ResultSet rs) throws SQLException {
		Product p = new Product();
		p.setId(rs.getInt("pid"));
		p.setName(rs.getString("pname"));
		p.setPrice(rs.getInt("pprice"));
		p.setCreationDate(rs.getTimestamp("pcreation_date").toLocalDateTime());
		p.setDescription(rs.getString("pdescription"));
		Category c = new Category();
		c.setId(rs.getInt("cid"));
		c.setName(rs.getString("cname"));
		p.setCategory(c);
		return p;
	}

	public void upload(File file) throws IOException {
		Files.lines(file.toPath()).skip(1).map(line -> {
			
			String[] arr = line.split("\t");
			
			Category category = CategoryService.getInstance().findByNameEqual(arr[0]);
			
			Product p = new Product();
			
			p.setCategory(category);
			
			p.setName(arr[1]);
			p.setPrice(Integer.parseInt(arr[2]));
			
			if(arr.length == 3)
				p.setDescription("");
			else
				p.setDescription(arr[3]);
			
			return p;
		}).forEach(this::save);
	}
}
