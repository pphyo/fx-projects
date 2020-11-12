package com.jdc.pos.view;

import java.io.FileInputStream;
import java.util.function.Consumer;

import com.jdc.pos.entity.Category;
import com.jdc.pos.entity.Product;
import com.jdc.pos.service.CategoryService;
import com.jdc.pos.util.PosException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ProductEdit {

	@FXML
	private Label title;
	@FXML
	private Label info;
	@FXML
	private ComboBox<Category> category;
	@FXML
	private TextField name;
	@FXML
	private TextField price;
	@FXML
	private TextArea description;
	@FXML
	private GridPane grid;
	
	private Product product;
	private Consumer<Product> listener;
	private CategoryService catService;
	
	@FXML
	private void initialize() {
		catService =  CategoryService.getInstance();
		category.getItems().addAll(catService.findByName(null));
		
		grid.setOnKeyPressed(e -> {
			if(e.getCode() == KeyCode.ENTER)
				save();
			if(e.getCode() == KeyCode.ESCAPE)
				close();
		});
	}
	
	public static void show(Product p, Consumer<Product> listener) {
		try {
			Stage stage = new Stage();
			FXMLLoader loader = new FXMLLoader(ProductEdit.class.getResource("ProductEdit.fxml"));
			Parent root = loader.load();
			ProductEdit controller = loader.getController();
			
			boolean isNew = null == p;
			
			controller.product = p;
			controller.listener = listener;
			controller.title.setText(isNew ? "Add Product" : "Edit Product");
			stage.setTitle(isNew ? "Add Product" : "Update Product");
			
			Image addImg = new Image(new FileInputStream("res/image/pro-add.png"));
			Image editImg = new Image(new FileInputStream("res/image/edit.png"));
			
			stage.getIcons().add(isNew ? addImg : editImg);
			
			if(!isNew)
				controller.setDataToView(p);
			
			stage.setScene(new Scene(root));
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void save() {
		try {
			
			if(null == product)
				product = new Product();
			
			if(null == category.getValue())
				throw new PosException("Please select one category!");
			product.setCategory(category.getValue());
			
			if(name.getText().isEmpty())
				throw new PosException("Please enter product name!");
			product.setName(name.getText());
			
			int value = price.getText().isEmpty() ? 0 : Integer.parseInt(price.getText());
			if(price.getText().isEmpty()) {
				throw new PosException("Please enter price!");
			} else {
				if(value <= 0)
					throw new PosException("Please enter correct price!");
			}
			product.setPrice(Integer.parseInt(price.getText()));
			
			product.setDescription(description.getText());
			
			listener.accept(product);
			close();
			
		} catch(Exception e) {
			info.setText(e.getMessage());
		}
	}
	
	public void close() {
		category.getScene().getWindow().hide();
	}
	
	private void setDataToView(Product p) {
		category.setValue(p.getCategory());
		name.setText(p.getName());
		price.setText(String.valueOf(p.getPrice()));
		description.setText(p.getDescription());
	}
	
}