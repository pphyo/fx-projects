package com.jdc.app.util.ui;

import java.util.function.Consumer;

import com.jdc.app.entity.Product;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;

public class PosProductBox extends VBox {
	
	public PosProductBox(Product p, Consumer<Product> clickListener) {
		Label product = new Label(p.getName());
		Label category = new Label(p.getCategory().getName());
		Label price = new Label(String.valueOf(p.getPrice()).concat(" MMK"));
		
		product.getStyleClass().add("pro-price");
		category.getStyleClass().add("c-name");
		price.getStyleClass().add("pro-price");
		
		HBox box = new HBox();
		Label stock = new Label(p.isStock() ? "In stock" : "Out of stock");
		SVGPath svg = p.isStock() ? Icons.InStock.getSVGPath(Color.BLACK) : Icons.OutOfStock.getSVGPath(Color.RED);
		
		box.getChildren().addAll(svg, stock);
		
		getStyleClass().add("cart-product-box");
		getChildren().addAll(product, category, price, box);
		
		this.setOnMouseClicked(e -> {
			if(e.getClickCount() == 2)
				clickListener.accept(p);
		});
	}

}
