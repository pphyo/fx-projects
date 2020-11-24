package com.jdc.app.dao;

import com.jdc.app.daoimpl.SaleDaoImpl;
import com.jdc.app.entity.SaleDTO;

public interface SaleDao {
	
	static SaleDao getInstance() {
		return new SaleDaoImpl();
	}
	
	void save(SaleDTO dto);

}
