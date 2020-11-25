package com.jdc.app.views;

import java.util.function.Consumer;

import com.jdc.app.PosException;
import com.jdc.app.entity.Employee;
import com.jdc.app.entity.Employee.Role;
import com.jdc.app.util.StringUtil;
import com.jdc.app.util.ui.UIUtil.ModalController;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;

public class EmployeeEdit implements ModalController<Employee> {
	
	@FXML
	private Label lblTitle;
	@FXML
	private TextField txtLoginId;
	@FXML
	private PasswordField txtLoginPassword;
	@FXML
	private TextField txtUsername;
	@FXML
	private ComboBox<Role> cbxRole;
	@FXML
	private TextField txtSalary;
	@FXML
	private TextField txtPhone;
	@FXML
	private GridPane grdNode;
	
	private Employee data;
	private Consumer<Employee> listener;
	
	@FXML
	private void initialize() {
		cbxRole.getItems().addAll(Role.values());
		grdNode.setOnKeyPressed(e -> {
			if(e.getCode() == KeyCode.ENTER)
				save();
			if(e.getCode() == KeyCode.ESCAPE)
				close();
		});
	}
	
	public void save() {
		try {
			
			if(StringUtil.isEmpty(txtLoginId.getText()))
				throw new PosException("Please enter login id!");
			data.setLoginId(txtLoginId.getText());
			
			if(StringUtil.isEmpty(txtLoginPassword.getText()))
				throw new PosException("Please enter login password!");
			data.setLoginPassword(txtLoginPassword.getText());
			
			if(StringUtil.isEmpty(txtUsername.getText()))
				throw new PosException("Please enter username!");
			data.setUsername(txtUsername.getText());
			
			if(null == cbxRole.getValue())
				throw new PosException("Please select role of employee!");
			data.setRole(cbxRole.getValue());
			
			if(StringUtil.isEmpty(txtSalary.getText())) {
				throw new PosException("Please enter salary!");
			} else {
				if(Integer.parseInt(txtSalary.getText()) <= 0)
					throw new PosException("Please enter correct value!");
			}
			data.setSalary(Integer.parseInt(txtSalary.getText()));
			
			data.setPhone(txtPhone.getText());
			
			listener.accept(data);
			close();
			
		} catch (NumberFormatException e) {
			MessageBox.show("Please enter digit only for salary!", false);
		} catch (Exception e) {
			MessageBox.show(e.getMessage(), false);
		}
	}
	
	public void close() {
		txtLoginId.getScene().getWindow().hide();
	}

	@Override
	public void init(Employee data, Consumer<Employee> listener) {
		this.data = data;
		this.listener = listener;
		if(null == data) {
			this.data = new Employee();
			lblTitle.setText("Add Employee");
		} else {
			lblTitle.setText("Edit Employee");
			txtLoginId.setText(data.getLoginId());
			txtLoginPassword.setText(data.getLoginPassword());
			txtUsername.setText(data.getUsername());
			cbxRole.setValue(data.getRole());
			txtSalary.setText(String.valueOf(data.getSalary()));
			txtPhone.setText(data.getPhone());
		}
	}

}
