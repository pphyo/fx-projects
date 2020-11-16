package com.jdc.app.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Invoice {

	private int id;
	private LocalDate invoiceDate;
	private LocalTime invoiceTime;
	private int tax;
	private int discount;
	private int subTotal;
	private int total;
	private Employee employee;
	private Customer customer;
	
}
