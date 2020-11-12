package com.jdc.pos.entity;

import java.time.format.DateTimeFormatter;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SaleOrder {

	private int id;
	private int quantity;
	private int unitPrice;
	private int total;
	private Product product;
	private Invoice invoice;

	public String getProductName() {
		return product.getName();
	}
	
	public String getSaleDate() {
		return invoice.getInvoiceDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
	}
	
	public String getSaleTime() {
		return invoice.getInvoiceTime().format(DateTimeFormatter.ofPattern("HH:mm:ss a"));
	}
	
	public String getCreator() {
		return invoice.getMember().getUserName();
	}
	
}
