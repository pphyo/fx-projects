package com.jdc.app.views;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.jdc.app.dao.CategoryDao;
import com.jdc.app.entity.Category;
import com.jdc.app.util.MessageBox;
import com.jdc.app.util.UIUtil;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class CategoryManagement {

    @FXML
    private TextField txtName;
    @FXML
    private VBox searchBox;
    @FXML
    private VBox addBox;
    @FXML
    private VBox uploadBox;
    @FXML
    private TableView<Category> tblCategoryList;
    
    private CategoryDao catDao;

    @FXML
    private void initialize() {
    	catDao = CategoryDao.getInstance();
    	
    	UIUtil.setTooltip(searchBox, "Search categories");
    	UIUtil.setTooltip(addBox, "Add category");
    	UIUtil.setTooltip(uploadBox, "Upload from file");
    	
    	search(null);
    }
    
    @FXML
    void add(MouseEvent event) {
    	
    }

    @FXML
    void search(MouseEvent event) {
    	tblCategoryList.getItems().clear();
    	List<Category> list = catDao.getAll();
    	tblCategoryList.getItems().addAll(list);
    }

    @FXML
    void upload(MouseEvent event) {
    	try {
    		FileChooser fc = new FileChooser();
        	fc.setTitle("Upload from file");
        	fc.setInitialDirectory(new File(System.getProperty("user.home"), "Documents"));
        	fc.setSelectedExtensionFilter(new ExtensionFilter("txt, csv, tsv", "*.txt", "*.csv", "*.tsv"));
        	File file = fc.showOpenDialog(txtName.getScene().getWindow());
        	catDao.upload(file);
		} catch(IOException e) {
			MessageBox.showErrorBox(e, "File Error", AlertType.ERROR);
		} catch(Exception e) {
			e.printStackTrace();
			MessageBox.showBox("No file selected!", "Nothing Select", AlertType.WARNING);
		}
    	search(event);
    }

}
