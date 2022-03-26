package com.jdc.pos.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Invoice {
	
	private int id;
	private LocalDate invoiceDate;
	private LocalTime invoiceTime;
	private int tax;
	private int subTotal;
	private int total;
	private Employee member;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDate getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(LocalDate invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public LocalTime getInvoiceTime() {
		return invoiceTime;
	}

	public void setInvoiceTime(LocalTime invoiceTime) {
		this.invoiceTime = invoiceTime;
	}

	public int getTax() {
		return tax;
	}

	public void setTax(int tax) {
		this.tax = tax;
	}

	public int getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(int subTotal) {
		this.subTotal = subTotal;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public Employee getMember() {
		return member;
	}

	public void setMember(Employee member) {
		this.member = member;
	}

	@Override
	public String toString() {
		return invoiceDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
	}

}
