package com.jdc.app.util;

import com.jdc.app.entity.Employee;

public class Security {

	private static Employee employee;

	public static Employee getEmployee() {
		return employee;
	}

	public static void setEmployee(Employee employee) {
		Security.employee = employee;
	}
	
}
