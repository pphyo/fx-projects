package com.jdc.app.dao;

import com.jdc.app.daoimpl.SaleDaoImpl;

public interface SaleDao {
	
	static SaleDao getInstance() {
		return new SaleDaoImpl();
	}

}
