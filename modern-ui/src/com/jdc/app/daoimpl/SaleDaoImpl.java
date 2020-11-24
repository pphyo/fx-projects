package com.jdc.app.daoimpl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Time;

import com.jdc.app.dao.SaleDao;
import com.jdc.app.entity.Customer;
import com.jdc.app.entity.Invoice;
import com.jdc.app.entity.SaleDTO;
import com.jdc.app.entity.SaleOrder;
import com.jdc.app.util.DatabaseConnection;
import com.jdc.app.util.Security;

public class SaleDaoImpl implements SaleDao {

	@Override
	public void save(SaleDTO dto) {
		
		String custsql = "insert into customer (name, phone, address) values (?, ?, ?)";
		String invSql = "insert into invoice (inv_date, inv_time, sub_total, tax, discount, total, customer_id, employee_id) values (?, ?, ?, ?, ?, ?, ?, ?)";
		String orderSql = "insert into sale_order (quantity, unit_price, total, invoice_id, product_id, product_category_id) values (?, ?, ?, ?, ?, ?)";
		
		try(Connection conn = DatabaseConnection.getDbConnection();
				PreparedStatement custStmt = conn.prepareStatement(custsql, Statement.RETURN_GENERATED_KEYS);
				PreparedStatement invStmt = conn.prepareStatement(invSql, Statement.RETURN_GENERATED_KEYS);
				PreparedStatement orderStmt = conn.prepareStatement(orderSql)) {
			
			Customer customer = dto.getCustomer();
			
			custStmt.setString(1, customer.getName());
			custStmt.setString(2, customer.getPhone());
			custStmt.setString(3, customer.getAddress());
			custStmt.executeUpdate();
			
			ResultSet rs = custStmt.getGeneratedKeys();
			
			while(rs.next())
				customer.setId(rs.getInt(1));
			
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

}
