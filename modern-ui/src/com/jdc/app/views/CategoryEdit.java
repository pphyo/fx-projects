package com.jdc.app.views;

import java.util.function.Consumer;

import com.jdc.app.PosException;
import com.jdc.app.entity.Category;
import com.jdc.app.util.StringUtil;
import com.jdc.app.util.ui.MessageBox;
import com.jdc.app.util.ui.UIUtil.ModalController;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class CategoryEdit implements ModalController<Category> {

	@FXML
	private Label lblTitle;
	@FXML
	private TextField txtName;
	@FXML
	private GridPane grdNode;
	
	private Category data;
	private Consumer<Category> listener;
	
	public void save() {
		try {
			if(StringUtil.isEmpty(txtName.getText()))
				throw new PosException("Please enter category name!");
			data.setName(txtName.getText());
			
			listener.accept(data);
			
			close();
			
		} catch (Exception e) {
			MessageBox.showErrorBox(e, "Error Occured");
		}
	}
	
	public void close() {
		lblTitle.getScene().getWindow().hide();
	}

	@Override
	public void init(Category data, Consumer<Category> listener) {
		this.data = data;
		this.listener = listener;
		
		if(null == data) {
			lblTitle.setText("Add Category");
			this.data = new Category();
		} else {
			lblTitle.setText("Edit Category");
			txtName.setText(data.getName());
		}
	}
	
}
