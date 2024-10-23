package com.jdc.app.views;

import java.io.FileInputStream;

import com.jdc.app.util.ui.Icons;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MessageBox {

	@FXML
	private HBox node;
	@FXML
	private ImageView img;
	@FXML
	private Label lblMessage;
	
	@FXML
	private void initialize() {
		node.setOnKeyPressed(e -> {
			if(e.getCode() == KeyCode.ENTER || e.getCode() == KeyCode.ESCAPE)
				close();
		});
	}
	
	public static void show(String message, boolean isError) {
		try {
			
			Stage stage = new Stage(StageStyle.TRANSPARENT);
			FXMLLoader loader = new FXMLLoader(MessageBox.class.getResource("MessageBox.fxml"));
			Parent root = loader.load();
			
			MessageBox controller = loader.getController();
			controller.lblMessage.setText(message);
			controller.img.setImage(new Image(new FileInputStream(isError ? Icons.Error.getView() : Icons.Info.getView())));
			
			Scene scene = new Scene(root);
			scene.setFill(Color.TRANSPARENT);
			stage.setScene(scene);
			stage.show();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void close() {
		lblMessage.getScene().getWindow().hide();
	}
	
}
