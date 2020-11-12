package com.jdc.pos.util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class PromptBox {

	public static void showPrompt(Alert alert, String title, String content, Image image) {
		alert.setTitle(title);
		alert.setContentText(content);
		Stage window = (Stage)alert.getDialogPane().getScene().getWindow();
		window.getIcons().add(image);
	}
	
	public static void showPrompt(String content, String title, Image image) {
		Dialog<String> dialog = new Dialog<>();
		dialog.setContentText(content);
		dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
		Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
		stage.getIcons().add(image);
		stage.setTitle(title);
		dialog.show();
	}
	
}
