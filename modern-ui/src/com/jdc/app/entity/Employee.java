package com.jdc.app.entity;

import com.jdc.app.util.CommonUtil;

public class Employee {

	private int id;
	private String loginId;
	private String loginPassword;
	private String username;
	private Role role;
	private int salary;
	private String phone;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getLoginPassword() {
		return loginPassword;
	}

	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getSalaryWithFormat() {
		return CommonUtil.noFormatMMK(salary);
	}
	
	public enum Role {
		Admin, Manager, Sale
	}
	
}
