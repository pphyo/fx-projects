package com.jdc.pos.entity;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SaleDTO {

	private Invoice invoice;
	private List<SaleOrder> orders;

	public SaleDTO() {
		invoice = new Invoice();
		orders = new ArrayList<>();
	}
	
}
