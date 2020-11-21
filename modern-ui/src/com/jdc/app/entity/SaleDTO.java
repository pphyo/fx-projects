package com.jdc.app.entity;

import java.util.ArrayList;
import java.util.List;

public class SaleDTO {

	private Customer customer;
	private Invoice invoice;
	private List<SaleOrder> orderList;

	public SaleDTO() {
		customer = new Customer();
		invoice = new Invoice();
		orderList = new ArrayList<>();
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Invoice getInvoice() {
		return invoice;
	}

	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

	public List<SaleOrder> getOrderList() {
		return orderList;
	}

	public void setOrderList(List<SaleOrder> orderList) {
		this.orderList = orderList;
	}

}
