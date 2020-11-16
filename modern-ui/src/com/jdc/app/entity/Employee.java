package com.jdc.app.entity;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Employee {

	private int id;
	private String loginId;
	private String loginPassword;
	private String username;
	private Role role;
	private int salary;
	private String phone;
	private String address;
	
	public enum Role {
		Admin, Manager, Sale
	}
	
}
