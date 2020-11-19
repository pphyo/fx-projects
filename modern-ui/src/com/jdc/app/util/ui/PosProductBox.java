package com.jdc.app.util.ui;

import com.jdc.app.entity.Product;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class PosProductBox extends VBox {
	
	public PosProductBox(Product p) {
		Label product = new Label(p.getName());
		Label category = new Label(p.getCategory().getName());
		Label price = new Label(String.valueOf(p.getPrice()));
		
		product.getStyleClass().add("pro-price");
		category.getStyleClass().add("c-name");
		price.getStyleClass().add("pro-price");
		
		getStyleClass().add("cart-product-box");
		getChildren().addAll(product, category, price);
	}

}
