package com.jdc.app.views;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import com.jdc.app.PosException;
import com.jdc.app.dao.ProductDao;
import com.jdc.app.dao.SaleDao;
import com.jdc.app.entity.Customer;
import com.jdc.app.entity.Invoice;
import com.jdc.app.entity.Product;
import com.jdc.app.entity.SaleDTO;
import com.jdc.app.entity.SaleOrder;
import com.jdc.app.util.CommonUtil;
import com.jdc.app.util.StringUtil;
import com.jdc.app.util.Validation;
import com.jdc.app.util.ui.CartTableRow;
import com.jdc.app.util.ui.MessageBox;
import com.jdc.app.util.ui.PosProductBox;
import com.jdc.app.util.ui.TextFieldUtil;
import com.jdc.app.util.ui.UIUtil;

import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
	private TextField txtCustPhone;
	@FXML
	private TextField txtCustAddress;
	@FXML
	private TextField txtTax;
	@FXML
	private ComboBox<Customer> cbxInvoice;
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
	@FXML
	private Label lblSubTotal;
	@FXML
	private Label lblTotal;
    
	private List<SaleOrder> orderList;
	private List<CartTableRow<SaleOrder>> rowList;
    private static Map<Customer, List<CartTableRow<SaleOrder>>> unpaidInvoiceList = new HashMap<>();
    private Customer customer;
	private SaleDTO dto;
	private ProductDao proDao;
	private SaleDao saleDao;
    
    @FXML
    private void initialize() {
    	rowList = new LinkedList<>();
    	orderList = new ArrayList<>();
    	proDao = ProductDao.getInstance();
    	saleDao = SaleDao.getInstance();

    	UIUtil.setTooltip(searchBox, "Search products");
    	
    	search();
    	setOrderName();
    	setInvoiceToBox();
    	
    	lblDate.setText(CommonUtil.formatCartDate(LocalDate.now()));
    	lblHeaderTotal.textProperty().bind(lblSubTotal.textProperty());
    	cbxInvoice.valueProperty().addListener((a, b, c) -> addSelectedInvoiceToCart());
    }
    
	private void setOrderName() {
		txtCustName.textProperty().addListener((a, b, c) -> {
    		if(StringUtil.isEmpty(txtCustName.getText()))
    			lblOrderName.setText("New Order");
    		else
    			lblOrderName.setText(txtCustName.getText());
    	});
	}
	
    @FXML
    void search() {
    	tileProBoxHolder.getChildren().clear();
    	List<Product> list = proDao.find(txtProduct.getText(), txtProduct.getText(), TextFieldUtil.getPriceValue(txtProduct));
    	list.stream().map(p -> new PosProductBox(p, this::addToCart)).forEach(tileProBoxHolder.getChildren()::add);
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
    	
    	//remove sale order object in list
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
    	int discount = StringUtil.isEmpty(lblDiscount.getText()) ? 0 : Integer.parseInt(lblDiscount.getText());
    	
    	int total = discount > 0 ? (subTotal / discount) + tax : subTotal + tax;
    	
    	lblSubTotal.setText(String.valueOf(subTotal));
    	lblTotal.setText(String.valueOf(total));
    }
    
    private void prepareCart() {
    	dto = null;
    	txtCustName.clear();
    	txtCustAddress.clear();
    	txtCustPhone.clear();
    	tblRowContainer.getChildren().clear();
    	orderList.clear();
    	rowList.clear();
    	cbxInvoice.setValue(null);
    	calculate();
    }
    
    @FXML
    void save() {
    	
    	try {
    		if(null == dto) {
    			dto = new SaleDTO();
    			
    			customer = dto.getCustomer();
    		
	    		Validation.validate(txtCustName.getText(), "Please enter customer name!");
	    		
	    		customer.setName(txtCustName.getText());
	    		customer.setAddress(txtCustAddress.getText());
	    		customer.setPhone(txtCustPhone.getText());
    		}
    		
    		if(!customer.getName().equals(txtCustName.getText())) {
    			customer.setName(txtCustName.getText());
    		}
    		if(!customer.getAddress().equals(txtCustAddress.getText())) {
    			customer.setAddress(txtCustAddress.getText());
    		}
    		if(!customer.getPhone().equals(txtCustPhone.getText())) {
    			customer.setPhone(txtCustPhone.getText());
    		}
    		
           	Validation.validate(rowList, "At least one product in the cart!");
           	
           	//create temp row List and save in collection
           	List<CartTableRow<SaleOrder>> temp = new LinkedList<>();
       		temp.addAll(rowList);
       		
       		//find value with
       		if(unpaidInvoiceList.containsKey(customer)) {
       			unpaidInvoiceList.replace(customer, temp);
       		} else {
       			unpaidInvoiceList.put(customer, temp);
       			setInvoiceToBox();
       		}

           	prepareCart();
        	
		} catch (Exception e) {
			MessageBox.showBox(e.getMessage(), "Cart Message", AlertType.INFORMATION);
		}
	}
    
    private void addSelectedInvoiceToCart() {
    	rowList.clear();
    	
    	if(null != cbxInvoice.getValue()) {
    		// set customer in field
    		Customer key = cbxInvoice.getValue();
        	txtCustName.setText(key.getName());
        	txtCustAddress.setText(key.getAddress());
        	txtCustPhone.setText(key.getPhone());
        	
        	// change reference from invoice list
        	customer = key;
        	
        	// value from invoice list (Map Object)
        	List<CartTableRow<SaleOrder>> value = unpaidInvoiceList.get(key);
        	
        	// copy select object to row list
        	rowList.addAll(value);

        	// clear container
        	tblRowContainer.getChildren().clear();
        	// add row in container
        	tblRowContainer.getChildren().addAll(value);
        	
        	// add sale order to order list
        	orderList = value.stream().map(row -> row.getRowItem()).collect(Collectors.toList());
        	calculate();
    	}
    }
    
    private void setInvoiceToBox() {
    	cbxInvoice.setValue(null);
    	cbxInvoice.getItems().clear();
    	cbxInvoice.getItems().addAll(getInvoiceKey());
    }
    
    private List<Customer> getInvoiceKey() {
    	List<Customer> result = new LinkedList<>();
    	for(Entry<Customer, List<CartTableRow<SaleOrder>>> en : unpaidInvoiceList.entrySet()) {
    		result.add(en.getKey());
    	}
    	return result;
    }
	
    @FXML
	void payNow() {
    	try {
    		if(null == dto) {
    			dto = new SaleDTO();
    		}
    		
	    	Validation.validate(txtCustName.getText(), "Please enter customer name!");
    		
           	Validation.validate(rowList, "At least one product in the cart!");
           	       		
           	if(null!= cbxInvoice.getValue()) {
           		customer = cbxInvoice.getValue();
           	} else {
           		customer = dto.getCustomer();
           		customer.setName(txtCustName.getText());
           		customer.setAddress(txtCustAddress.getText());
           		customer.setPhone(txtCustPhone.getText());
           	}
           	
           	Invoice invoice = dto.getInvoice();
           	invoice.setCustomer(customer);
           	invoice.setInvoiceDate(LocalDate.now());
           	invoice.setInvoiceTime(LocalTime.now());
           	invoice.setSubTotal(Integer.parseInt(lblSubTotal.getText()));
           	invoice.setTax(Integer.parseInt(lblTax.getText()));
           	invoice.setDiscount(Integer.parseInt(lblDiscount.getText()));
           	invoice.setTotal(Integer.parseInt(lblTotal.getText()));
           	
           	dto.getOrderList().clear();
           	dto.getOrderList().addAll(orderList);
           	
           	saleDao.save(dto);
           	
           	prepareCart();
        	
		} catch (Exception e) {
			MessageBox.showBox(e.getMessage(), "Cart Message", AlertType.INFORMATION);
		}
	}

}
