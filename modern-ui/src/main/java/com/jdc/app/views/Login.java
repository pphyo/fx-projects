package com.jdc.app.views;

import com.jdc.app.PosException;
import com.jdc.app.dao.EmployeeDao;
import com.jdc.app.entity.Employee;
import com.jdc.app.util.SecurityUtils;
import com.jdc.app.util.StringUtil;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;

public class Login {

	@FXML
	private TextField txtLoginId;
	@FXML
	private PasswordField txtLoginPassword;
	@FXML
	private GridPane grdNode;
	
	public void initialize() {
		grdNode.setOnKeyPressed(e -> {
			if(e.getCode() == KeyCode.ENTER)
				login();
			if(e.getCode() == KeyCode.ESCAPE)
				close();
		});
	}
	
	public void login() {
		try {
			
			if(StringUtil.isEmpty(txtLoginId.getText()))
				throw new PosException("Please enter login id!");
			
			if(StringUtil.isEmpty(txtLoginPassword.getText()))
				throw new PosException("Please enter login password!");
			
			Employee emp = EmployeeDao.getInstance().getOne(txtLoginId.getText(), SecurityUtils.encodePassword(txtLoginPassword.getText()));
			
			if(null == emp)
				throw new PosException("User not found!");
			
			SecurityUtils.setEmployee(emp);
			RootFrame.show();
			close();
		} catch (Exception e) {
			MessageBox.show(e.getMessage(), true);
		}
	}
	
	private void close() {
		txtLoginId.getScene().getWindow().hide();
	}
	
}
