package com.jdc.app.daoimpl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import com.jdc.app.dao.SaleDao;
import com.jdc.app.entity.Customer;
import com.jdc.app.entity.Invoice;
import com.jdc.app.entity.SaleDTO;
import com.jdc.app.entity.SaleOrder;
import com.jdc.app.util.DatabaseConnection;
import com.jdc.app.util.Security;
import com.jdc.app.util.StringUtil;

public class SaleDaoImpl implements SaleDao {

	private final String custSql = "insert into customer (name, phone, address) values (?, ?, ?)";
	
	@Override
	public void saveSale(SaleDTO dto) {
		
		String invSql = "insert into invoice (inv_date, inv_time, sub_total, tax, discount, total, customer_id, employee_id) values (?, ?, ?, ?, ?, ?, ?, ?)";
		String orderSql = "insert into sale_order (quantity, unit_price, total, invoice_id, product_id, product_category_id) values (?, ?, ?, ?, ?, ?)";
		
		try(Connection conn = DatabaseConnection.getDbConnection();
				PreparedStatement custStmt = conn.prepareStatement(custSql, Statement.RETURN_GENERATED_KEYS);
				PreparedStatement invStmt = conn.prepareStatement(invSql, Statement.RETURN_GENERATED_KEYS);
				PreparedStatement orderStmt = conn.prepareStatement(orderSql)) {
			
			Customer customer = null;
			ResultSet rs = null;
			
			Customer dtoCustomer = dto.getInvoice().getCustomer();
			Customer existingCustomer = findOneCustomer(dtoCustomer.getName());
			
			if(null != existingCustomer && dtoCustomer.equals(existingCustomer)) {
				customer = existingCustomer;
			} else {
			
				custStmt.setString(1, dtoCustomer.getName());
				custStmt.setString(2, dtoCustomer.getPhone());
				custStmt.setString(3, dtoCustomer.getAddress());
				custStmt.executeUpdate();
				
				rs = custStmt.getGeneratedKeys();
				
				while(rs.next())
					dtoCustomer.setId(rs.getInt(1));
				
				customer = dtoCustomer;
			}
			
			Invoice invoice = dto.getInvoice();
			
			invStmt.setDate(1, Date.valueOf(invoice.getInvoiceDate()));
			invStmt.setTime(2, Time.valueOf(invoice.getInvoiceTime()));
			invStmt.setInt(3, invoice.getSubTotal());
			invStmt.setInt(4, invoice.getTax());
			invStmt.setInt(5, invoice.getDiscount());
			invStmt.setInt(6, invoice.getTotal());
			invStmt.setInt(7, customer.getId());
			invStmt.setInt(8, Security.getEmployee().getId());
			invStmt.executeUpdate();
			
			rs = invStmt.getGeneratedKeys();
			
			while(rs.next()) {
				int id = rs.getInt(1);
				
				for(SaleOrder so : dto.getOrderList()) {
					orderStmt.setInt(1, so.getQuantity());
					orderStmt.setInt(2, so.getUnitPrice());
					orderStmt.setInt(3, so.getTotal());
					orderStmt.setInt(4, id);
					orderStmt.setInt(5, so.getProduct().getId());
					orderStmt.setInt(6, so.getProduct().getCategory().getId());
					
					orderStmt.addBatch();
				}
				orderStmt.executeBatch();
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void saveCustomer(Customer c) {
		
		String updateSql = "update customer set name = ?, phone = ?, address = ? where id = ?";
		
		boolean isNew = c.getId() == 0;
		
		try(Connection conn = DatabaseConnection.getDbConnection();
				PreparedStatement stmt = conn.prepareStatement(isNew ? custSql : updateSql)) {
			
			stmt.setString(1, c.getName());
			stmt.setString(2, c.getPhone());
			stmt.setString(3, c.getAddress());
			if(!isNew)
				stmt.setInt(4, c.getId());
			stmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void deleteCustomer(Customer c) {
		String sql = "delete from customer where id = ?";
		
		try(Connection conn = DatabaseConnection.getDbConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			
			stmt.setInt(1, c.getId());
			stmt.executeUpdate();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public List<Customer> findCustomer(String name, String phone, int totalAmount) {
		String sql = "select c.id, c.name, c.phone, c.address, count(i.customer_id) no_of_invoices,"
				+ " sum(i.total) total_amount from customer c left join invoice i on c.id = i.customer_id"
				+ " where 1 = 1";
		
		List<Customer> result = new ArrayList<>();
		
		try(Connection conn = DatabaseConnection.getDbConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			
			ResultSet rs = null;
			
			if(!StringUtil.isEmpty(name)) {
				String nameSql = sql.concat(" and lower(c.name) like ? group by c.id");
				
				try(PreparedStatement nameStmt = conn.prepareStatement(nameSql)) {
					
					nameStmt.setString(1, name.toLowerCase().concat("%"));
					rs = nameStmt.executeQuery();
					
					while(rs.next())
						result.add(getCustomerObject(rs));
					
				}
			}
			
			boolean isNotExistResult = result.size() == 0;
			
			if(isNotExistResult) {
				
				if(!StringUtil.isEmpty(phone)) {
					String phoneSql = sql.concat(" and c.phone like ? group by c.id");
					
					try(PreparedStatement phoneStmt = conn.prepareStatement(phoneSql)) {
						
						phoneStmt.setString(1, phone.concat("%"));
						rs = phoneStmt.executeQuery();
						
						while(rs.next())
							result.add(getCustomerObject(rs));
						
					}
				}
				
			}
			
			if(isNotExistResult) {
				
				if(totalAmount > 0) {
					String totSql = sql.concat(" group by c.id having total_amount >= ?");
					
					try(PreparedStatement totStmt = conn.prepareStatement(totSql)) {
						
						totStmt.setInt(1, totalAmount);
						rs = totStmt.executeQuery();
						
						while(rs.next())
							result.add(getCustomerObject(rs));
						
					}
				}
				
			}
			
			isNotExistResult = result.size() == 0;
			
			if(isNotExistResult && StringUtil.isEmpty(name)) {
				String noResultSql = sql.concat(" group by c.id");
				
				try(PreparedStatement noResultStmt = conn.prepareStatement(noResultSql)) {
					rs = noResultStmt.executeQuery();
					
					while(rs.next())
						result.add(getCustomerObject(rs));
				}
				
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public List<Customer> findCustomer(String name) {
		return findCustomer(name, null, 0);
	}

	@Override
	public Customer findOneCustomer(String name) {
		return findCustomer(name).get(0);
	}

	private Customer getCustomerObject(ResultSet rs) throws SQLException {
		Customer c = new Customer();
		c.setId(rs.getInt("id"));
		c.setName(rs.getString("name"));
		c.setPhone(rs.getString("phone"));
		c.setAddress(rs.getString("address"));
		c.setNoOfInvoices(rs.getInt("no_of_invoices"));
		c.setTotalAmount(rs.getInt("total_amount"));
		return c;
	}

}
