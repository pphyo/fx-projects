package com.jdc.pos.service;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.jdc.pos.entity.Employee;
import com.jdc.pos.entity.Role;
import com.jdc.pos.util.ConnectionManager;
import com.jdc.pos.util.DateUtil;
import com.jdc.pos.util.StringUtil;

public class EmployeeService {

	private static EmployeeService INSTANCE;
	
	private EmployeeService() {}
	
	public static EmployeeService getInstance() {
		return INSTANCE == null ? INSTANCE = new EmployeeService() : INSTANCE;
	}
	
	public void create(Employee emp) {
		String insert = "insert into employee (username, email, password, role, salary, dob, phone_no, address, paid) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		String update = "update employee set username = ?, email = ?, password = ?, role = ?, salary = ?, dob = ?, phone_no = ?, address = ?, paid = ? where id = ?";
		
		boolean isNew = emp.getId() == 0;
		
		try(Connection conn = ConnectionManager.getDbConnection();
				PreparedStatement stmt = conn.prepareStatement(isNew ? insert : update, Statement.RETURN_GENERATED_KEYS)) {
			
			stmt.setString(1, emp.getUserName());
			stmt.setString(2, emp.getEmail());
			stmt.setString(3, emp.getPassword());
			stmt.setString(4, emp.getRole().toString());
			stmt.setInt(5, emp.getSalary());
			stmt.setDate(6, Date.valueOf(DateUtil.format(emp.getDob())));
			stmt.setString(7, emp.getPhoneNo());
			stmt.setString(8, emp.getAddress());
			stmt.setBoolean(9, emp.isPaid());
			
			if(!isNew)
				stmt.setInt(10, emp.getId());
			
			stmt.executeUpdate();
			
			if(isNew) {
				ResultSet rs = stmt.getGeneratedKeys();
				while(rs.next())
					emp.setId(rs.getInt(1));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void delete(int id) {
		String sql = "delete from employee where id = ?";
		try(Connection conn = ConnectionManager.getDbConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			
			stmt.setInt(1, id);
			stmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Employee getOne(int id) {
		String sql = "select * from employee where id = ?";
		
		try(Connection conn = ConnectionManager.getDbConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			while(rs.next())
				return getObject(rs);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Employee getOne(String userName, String password) {
		String sql = "select * from employee where 1 = 1";
		StringBuilder sb = new StringBuilder(sql);
		
		boolean isConcat = StringUtil.check(userName) && StringUtil.check(password);
		
		String runSql = isConcat ? sb.toString().concat(" and username like ? and password like ?") : sb.toString();
		
		try(Connection conn = ConnectionManager.getDbConnection();
				PreparedStatement stmt = conn.prepareStatement(runSql)) {
			
			stmt.setString(1, userName);
			stmt.setString(2, password);
			ResultSet rs = stmt.executeQuery();
			while(rs.next())
				return getObject(rs);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Employee> find(String userName, Role role, String email, String address) {
		
		String sql = "select * from employee where 1 = 1";
		
		List<Employee> result = new ArrayList<>();
		List<Object> params = new LinkedList<>();
		StringBuilder sb = new StringBuilder(sql);
		
		if(StringUtil.check(userName)) {
			sb.append(" and lower(username) like ?");
			params.add("%".concat(userName.toLowerCase()).concat("%"));
		}
		
		if(null != role) {
			sb.append(" and role like ?");
			params.add(role.toString());
		}
		
		if(StringUtil.check(email)) {
			sb.append(" and email like ?");
			params.add(email.concat("%"));
		}
		
		if(StringUtil.check(address)) {
			sb.append(" and lower(address) like ?");
			params.add("%".concat(address).concat("%"));
		}
		
		try(Connection conn = ConnectionManager.getDbConnection();
				PreparedStatement stmt = conn.prepareStatement(sb.toString())) {
			
			for(int i = 0; i < params.size(); i++) {
				Object obj = params.get(i);
				
				if(obj instanceof Role) {
					Role r = (Role)obj;
					String rName = r.name();
					stmt.setObject(i + 1, rName);
				}
				stmt.setObject(i + 1, obj);
			}
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next())
				result.add(getObject(rs));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	private Employee getObject(ResultSet rs) throws SQLException {
		Employee emp = new Employee();
		emp.setId(rs.getInt("id"));
		emp.setUserName(rs.getString("username"));
		emp.setEmail(rs.getString("email"));
		emp.setPassword(rs.getString("password"));
		emp.setRole(Role.valueOf(rs.getString("role")));
		emp.setSalary(rs.getInt("salary"));
		emp.setDob(rs.getDate("dob").toLocalDate());
		emp.setPhoneNo(rs.getString("phone_no"));
		emp.setAddress(rs.getString("address"));
		emp.setPaid(rs.getBoolean("paid"));
		String img = rs.getString("image");
		if(StringUtil.check(img))
			emp.setImage(img);
		return emp;
	}
	
	public void upload(String file, int id) {
		String sql = "update employee set image = ? where id = ?";
		try(Connection conn = ConnectionManager.getDbConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			
			stmt.setString(1, file);
			stmt.setInt(2, id);
			stmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void paySalary(boolean paid, int id) {
		String sql = "update employee set paid = ? where id = ?";
		try(Connection conn = ConnectionManager.getDbConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			
			stmt.setBoolean(1, paid);
			stmt.setInt(2, id);
			stmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}