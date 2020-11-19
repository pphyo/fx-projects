package com.jdc.app.util.ui;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class MessageBox {
	
	public static void showErrorBox(Exception content, String title) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(title);
		alert.setContentText(content instanceof NumberFormatException ? "Please enter digit only for price!" : content.getMessage());
		alert.show();
	}
	
	public static void showBox(String content, String title, AlertType type) {
		Alert alert = new Alert(type);
		alert.setTitle(title);
		alert.setContentText(content);
		alert.show();
	}

}
