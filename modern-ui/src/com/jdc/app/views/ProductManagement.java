package com.jdc.app.views;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.jdc.app.dao.ProductDao;
import com.jdc.app.entity.Product;
import com.jdc.app.util.ui.TableCellFactory;
import com.jdc.app.util.ui.TextFieldUtil;
import com.jdc.app.util.ui.UIUtil;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class ProductManagement extends TableCellFactory<Product> {
	
	@FXML
	private TextField txtParams;
	@FXML
	private TableView<Product> tblProductList;
	@FXML
	private VBox searchBox;
	@FXML
	private VBox addBox;
	@FXML
	private VBox uploadBox;
	
	private ProductDao proDao;
	
	@FXML
	private void initialize() {
		proDao = ProductDao.getInstance();
		
		UIUtil.setTooltip(searchBox, "Search products");
		UIUtil.setTooltip(addBox, "Add product");
		UIUtil.setTooltip(uploadBox, "Upload products from file");
		
		tblProductList.setPlaceholder(new Label("There is no product in the table."));
		
		search();
		createUpdateCol();
	}
	
	private void createUpdateCol() {
		
		TableColumn<Product, Void> updateCol = new TableColumn<>();
		updateCol.setMinWidth(50);
    	updateCol.setPrefWidth(50);
    	updateCol.setMaxWidth(500);
		
    	updateCol.setCellFactory(this);
		
		tblProductList.getColumns().add(updateCol);
		
	}
	
	@Override
	public void edit() {
		Product p = tblProductList.getSelectionModel().getSelectedItem();
		UIUtil.show(ProductEdit.class, p, this::save);
		search();
	}
	
	@Override
	public void delete() {
		Product p = tblProductList.getSelectionModel().getSelectedItem();
		proDao.delete(p);
		search();
	}
	
	public void search() {
		tblProductList.getItems().clear();
		List<Product> list = proDao.find(txtParams.getText(), txtParams.getText(), TextFieldUtil.getPriceValue(txtParams));
		tblProductList.getItems().addAll(list);
	}
	
	public void add() {
		UIUtil.show(ProductEdit.class, null, this::save);
	}
	
	private void save(Product p) {
		proDao.save(p);
		search();
	}
	
	public void upload() {
		try {
    		FileChooser fc = new FileChooser();
        	fc.setTitle("Upload from file");
        	fc.setInitialDirectory(new File(System.getProperty("user.home"), "Documents"));
        	fc.setSelectedExtensionFilter(new ExtensionFilter("txt, csv, tsv", "*.txt", "*.csv", "*.tsv"));
        	File file = fc.showOpenDialog(txtParams.getScene().getWindow());
        	proDao.upload(file);
		} catch(IOException e) {
			MessageBox.show(e.getMessage(), true);
		} catch(Exception e) {
			MessageBox.show("No file selected!", false);
		}
    	search();
	}

}