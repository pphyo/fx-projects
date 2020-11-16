package com.jdc.app.dao;

import java.util.List;

import com.jdc.app.daoimpl.EmployeeDaoImpl;
import com.jdc.app.entity.Employee;
import com.jdc.app.entity.Employee.Role;

public interface EmployeeDao {
	
	static EmployeeDao getInstance() {
		return new EmployeeDaoImpl();
	}
	
	void save(Employee e);
	void delete(Employee e);
	List<Employee> find(String name, int salary, Role role);
	List<Employee> getAll();

}