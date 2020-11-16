package com.jdc.app.daoimpl;

import java.util.List;

import com.jdc.app.dao.EmployeeDao;
import com.jdc.app.entity.Employee;
import com.jdc.app.entity.Employee.Role;

public class EmployeeDaoImpl implements EmployeeDao {

	@Override
	public void save(Employee e) {
		
	}

	@Override
	public void delete(Employee e) {
		
	}

	@Override
	public List<Employee> find(String name, int salary, Role role) {
		return null;
	}

	@Override
	public List<Employee> getAll() {
		return find(null, 0, null);
	}

}
