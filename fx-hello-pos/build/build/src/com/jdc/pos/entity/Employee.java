package com.jdc.pos.entity;

import java.time.LocalDate;

import com.jdc.pos.util.DecimalFormatConverter;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Employee {
	
	private int id;
	private String userName;
	private String email;
	private String password;
	private Role role;
	private LocalDate dob;
	private String phoneNo;
	private String address;
	private int salary;
	private String image;
	private boolean paid;
	
	public String getSalaryWithFormat() {
		return DecimalFormatConverter.format(salary);
	}

}
