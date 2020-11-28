package com.jdc.app.views;

import com.jdc.app.dao.SaleDao;
import com.jdc.app.entity.Invoice;
import com.jdc.app.util.ui.UIUtil;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class SaleHistory {
	
	@FXML
	private DatePicker dpFrom;
	@FXML
	private DatePicker dpTo;
	@FXML
	private TextField txtParams;
	@FXML
	private VBox searchBox;
	@FXML
	private TableView<Invoice> tblInvoiceList;
	
	private SaleDao saleDao;
	
	@FXML
	private void initialize() {
		saleDao = SaleDao.getInstance();
		
		search();
		
		UIUtil.setTooltip(searchBox, "Search sale histroy");
		
		tblInvoiceList.setPlaceholder(new Label("There is no history in the table."));
	}
	
	public void search() {
		
	}

}
