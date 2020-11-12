package com.jdc.pos;

import java.io.FileInputStream;

import com.jdc.pos.view.Login;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class HelloPosApplication extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		stage.setScene(new Scene(FXMLLoader.load(Login.class.getResource("Login.fxml"))));
		stage.setTitle("Log In");
		stage.getIcons().add(new Image(new FileInputStream("res/image/login-color.png")));
		stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}