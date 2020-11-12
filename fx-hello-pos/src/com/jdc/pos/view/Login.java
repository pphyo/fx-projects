package com.jdc.pos.view;

import java.io.FileInputStream;

import com.jdc.pos.entity.Employee;
import com.jdc.pos.service.EmployeeService;
import com.jdc.pos.util.PosException;
import com.jdc.pos.util.Security;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;

public class Login {
	
	@FXML
	private Label info;
	@FXML
	private TextField userName;
	@FXML
	private PasswordField password;
	@FXML
	private GridPane grid;
	@FXML
	private ImageView pic;

	private EmployeeService empService;
	
	@FXML
	private void initialize() {
		try {
			pic.setImage(new Image(new FileInputStream("res/image/monitor.png")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		empService = EmployeeService.getInstance();
		
		grid.setOnKeyPressed(e -> {
			if(e.getCode() == KeyCode.ENTER)
				login();
			if(e.getCode() == KeyCode.ESCAPE)
				close();
		});
	}
	
	public void login() {
		try {
			
			if(userName.getText().isEmpty())
				throw new PosException("Please enter user name!");
			if(password.getText().isEmpty())
				throw new PosException("Please enter password!");
			
			Employee emp = empService.getOne(userName.getText(), Security.encodePassword(password.getText()));
			if(null == emp)
				throw new PosException("User name or password is incorrect!");
			
			Security.setMember(emp);
			
			PosFrame.show(emp);
			close();
			
		} catch (Exception e) {
			info.setText(e.getMessage());
		}
	}
	
	public void close() {
		info.getScene().getWindow().hide();
	}

}