package com.solt.jdc.views;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import static com.solt.jdc.util.CommonUtil.*;

import com.solt.jdc.util.MessageBox;
import com.solt.jdc.util.StudentException;

public class Login {

	@FXML
	private TextField txtUsername;
	@FXML
	private TextField txtPassword;
	
	public void login() {
		
		try {
			
			if(isEmpty(txtUsername.getText()))
				throw new StudentException("Please enter username!");
			
			if(isEmpty(txtPassword.getText()))
				throw new StudentException("Please enter password!");
			
			String username = txtUsername.getText();
			String password = txtPassword.getText();
			
			if(!username.equals(getInfo("app.user")))
				throw new StudentException("Please enter correct username!");
			
			if(!encodePassword(password).equals(getInfo("app.pwd")))
				throw new StudentException("Please enter correct password!");
			
			RootFrame.show();
			close();
			
		} catch (Exception e) {
			MessageBox.errorBox(e);
		}
		
	}
	
	public void close() {
		txtUsername.getScene().getWindow().hide();
	}
	
}
