package com.jdc.app.entity;

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

}
