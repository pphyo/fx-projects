package com.jdc.app.views;

import java.util.function.Consumer;

import com.jdc.app.PosException;
import com.jdc.app.entity.Customer;
import com.jdc.app.util.StringUtil;
import com.jdc.app.util.ui.UIUtil.ModalController;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class CustomerEdit implements ModalController<Customer> {
	
	@FXML
	private Label lblTitle;
	@FXML
	private TextField txtName;
	@FXML
	private TextField txtPhone;
	@FXML
	private TextField txtAddress;
	@FXML
	private GridPane grdNode;
	
	private Customer data;
	private Consumer<Customer> listener;
	
	@FXML
	private void initialize() {
		
	}
	
	public void save() {
		
		try {
			
			if(StringUtil.isEmpty(txtName.getText()))
				throw new PosException("Please enter customer name!");
			data.setName(txtName.getText());
			
			if(txtPhone.getText().length() > 11)
				throw new PosException("Phone number is only 11 characters at most!");
			data.setPhone(txtPhone.getText());
			
			data.setAddress(txtAddress.getText());
			
			listener.accept(data);
			
			close();
			
		} catch (Exception e) {
			MessageBox.show(e.getMessage(), false);
		}
		
	}
	
	public void close() {
		lblTitle.getScene().getWindow().hide();
	}

	@Override
	public void init(Customer data, Consumer<Customer> listener) {
		this.data = data;
		this.listener = listener;
		
		if(null == data) {
			lblTitle.setText("Add Customer");
			this.data = new Customer();
		} else {
			lblTitle.setText("Edit Customer");
			txtName.setText(data.getName());
			txtPhone.setText(data.getPhone());
			txtAddress.setText(data.getAddress());
		}
	}

}