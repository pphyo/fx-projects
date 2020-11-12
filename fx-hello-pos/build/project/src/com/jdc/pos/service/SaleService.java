package com.jdc.pos.service;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.jdc.pos.entity.Category;
import com.jdc.pos.entity.Employee;
import com.jdc.pos.entity.Invoice;
import com.jdc.pos.entity.Product;
import com.jdc.pos.entity.SaleDTO;
import com.jdc.pos.entity.SaleOrder;
import com.jdc.pos.util.ConnectionManager;
import com.jdc.pos.util.Security;

public class SaleService {
	
	private static SaleService INSTANCE;
	
	private SaleService() {}

	public static SaleService getInstance() {
		return INSTANCE == null ? INSTANCE = new SaleService() : INSTANCE;
	}
	
	public void save(SaleDTO dto) {
		
		String invSql = "insert into invoice (inv_date, inv_time, tax, sub_total, total, employee_id) values (?, ?, ?, ?, ?, ?)";
		String soSql = "insert into sale_order (quantity, unit_price, total, product_id, invoice_id) values (?, ?, ?, ?, ?)";
		
		try(Connection conn = ConnectionManager.getDbConnection();
				PreparedStatement invInsert = conn.prepareStatement(invSql, Statement.RETURN_GENERATED_KEYS);
				PreparedStatement soInsert = conn.prepareStatement(soSql)) {
		
			Invoice invoice = dto.getInvoice();
			invInsert.setDate(1, Date.valueOf(invoice.getInvoiceDate()));
			invInsert.setTime(2, Time.valueOf(invoice.getInvoiceTime()));
			invInsert.setInt(3, invoice.getTax());
			invInsert.setInt(4, invoice.getSubTotal());
			invInsert.setInt(5, invoice.getTotal());
			invInsert.setInt(6, Security.getMember().getId());
			
			invInsert.executeUpdate();
			
			ResultSet rs = invInsert.getGeneratedKeys();
			while(rs.next()) {
				int invId = rs.getInt(1);
				
				for(SaleOrder so : dto.getOrders()) {
					
					soInsert.setInt(1, so.getQuantity());
					soInsert.setInt(2, so.getUnitPrice());
					soInsert.setInt(3, so.getTotal());
					soInsert.setInt(4, so.getProduct().getId());
					soInsert.setInt(5, invId);
					
					soInsert.addBatch();
				}
				soInsert.executeBatch();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<SaleOrder> findSaleOrder(LocalDate from, LocalDate to, int price) {
		String sql = "select so.id so_id, so.quantity so_quantity, so.unit_price so_unit_price,"
				+ " so.total so_total, inv.inv_date inv_date, inv.inv_time inv_time, p.name product_name, emp.username emp_name"
				+ " from sale_order so join invoice inv on so.invoice_id = inv.id join product p on so.product_id = p.id"
				+ " join employee emp on inv.employee_id = emp.id";
		
		List<SaleOrder> result = new ArrayList<>();
		List<Object> params = new LinkedList<>();
		StringBuilder sb = new StringBuilder(sql);
		
		if(from.isBefore(to)) {
			sb.append(" and inv.inv_date >= ?");
			params.add(Date.valueOf(from));
		}
		
		if(to.isAfter(from) && to.isBefore(LocalDate.now())) {
			sb.append(" and inv.inv_date <= ?");
			params.add(Date.valueOf(to));
		}
		
		if(price >= 0) {
			sb.append(" and so.unit_price >= ?");
			params.add(price);
		}
		
		try(Connection conn = ConnectionManager.getDbConnection();
				PreparedStatement stmt = conn.prepareStatement(sb.toString())) {
			
			for(int i = 0; i < params.size(); i++)
				stmt.setObject(i + 1, params.get(i));
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				result.add(getObject(rs));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public SaleOrder getObject(ResultSet rs) throws SQLException {
		SaleOrder so = new SaleOrder();
		
		so.setId(rs.getInt("so_id"));
		so.setQuantity(rs.getInt("so_quantity"));
		so.setUnitPrice(rs.getInt("so_unit_price"));
		so.setTotal(rs.getInt("so_total"));
		
		Invoice inv =  new Invoice();
		inv.setInvoiceDate(rs.getDate("inv_date").toLocalDate());
		inv.setInvoiceTime(rs.getTime("inv_time").toLocalTime());
		
		Employee emp = new Employee();
		emp.setUserName(rs.getString("emp_name"));
		
		Product p = new Product();
		p.setName(rs.getString("product_name"));

		inv.setMember(emp);
		so.setProduct(p);
		so.setInvoice(inv);

		return so;
	}
	
	public Map<String, Map<String, Integer>> find(LocalDate from, LocalDate to) {
		Map<String, Map<String, Integer>> result = new LinkedHashMap<>();
		
		for(Category c : CategoryService.getInstance().findByName(null)) {
			result.put(c.getName(), findData(c, from, to));
		}
		
		return result;
	}
	
	private Map<String, Integer> findData(Category c, LocalDate from, LocalDate to) {
		
		String sql = "select sum(so.total) from sale_order so join invoice inv"
				+ " on so.invoice_id = inv.id join product p on so.product_id = p.id join category c"
				+ " on c.id = p.category_id where c.id = ? and date(inv.inv_date)"
				+ " between ? and ? group by c.id";
		
		Map<String, Integer> map = new LinkedHashMap<>();
		DateTimeFormatter df = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		
		try(Connection conn = ConnectionManager.getDbConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			
			while(from.compareTo(to) <= 0) {
				stmt.setInt(1, c.getId());
				stmt.setDate(2, Date.valueOf(from));
				stmt.setDate(3, Date.valueOf(to.plusDays(1)));
				
				ResultSet rs = stmt.executeQuery();
				int value = 0;
				
				while(rs.next()) {
					Long count = rs.getLong(1);
					value = count.intValue();
				}
				
				map.put(from.format(df), value);
				from = from.plusDays(1);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return map;
	}
	
}
