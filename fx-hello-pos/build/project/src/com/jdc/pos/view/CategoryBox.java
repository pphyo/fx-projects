package com.jdc.pos.view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import com.jdc.pos.entity.Category;
import com.jdc.pos.service.CategoryService;
import com.jdc.pos.util.PromptBox;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;

public class CategoryBox extends HBox {
	
	public CategoryBox(Category c) {
		Label name = new Label(c.getName());
		getChildren().add(name);
		getStyleClass().add("cat-box");
		
		this.setOnMouseClicked(event -> {
			if(event.getClickCount() == 2) {
				 Alert alert = new Alert(AlertType.WARNING);
				 try {
					PromptBox.showPrompt(alert, "Delete Category", "Are you sure want to delete this!", new Image(new FileInputStream("res/image/bin.png")));
				 } catch (FileNotFoundException e) {
					e.printStackTrace();
				 }
				 alert.showAndWait().ifPresent(a -> {
					 if(a == ButtonType.CANCEL) {
						 event.consume();
					 } else {
						 this.getChildren().stream().filter(n -> n instanceof Label)
						 	.map(n -> (Label)n)
						 	.forEach(l -> {
						 		CategoryService.getInstance().delete(CategoryService.getInstance().findByNameEqual(l.getText()).getId());
						 	});
					 }
				 });
			 }
		});
	}

}
