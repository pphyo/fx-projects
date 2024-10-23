package com.jdc.app;

import com.jdc.app.dao.EmployeeDao;
import com.jdc.app.entity.Employee;
import com.jdc.app.entity.Employee.Role;
import com.jdc.app.util.SecurityUtils;
import com.jdc.app.views.Login;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ModernUIApp extends Application {
	
	@Override
	public void init() throws Exception {
		EmployeeDao dao = EmployeeDao.getInstance();
		
		if(dao.getAll().size() == 0) {
			Employee admin = new Employee();
			admin.setLoginId("admin");
			admin.setLoginPassword(SecurityUtils.encodePassword("admin"));
			admin.setUsername("Admin");
			admin.setSalary(1000000);
			admin.setRole(Role.Admin);
			admin.setPhone("09420304001");
			dao.save(admin);
		}
	}

	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(Login.class.getResource("Login.fxml"));
		Scene scene = new Scene(root);
		scene.setFill(Color.TRANSPARENT);
		stage.setScene(scene);
		stage.initStyle(StageStyle.TRANSPARENT);
		stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
