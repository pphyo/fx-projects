package com.jdc.pos.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Invoice {
	
	private int id;
	private LocalDate invoiceDate;
	private LocalTime invoiceTime;
	private int tax;
	private int subTotal;
	private int total;
	private Employee member;

	@Override
	public String toString() {
		return invoiceDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
	}

}
