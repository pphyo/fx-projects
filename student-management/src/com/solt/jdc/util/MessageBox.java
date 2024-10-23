package com.solt.jdc.util;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class MessageBox {

	public static void errorBox(Exception e) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Empty Field");
		alert.setContentText(e instanceof NumberFormatException ? "Please type digit only in year!" : e.getMessage());
		alert.show();
	}
	
}
