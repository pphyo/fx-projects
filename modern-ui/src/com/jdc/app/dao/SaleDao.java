package com.jdc.app.dao;

import java.util.List;

import com.jdc.app.daoimpl.SaleDaoImpl;
import com.jdc.app.entity.Customer;
import com.jdc.app.entity.SaleDTO;

public interface SaleDao {
	
	static SaleDao getInstance() {
		return new SaleDaoImpl();
	}
	
	void save(SaleDTO dto);
	
	List<Customer> findCustomer(String name, String phone, int noOfInvoice, int totalAmount);
	List<Customer> findCustomer(String name);
	Customer findOneCustomer(String name);

}
