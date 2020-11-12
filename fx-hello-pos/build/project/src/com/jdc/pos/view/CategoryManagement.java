package com.jdc.pos.view;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import com.jdc.pos.entity.Category;
import com.jdc.pos.entity.Role;
import com.jdc.pos.service.CategoryService;
import com.jdc.pos.util.PromptBox;
import com.jdc.pos.util.Security;
import com.jdc.pos.util.StringUtil;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.TilePane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class CategoryManagement {
	
	@FXML
	private TextField name;
	@FXML
	private TilePane boxHolder;
	
	private CategoryService catService;
	
	@FXML
	private void initialize() {
		catService = CategoryService.getInstance();
		search();
		
		name.setOnKeyPressed(e -> {
			if(e.getCode() == KeyCode.ENTER)
				add();
		});
		
		name.textProperty().addListener(e -> search());
		
	}
	
	public void add() {
		try {
			if(Security.getMember().getRole() == Role.Admin) {
				Category c = new Category();
				if(StringUtil.check(name.getText())) {
					c.setName(name.getText());
					catService.create(c);
				} else {
					PromptBox.showPrompt("Please enter category name!", "Empty", new Image(new FileInputStream("res/image/add-text.png")));
				}
				search();
				clear();
			} else {
				PromptBox.showPrompt("Only admin create category!", "Can't create", new Image(new FileInputStream("res/image/cc.png")));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void search() {
		boxHolder.getChildren().clear();
		List<Category> categories = catService.findByName(name.getText());
		categories.stream().map(CategoryBox::new).forEach(boxHolder.getChildren()::addAll);
	}
	
	public void clear() {
		name.clear();
	}
	
	public void upload() {
		try {
			FileChooser fc = new FileChooser();
			fc.setTitle("Category Upload");
			fc.setSelectedExtensionFilter(new ExtensionFilter("csv, tsv, txt", "*.csv", ".tsv", ".txt"));
			fc.setInitialDirectory(new File(System.getProperty("user.home"), "Desktop"));
			File file = fc.showOpenDialog(name.getScene().getWindow());
			catService.upload(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		search();
	}
	
}