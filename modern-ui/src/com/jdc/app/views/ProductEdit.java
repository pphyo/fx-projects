package com.jdc.app.views;

import java.util.function.Consumer;

import com.jdc.app.PosException;
import com.jdc.app.dao.CategoryDao;
import com.jdc.app.entity.Category;
import com.jdc.app.entity.Product;
import com.jdc.app.util.StringUtil;
import com.jdc.app.util.ui.MessageBox;
import com.jdc.app.util.ui.UIUtil.ModalController;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class ProductEdit implements ModalController<Product> {
	
	@FXML
	private Label lblTitle;
	@FXML
	private ComboBox<Category> cbxCategory;
	@FXML
	private TextField txtProduct;
	@FXML
	private TextField txtPrice;
	@FXML
	private TextArea txtDescription;
	@FXML
	private CheckBox chkStock;
	@FXML
	private GridPane grdNode;
	
	private Product data;
	private Consumer<Product> listenter;
	private CategoryDao catDao;
	
	@FXML
	private void initialize() {
		catDao = CategoryDao.getInstance();
		cbxCategory.getItems().addAll(catDao.getAll());
		
		chkStock.setSelected(true);
		stockText();
	}
	
	private void stockText() {
		chkStock.selectedProperty().addListener((a, b, c) -> {
			if(chkStock.isSelected())
				chkStock.setText("In stock");
			else
				chkStock.setText("Out of stock");
		});
	}
	
	public void save() {
		try {
			
			if(null == cbxCategory.getValue())
				throw new PosException("Select one category!");
			data.setCategory(cbxCategory.getValue());
			
			if(StringUtil.isEmpty(txtProduct.getText()))
				throw new PosException("Please enter product name!");
			data.setName(txtProduct.getText());
			
			if(StringUtil.isEmpty(txtPrice.getText())) {
				throw new PosException("Please enter price!");
			} else {
				if(Integer.parseInt(txtPrice.getText()) <= 0)
					throw new PosException("Please enter correct price!");
			}
			data.setPrice(Integer.parseInt(txtPrice.getText()));
			
			if(StringUtil.isEmpty(txtDescription.getText()))
				data.setDescription("NA");
			else
				data.setDescription(txtDescription.getText());
			
			if(chkStock.isSelected())
				data.setStock(true);
			else
				data.setStock(false);
			
			listenter.accept(data);
			
			close();
			
			
		} catch (NumberFormatException e) {
			MessageBox.showErrorBox(e, "Error Occured");
		} catch (Exception e) {
			MessageBox.showErrorBox(e, "Error Occured");
		}
	}
	
	public void close() {
		lblTitle.getScene().getWindow().hide();
	}
			
	@Override
	public void init(Product data, Consumer<Product> listener) {
		
		this.data = data;
		this.listenter = listener;
		
		if(null == data) {
			lblTitle.setText("Add Product");
			this.data = new Product();
		} else {
			lblTitle.setText("Edit Product");
			cbxCategory.setValue(data.getCategory());;
			txtProduct.setText(data.getName());;
			txtPrice.setText(String.valueOf(data.getPrice()));;
			txtDescription.setText(data.getDescription());
			chkStock.setSelected(data.isStock() ? true : false);
		}
		
	}

}