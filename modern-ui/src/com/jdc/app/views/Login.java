package com.jdc.app.views;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class Login {

	@FXML
	private TextField txtUsername;
	@FXML
	private PasswordField pwdPassword;
	@FXML
	private GridPane grdNode;
	
	public void initialize() {
		
	}
	
	public void login() {
		RootFrame.show();
		close();
	}
	
	private void close() {
		txtUsername.getScene().getWindow().hide();
	}
	
}
