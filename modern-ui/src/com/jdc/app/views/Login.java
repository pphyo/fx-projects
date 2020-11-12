package com.jdc.app.views;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class Login {

	@FXML
	private TextField username;
	@FXML
	private PasswordField password;
	@FXML
	
	public void initialize() {
		
	}
	
	public void login() {
		RootFrame.show();
		close();
	}
	
	private void close() {
		username.getScene().getWindow().hide();
	}
	
}
