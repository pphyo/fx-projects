package com.jdc.app.views;

import java.util.List;

import com.jdc.app.dao.ProductDao;
import com.jdc.app.entity.Product;
import com.jdc.app.util.ui.PosProductBox;
import com.jdc.app.util.ui.TextFieldUtil;
import com.jdc.app.util.ui.UIUtil;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

public class SaleManagement {

    @FXML
    private TextField txtProduct;
    @FXML
    private TilePane tileProboxHolder;
    @FXML
    private VBox searchBox;
    @FXML
	private TextField txtcustName;
	@FXML
	private TextField txtcustPhone;
	@FXML
	private TextField txtcustAddress;
	@FXML
	private TextField txtTax;
	@FXML
	private Label lblHeaderTotal;
	@FXML
	private TableView<?> tblOrderList;
	@FXML
	private TableColumn<?, ?> delCol;
	@FXML
	private TableColumn<?, ?> nameAndPriceCol;
	@FXML
	private TableColumn<?, ?> qtyCol;
	@FXML
	private TableColumn<?, ?> totalCol;
	@FXML
	private Label lblTax;
	@FXML
	private Label lblDiscount;
	@FXML
	private Label lblSubTotal;
	@FXML
	private Label lblTotal;
    
    private ProductDao proDao;
    
    @FXML
    private void initialize() {
    	proDao = ProductDao.getInstance();

    	UIUtil.setTooltip(searchBox, "Search products");
    }

    @FXML
    void search(MouseEvent event) {
    	tileProboxHolder.getChildren().clear();
    	List<Product> list = proDao.find(txtProduct.getText(), txtProduct.getText(), TextFieldUtil.getPriceValue(txtProduct));
    	list.stream().map(PosProductBox::new).forEach(tileProboxHolder.getChildren()::add);
    }
    
    @FXML
    void addToCart(MouseEvent event) {
    	
    }
    
    @FXML
    void save() {
		
	}
	
    @FXML
	void payNow() {
		
	}

}
