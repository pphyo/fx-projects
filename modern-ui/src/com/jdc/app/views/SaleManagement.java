package com.jdc.app.views;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import com.jdc.app.PosException;
import com.jdc.app.dao.ProductDao;
import com.jdc.app.entity.Product;
import com.jdc.app.entity.SaleDTO;
import com.jdc.app.entity.SaleOrder;
import com.jdc.app.util.CommonUtil;
import com.jdc.app.util.StringUtil;
import com.jdc.app.util.ui.CartTableRow;
import com.jdc.app.util.ui.MessageBox;
import com.jdc.app.util.ui.PosProductBox;
import com.jdc.app.util.ui.TextFieldUtil;
import com.jdc.app.util.ui.UIUtil;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

public class SaleManagement {

    @FXML
    private TextField txtProduct;
    @FXML
    private TilePane tileProBoxHolder;
    @FXML
    private VBox searchBox;
    @FXML
	private TextField txtCustName;
	@FXML
	private TextField txtcustPhone;
	@FXML
	private TextField txtcustAddress;
	@FXML
	private TextField txtTax;
	@FXML
	private VBox newInvoiceBox;
	@FXML
	private Label lblHeaderTotal;
	@FXML
	private Label lblOrderName;
	@FXML
	private Label lblDate;
	@FXML
	private TilePane tblRowContainer;
	@FXML
	private Label lblTax;
	@FXML
	private Label lblDiscount;
//	@FXML
//	private Label lblDiscount;
	@FXML
	private Label lblSubTotal;
	@FXML
	private Label lblTotal;
    
	private SaleDTO dto;
	private List<SaleOrder> orderList;
	private List<CartTableRow<SaleOrder>> rowList;
    private ProductDao proDao;
    
    @FXML
    private void initialize() {
    	rowList = new LinkedList<>();
    	orderList = new ArrayList<>();
    	proDao = ProductDao.getInstance();

    	UIUtil.setTooltip(searchBox, "Search products");
    	UIUtil.setTooltip(newInvoiceBox, "New Invoice");
    	
    	search();
    	
    	txtCustName.textProperty().addListener((a, b, c) -> lblOrderName.setText(txtCustName.getText()));
    	lblDate.setText(CommonUtil.formatCartDate(LocalDate.now()));
    	lblHeaderTotal.textProperty().bind(lblSubTotal.textProperty());
    }

    @FXML
    void search() {
    	tileProBoxHolder.getChildren().clear();
    	List<Product> list = proDao.find(txtProduct.getText(), txtProduct.getText(), TextFieldUtil.getPriceValue(txtProduct));
    	list.stream().map(p -> new PosProductBox(p, this::addToCart)).forEach(tileProBoxHolder.getChildren()::add);
    }
    
    @FXML
    void createNewInvoice() {
    	
    }
    
    @SuppressWarnings("rawtypes")
	private void addToCart(Product p) {
    	
    	try {
    		if(!p.isStock())
        		throw new PosException("Product you selected is out of stock!");
        	
    		SaleOrder order = tblRowContainer.getChildren().stream()
        												   .filter(node -> node instanceof CartTableRow)
        												   .map(node -> (CartTableRow)node)
        												   .map(box -> box.getRowItem())
        												   .filter(o -> o.getProduct().getId() == p.getId())
        												   .findFirst()
        												   .orElse(null);
        	
        	if(null == order) {
        		order = new SaleOrder();
        		order.setProduct(p);
        		order.setQuantity(1);
        		order.setUnitPrice(p.getPrice());
        		order.setTotal(order.getQuantity() * order.getUnitPrice());
        		orderList.add(order);
        		
        	  	CartTableRow<SaleOrder> row = new CartTableRow<SaleOrder>(order, this::plusQuantity, this::minusQuantity, this::deleteRow);
            	rowList.add(row);
            	tblRowContainer.getChildren().add(row);
          
        	} else {
        		MessageBox.showBox("Product you selected is already in cart!", "Alerady have", AlertType.WARNING);        		
        	}
		} catch (Exception e) {
			e.printStackTrace();
			MessageBox.showBox(e.getMessage(), "Out of Stock", AlertType.WARNING);
		}
    	
    	calculate();
    	  												   
    }
    
    private void deleteRow(CartTableRow<SaleOrder> so) {
    	tblRowContainer.getChildren().clear();
    	
    	//find table row box with id
    	CartTableRow<SaleOrder> deleteRow = rowList.stream()
    			.filter(row -> row.getId() == so.getId()).findFirst().orElse(null);
    	
    	//remove from list
    	rowList.remove(deleteRow);
    	
    	//add in container after remove
    	tblRowContainer.getChildren().addAll(rowList);
    	
    	SaleOrder deleteOrder = orderList.stream().filter(o -> o.equals(so.getRowItem())).findFirst().orElse(null);
    	orderList.remove(deleteOrder);
    	
    	calculate();
    	
    }
    
    private void plusQuantity(SaleOrder so) {
    	so.setQuantity(so.getQuantity() + 1);
    	so.setTotal(so.getQuantity() * so.getUnitPrice());
    	calculate();
    }
    
    private void minusQuantity(SaleOrder so) {
    	if(so.getQuantity() > 1) {
    		so.setQuantity(so.getQuantity() - 1);
    		so.setTotal(so.getQuantity() * so.getUnitPrice());
    	}
    	calculate();
    }
    
    private void calculate() {
    	int subTotal = orderList.stream().mapToInt(so -> so.getTotal()).sum();
    	int tax = Integer.parseInt(lblTax.getText());
    	int discount = Integer.parseInt(lblDiscount.getText());
    	int total = 0;
    	
    	if(discount > 0)
    		total = (subTotal / discount) + tax;
    	else
    		total = subTotal + tax;
    	
    	lblSubTotal.setText(String.valueOf(subTotal));
    	lblTotal.setText(String.valueOf(total));
    }
    
    @FXML
    void save() {
		
	}
	
    @FXML
	void payNow() {
		
	}

}
