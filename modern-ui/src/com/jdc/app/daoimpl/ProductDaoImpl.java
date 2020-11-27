package com.jdc.app.daoimpl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.jdc.app.dao.ProductDao;
import com.jdc.app.entity.Category;
import com.jdc.app.entity.Product;
import com.jdc.app.util.DatabaseConnection;
import com.jdc.app.util.StringUtil;

public class ProductDaoImpl implements ProductDao {
	
	@Override
	public void save(Product p) {
		
		String insertSql = "insert into product (name, price, description, stock, creator, category_id) values (?, ?, ?, ?, ?, ?)";
		String updateSql = "update product set name = ?, price = ?, description = ?, stock = ?, creator = ?, category_id = ? where id = ?";
		
		boolean isNew = p.getId() == 0;
		
		try(Connection conn = DatabaseConnection.getDbConnection();
				PreparedStatement stmt = conn.prepareStatement(isNew ? insertSql : updateSql)) {
		
			stmt.setString(1, p.getName());
			stmt.setInt(2, p.getPrice());
			stmt.setString(3, p.getDescription());
			stmt.setBoolean(4, p.isStock());
			stmt.setString(5, "Pyae Phyo");
			stmt.setInt(6, p.getCategory().getId());
			if(!isNew)
				stmt.setInt(7, p.getId());
			
			stmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(Product p) {
		
		String sql = "delete from product where id = ?";
		
		try(Connection conn = DatabaseConnection.getDbConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			
			stmt.setInt(1, p.getId());
			stmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Product> find(String category, String product, int price) {
		String sql = "select c.id cat_id, c.name cat_name, c.creator cat_creator, p.id pro_id, p.name pro_name,"
				+ " p.price price, p.description description, p.creator pro_creator, p.stock stock from category c join product p"
				+ " on c.id = p.category_id where 1 = 1";

		List<Product> result = new ArrayList<>();
		
		try(Connection conn = DatabaseConnection.getDbConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			
			ResultSet rs = null;
			
			if(!StringUtil.isEmpty(category)) {
				String catSql = sql.concat(" and c.name like ?");
				
				try(PreparedStatement categoryStmt = conn.prepareStatement(catSql)) {
				
					categoryStmt.setString(1, category.toLowerCase().concat("%"));
					rs = categoryStmt.executeQuery();

					while(rs.next())
						result.add(getProductObject(rs));

					
				}
				
			}

			boolean isNotExistResult = result.size() == 0;
			
			if(isNotExistResult) {
				if(!StringUtil.isEmpty(product)) {
					String productSql = sql.concat(" and p.name like ?");
					
					try(PreparedStatement nameStmt = conn.prepareStatement(productSql)) {

						nameStmt.setString(1, product.toLowerCase().concat("%"));
						rs = nameStmt.executeQuery();
						
						while(rs.next())
							result.add(getProductObject(rs));

					}
				}
			}
						
			if(isNotExistResult) {
				if(price > 0) {
					String priceSql = sql.concat(" and p.price >= ?");
					
					try(PreparedStatement priceStmt = conn.prepareStatement(priceSql)) {
						
						priceStmt.setInt(1, price);
						rs = priceStmt.executeQuery();
						
						while(rs.next())
							result.add(getProductObject(rs));
						
					}
					
				}
			}
			
			isNotExistResult = result.size() == 0;
			
			if(isNotExistResult && StringUtil.isEmpty(category)) {
				rs = stmt.executeQuery();
				
				while(rs.next())
					result.add(getProductObject(rs));
			}
	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<Product> getAll() {
		return find(null, null, 0);
	}
	
	public void upload(File file) throws IOException {
		Files.lines(file.toPath()).skip(1).map(Product::new).forEach(this::save);
	};
	
	private Product getProductObject(ResultSet rs) throws SQLException {
		Product p = new Product();
		Category c = new Category();
		
		c.setId(rs.getInt("cat_id"));
		c.setName(rs.getString("cat_name"));
		c.setCreator(rs.getString("cat_creator"));
		
		p.setCategory(c);
		
		p.setId(rs.getInt("pro_id"));
		p.setName(rs.getString("pro_name"));
		p.setPrice(rs.getInt("price"));
		p.setDescription(rs.getString("description"));
		p.setCreator(rs.getString("pro_creator"));
		p.setStock(rs.getBoolean("stock"));
		
		return p;
	}

}
