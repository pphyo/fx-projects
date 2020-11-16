package com.jdc.app.views;

import com.jdc.app.util.UIUtil;
import com.jdc.app.views.page.Page;
import com.jdc.app.views.page.PageLoader;

import animatefx.animation.FadeIn;
import animatefx.animation.FadeInUp;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

public class SaleManagement implements PageLoader {

    @FXML
    private TextField product;
    @FXML
    private TilePane productBoxContainer;
    @FXML
    private HBox cartMenu;
    @FXML
    private StackPane cartViewHolder;
    @FXML
    private VBox tool;
    
    @FXML
    private void initialize() {
    	loadView(Page.CartOrders);
    	UIUtil.setTooltip(tool, "Search Products");
    }

    @FXML
    void search(MouseEvent event) {

    }
    
    @Override
    public void loadView(Page page) {
    	try {
			Parent root = FXMLLoader.load(getClass().getResource(page.getViewName()));
			loadView(root);
			new FadeInUp(root).play();
		} catch (Exception e) {
			
		}    	
    }
    
    private void loadView(Parent root) {
    	cartViewHolder.getChildren().clear();
    	cartViewHolder.getChildren().add(root);
    }
    
    @FXML
    void loadView(MouseEvent event) {
    	Node node = (Node)event.getSource();
		loadView(Page.valueOf(node.getId()));
		cartMenu.getChildren().stream().filter(n -> n instanceof HBox)
							   .map(n -> (HBox)n)
							   .filter(box -> box.getStyleClass().contains("cart-menu-active"))
							   .findAny()
							   .ifPresent(box -> box.getStyleClass().remove("cart-menu-active"));
		node.getStyleClass().add("cart-menu-active");
		new FadeIn(node).play();
    }

}
