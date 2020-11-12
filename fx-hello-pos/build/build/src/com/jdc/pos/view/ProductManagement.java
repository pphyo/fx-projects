package com.jdc.pos.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import com.jdc.pos.entity.Category;
import com.jdc.pos.entity.Product;
import com.jdc.pos.service.CategoryService;
import com.jdc.pos.service.ProductService;
import com.jdc.pos.util.PromptBox;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class ProductManagement {
	
	@FXML
	private ComboBox<Category> category;
	@FXML
	private TextField name;
	@FXML
	private TextField price;
	@FXML
	private TableView<Product> proList;
	@FXML
	private VBox node;
	
	private CategoryService catService;
	private ProductService proService;
	

	@FXML
	private void initialize() {
		catService = CategoryService.getInstance();
		proService = ProductService.getInstance();
		category.getItems().addAll(catService.findByName(null));
		
		search();
		
		proList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);		
		
		createMenu();
		
		category.valueProperty().addListener(e -> search());
		name.textProperty().addListener(e -> search());
		price.textProperty().addListener(e -> search());
		
		proList.setOnMouseClicked(e -> {
			if(e.getClickCount() == 2)
				edit();
		});
		
	}
	
	public void add() {
		ProductEdit.show(null, this::save);
	}
	
	private void save(Product p) {
		proService.save(p);
		search();
	}
	
	public void search() {
		proList.getItems().clear();
		List<Product> list = proService.find(category.getValue(), name.getText(), price.getText());
		proList.getItems().addAll(list);
	}
	
	public void clear() {
		category.setValue(null);
		name.clear();
		price.clear();
	}
	
	private void createMenu() {
		MenuItem edit = new MenuItem("Edit");
		edit.setOnAction(e -> edit());
		
		MenuItem delete = new MenuItem("Delete");
		delete.setOnAction(e -> delete(e));
		
		ContextMenu menu = new ContextMenu(edit, delete);
		proList.setContextMenu(menu);
	}
	
	private void edit() {
		Product p = proList.getSelectionModel().getSelectedItem();
		ProductEdit.show(p, this::save);
		search();
	}
	
	private void delete(ActionEvent event) {
		List<Product> list = proList.getSelectionModel().getSelectedItems();
		Alert alert = new Alert(AlertType.WARNING);
		try {
			PromptBox.showPrompt(alert, list.size() == 1 ? "Delete Product" : "Delete Products", list.size() == 1 ? "Are you sure want to delete this!" : "Are you sure want to delete this products!", new Image(new FileInputStream("res/image/bin.png")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		alert.showAndWait().ifPresent(e -> {
			if(e == ButtonType.CANCEL) {
				event.consume();
			} else {
				if(list.size() > 1) {
					for(Product p : list)
						proService.delete(p.getId());
				} else {
					proService.delete(list.get(0).getId());
				}

				search();
			}
		});
		
	}
	
	public void upload() {
		try {
			FileChooser fc = new FileChooser();
			fc.setTitle("Product Upload");
			fc.setSelectedExtensionFilter(new ExtensionFilter("csv, tsv, txt", "*.csv", ".tsv", ".txt"));
			fc.setInitialDirectory(new File(System.getProperty("user.home"), "Desktop"));
			File file = fc.showOpenDialog(name.getScene().getWindow());
			proService.upload(file);
			search();
		} catch (Exception e) {
			e.printStackTrace();
		}
		search();
	}
}