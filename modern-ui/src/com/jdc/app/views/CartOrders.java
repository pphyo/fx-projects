package com.jdc.app.views;

import com.jdc.app.util.UIUtil;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class CartOrders {
	
	@FXML
	private TextField txtcustName;
	@FXML
	private TextField txtcustPhone;
	@FXML
	private TextField txtcustAddress;
	@FXML
	private TextField txtTax;
	@FXML
	private VBox tool;
	@FXML
	private Label lblHeaderTotal;
	@FXML
	private TableView<?> tblOrderList;
	@FXML
	private TableColumn<?, ?> delCol;
	@FXML
	private TableColumn<?, ?> nameAndPriceCol;
	@FXML
	private TableColumn<?, ?> qtyCol;
	@FXML
	private TableColumn<?, ?> totalCol;
	@FXML
	private Label lblTax;
	@FXML
	private Label lblDiscount;
	@FXML
	private Label lblSubTotal;
	@FXML
	private Label lblTotal;
	
	@FXML
	private void initialize() {
		UIUtil.setTooltip(tool, "New Invoice");		
	}
	
	public void createNewInvoice() {
		
	}
	
	public void save() {
		
	}
	
	public void payNow() {
		
	}

}