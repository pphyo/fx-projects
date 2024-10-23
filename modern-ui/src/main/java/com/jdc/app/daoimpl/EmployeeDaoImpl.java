package com.jdc.app.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.jdc.app.dao.EmployeeDao;
import com.jdc.app.entity.Employee;
import com.jdc.app.entity.Employee.Role;
import com.jdc.app.util.DatabaseConnection;
import com.jdc.app.util.SecurityUtils;
import com.jdc.app.util.StringUtil;

public class EmployeeDaoImpl implements EmployeeDao {
	
	private final String SELECT = "select * from employee where 1 = 1";

	@Override
	public void save(Employee emp) {
		String insertSql = "insert into employee (login_id, login_password, username, role, salary, phone) values (?, ?, ?, ?, ?, ?)";
		String updateSql = "update employee set login_id = ?, login_password = ?, username = ?, role = ?, salary = ?, phone = ? where id = ?";
		
		boolean isNew = emp.getId() == 0;
		
		try(Connection conn = DatabaseConnection.getDbConnection();
				PreparedStatement stmt = conn.prepareStatement(isNew ? insertSql : updateSql)) {
		
			stmt.setString(1, emp.getLoginId());
			stmt.setString(2, SecurityUtils.encodePassword(emp.getLoginPassword()));
			stmt.setString(3, emp.getUsername());
			stmt.setString(4, emp.getRole().toString());
			stmt.setInt(5, emp.getSalary());
			stmt.setString(6, emp.getPhone());
			if(!isNew)
				stmt.setInt(7, emp.getId());
			
			stmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(Employee emp) {
		String sql = "delete from employee where id = ?";
		
		try(Connection conn = DatabaseConnection.getDbConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
		
			stmt.setInt(1, emp.getId());
			
			stmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Employee> find(String name, int salary, String role) {
		
		List<Employee> result = new ArrayList<>();
		
		try(Connection conn = DatabaseConnection.getDbConnection();
				PreparedStatement stmt = conn.prepareStatement(SELECT)) {
		
			ResultSet rs = null;
			
			if(!StringUtil.isEmpty(name)) {
				String nameSql = SELECT.concat(" and lower(username) like ?");
				
				try(PreparedStatement nameStmt = conn.prepareStatement(nameSql)) {
					
					nameStmt.setString(1, name.toLowerCase().concat("%"));
					rs = nameStmt.executeQuery();
					
					while(rs.next())
						result.add(getEmployeeObject(rs));
					
				}
			}
			
			boolean isNotExistReuslt = result.size() == 0;
			
			if(isNotExistReuslt) {
				if(salary > 0) {
					String salarySql = SELECT.concat(" and salary >= ?");
					
					try(PreparedStatement salaryStmt = conn.prepareStatement(salarySql)) {
						salaryStmt.setInt(1, salary);
						rs = salaryStmt.executeQuery();
						
						while(rs.next())
							result.add(getEmployeeObject(rs));
					}
				}
			}
			
			if(isNotExistReuslt) {
				if(!StringUtil.isEmpty(role)) {
					String roleSql = SELECT.concat(" and lower(role) like ?");
					
					try(PreparedStatement roleStmt = conn.prepareStatement(roleSql)) {
						roleStmt.setString(1, role.toLowerCase().concat("%"));
						rs = roleStmt.executeQuery();
						
						while(rs.next())
							result.add(getEmployeeObject(rs));
					}
				}
			}
			
			isNotExistReuslt = result.size() == 0;
			
			if(isNotExistReuslt && StringUtil.isEmpty(name)) {
				rs = stmt.executeQuery();
				
				while(rs.next())
					result.add(getEmployeeObject(rs));
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public List<Employee> getAll() {
		return find(null, 0, null);
	}
	
	@Override
	public Employee getOne(String id, String password) {
		try(Connection conn = DatabaseConnection.getDbConnection();
				PreparedStatement stmt = conn.prepareStatement(SELECT.concat(" and lower(login_id) like ? and login_password like ?"))) {
			
			stmt.setString(1, id.toLowerCase());
			stmt.setString(2, password);
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next())
				return getEmployeeObject(rs);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private Employee getEmployeeObject(ResultSet rs) throws SQLException {
		Employee emp = new Employee();
		emp.setId(rs.getInt("id"));
		emp.setLoginId(rs.getString("login_id"));
		emp.setLoginPassword(rs.getString("login_password"));
		emp.setUsername(rs.getString("username"));
		emp.setRole(Role.valueOf(rs.getString("role")));
		emp.setSalary(rs.getInt("salary"));
		emp.setPhone(rs.getString("phone"));
		return emp;
	}

}
