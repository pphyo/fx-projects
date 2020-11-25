package com.jdc.app.views;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import com.jdc.app.dao.CategoryDao;
import com.jdc.app.entity.Category;
import com.jdc.app.util.StringUtil;
import com.jdc.app.util.ui.TableCellFactory;
import com.jdc.app.util.ui.UIUtil;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class CategoryManagement extends TableCellFactory<Category> {

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
    @FXML
    private TableColumn<Category, String> nameCol;
    
    private CategoryDao catDao;

    @FXML
    private void initialize() {
    	catDao = CategoryDao.getInstance();
    	
    	UIUtil.setTooltip(searchBox, "Search categories");
    	UIUtil.setTooltip(addBox, "Add category");
    	UIUtil.setTooltip(uploadBox, "Upload from file");
    	
    	search();
    	tblCategoryList.setPlaceholder(new Label("There is no category in the table."));
    	createUpdateCol();
    	
    	tblCategoryList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    	
    	nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
    	nameCol.setOnEditCommit(e -> {
    		Category c = e.getRowValue();
    		if(StringUtil.isEmpty(e.getNewValue()))
    			MessageBox.show("Please enter category name!", false);
    		else
    			catDao.update(e.getNewValue(), c.getId());
    		search();
    	});
    	
    }
    
    private void createUpdateCol() {
    	
    	TableColumn<Category, Void> updateCol = new TableColumn<>();
    	updateCol.setMinWidth(50);
    	updateCol.setPrefWidth(50);
    	updateCol.setMaxWidth(500);
        
        updateCol.setCellFactory(this);
        
        tblCategoryList.getColumns().add(updateCol);
    }
    
    @Override
    public void edit() {
    	Category c = tblCategoryList.getSelectionModel().getSelectedItem();
    	UIUtil.show(CategoryEdit.class, c, cat -> {
    		catDao.update(cat.getName(), cat.getId());
    		search();
    	});
    }
    
    @Override
    public void delete() {
    	List<Category> list = tblCategoryList.getSelectionModel().getSelectedItems();
    	list.stream().forEach(cat -> catDao.delete(cat.getId()));
    	search();
    }
    
    @FXML
    void add() {
    	UIUtil.show(CategoryEdit.class, null, this::save);
    }

    private void save(Category c) {
    	catDao.insert(c);
    	search();
    }
    
    @FXML
    void search() {
    	tblCategoryList.getItems().clear();
    	List<Category> list = catDao.findByName(txtName.getText().toLowerCase());
    	tblCategoryList.getItems().addAll(list.stream().sorted().collect(Collectors.toList()));
    }

    @FXML
    void upload() {
    	try {
    		FileChooser fc = new FileChooser();
        	fc.setTitle("Upload from file");
        	fc.setInitialDirectory(new File(System.getProperty("user.home"), "Documents"));
        	fc.setSelectedExtensionFilter(new ExtensionFilter("txt, csv, tsv", "*.txt", "*.csv", "*.tsv"));
        	File file = fc.showOpenDialog(txtName.getScene().getWindow());
        	catDao.upload(file);
		} catch(IOException e) {
			MessageBox.show(e.getMessage(), true);
		} catch(Exception e) {
			MessageBox.show("No file selected!", false);;
		}
    	search();
    }
}
