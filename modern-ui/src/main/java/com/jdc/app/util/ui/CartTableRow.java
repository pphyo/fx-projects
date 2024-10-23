package com.jdc.app.util.ui;

import java.util.function.Consumer;

import com.jdc.app.entity.SaleOrder;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;

public class CartTableRow<T extends SaleOrder> extends HBox {
	
	private T rowItem;
	
	public CartTableRow(T order, Consumer<SaleOrder> plusListener, Consumer<SaleOrder> minusListener, Consumer<CartTableRow<T>> deleteListener) {
		rowItem = order;
		this.setId(toString());
		// Delete Box
		VBox deleteBox = new VBox();
		UIUtil.setTooltip(deleteBox, "Delete item");
		SVGPath delete = Icons.Delete.getSVGPath(Color.RED);
		
		deleteBox.getStyleClass().add("delete-box");
		
		deleteBox.getChildren().add(delete);
		
		// Product name and price box
		VBox namePriceBox = new VBox();
		Label name = new Label(order.getProduct().getName());
		Label price = new Label("@ ".concat(String.valueOf(order.getUnitPrice())));
		
		namePriceBox.getStyleClass().add("name-price-box");
		name.getStyleClass().add("tbl-row-pro-name");
		price.getStyleClass().add("tbl-row-font-prop");

		namePriceBox.getChildren().addAll(name, price);
		
		// Quantity Box
		HBox qtyBox = new HBox();
		qtyBox.getStyleClass().add("qty-box");
		
		VBox qtyLabelBox = new VBox();
		qtyLabelBox.getStyleClass().add("qty-lbl-box");

		Label qtyLabel = new Label(String.valueOf(order.getQuantity()));
		qtyLabel.getStyleClass().add("tbl-row-font-prop");
		
		qtyLabelBox.getChildren().add(qtyLabel);
		
		VBox qtyBtnBox = new VBox();
		VBox qtyBtnPlus = new VBox();
		VBox qtyBtnMinus = new VBox();
		Label plus = new Label("+");
		Label minus = new Label("-");
		
		plus.getStyleClass().add("qty-btn-lbl");
		minus.getStyleClass().add("qty-btn-lbl");
		qtyBtnPlus.getStyleClass().add("qty-btn-plus");
		qtyBtnMinus.getStyleClass().add("qty-btn-minus");
		
		qtyBtnPlus.getChildren().add(plus);
		qtyBtnMinus.getChildren().add(minus);
		qtyBtnBox.getChildren().addAll(qtyBtnPlus, qtyBtnMinus);
		
		qtyBox.getChildren().addAll(qtyLabelBox, qtyBtnBox);
		
		
		// Total Box
		HBox totBox = new HBox();
		Label total = new Label(String.valueOf(order.getTotal()));
		
		total.getStyleClass().add("tbl-row-font-prop");
		totBox.getStyleClass().add("tot-box");
		
		totBox.getChildren().add(total);
		
		getStyleClass().add("cart-tbl-row");
		getChildren().addAll(deleteBox, namePriceBox, qtyBox, totBox);
		
		qtyBtnPlus.setOnMouseClicked(e -> {
			plusListener.accept(order);
			getQtyTotal(order, qtyLabel, total);
		});
		
		qtyBtnMinus.setOnMouseClicked(e -> {	
			minusListener.accept(order);
			getQtyTotal(order, qtyLabel, total);
		});
		
		deleteBox.setOnMouseClicked(e -> deleteListener.accept(this));
		
	}

	private void getQtyTotal(T order, Label qtyLabel, Label total) {
		qtyLabel.setText(String.valueOf(order.getQuantity()));
		total.setText(String.valueOf(order.getQuantity() * order.getUnitPrice()));
	}

	public T getRowItem() {
		return rowItem;
	}

	public void setRowItem(T rowItem) {
		this.rowItem = rowItem;
	}

}