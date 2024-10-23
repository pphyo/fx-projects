package com.jdc.app.dao;

import java.util.List;

import com.jdc.app.daoimpl.EmployeeDaoImpl;
import com.jdc.app.entity.Employee;

public interface EmployeeDao {
	
	static EmployeeDao getInstance() {
		return new EmployeeDaoImpl();
	}
	
	void save(Employee emp);
	void delete(Employee emp);
	List<Employee> find(String name, int salary, String role);
	List<Employee> getAll();
	Employee getOne(String id, String password);

}
