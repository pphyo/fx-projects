package com.jdc.pos.view;

import java.io.FileInputStream;
import java.util.function.Consumer;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class TaxView {

	@FXML
	private TextField value;
	@FXML
	private HBox node;
	
	private Consumer<Integer> listener;
	
	@FXML
	private void initialize() {
		node.setOnKeyPressed(e -> {
			if(e.getCode() == KeyCode.ESCAPE)
				value.getScene().getWindow().hide();
			if(e.getCode() == KeyCode.ENTER)
				setTaxRate();
		});
	}
	
	public static void show(Consumer<Integer> listener) {
		try {
			FXMLLoader loader = new FXMLLoader(TaxView.class.getResource("TaxView.fxml"));
			Parent root = loader.load();
			TaxView controller = loader.getController();
			
			controller.listener = listener;
			
			Stage stage = new Stage(StageStyle.UNDECORATED);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setScene(new Scene(root));
			stage.setTitle("Set Tax");
			stage.getIcons().add(new Image(new FileInputStream("res/image/taxes.png")));
			stage.show();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setTaxRate() {
		int val = value.getText().isEmpty() ? 0 : Integer.parseInt(value.getText());
		listener.accept(val);
		value.getScene().getWindow().hide();
	}

}