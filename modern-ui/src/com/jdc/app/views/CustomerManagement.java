package com.jdc.app.views;

import java.util.List;

import com.jdc.app.dao.SaleDao;
import com.jdc.app.entity.Customer;
import com.jdc.app.util.ui.TableCellFactory;
import com.jdc.app.util.ui.TextFieldUtil;
import com.jdc.app.util.ui.UIUtil;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class CustomerManagement extends TableCellFactory<Customer>{
	
	@FXML
	private TextField txtParams;
	@FXML
	private TableView<Customer> tblCustomerList;
	@FXML
	private VBox searchBox;
	@FXML
	private VBox addBox;
	
	private SaleDao saleDao;
	
	@FXML
	private void initialize() {
		saleDao = SaleDao.getInstance();
		
		search();
		createUpdateCol();
		
		tblCustomerList.setPlaceholder(new Label("There is no customer in the table."));
		UIUtil.setTooltip(searchBox, "Search customers");
		UIUtil.setTooltip(addBox, "Add customer");
		
	}
	
	private void createUpdateCol() {
		
		TableColumn<Customer, Void> updateCol = new TableColumn<>();
		updateCol.setMinWidth(50);
    	updateCol.setPrefWidth(50);
    	updateCol.setMaxWidth(500);
		
    	updateCol.setCellFactory(this);
		
		tblCustomerList.getColumns().add(updateCol);
		
	}

	public void search() {
		tblCustomerList.getItems().clear();
		List<Customer> list = saleDao.findCustomer(txtParams.getText(), txtParams.getText(), TextFieldUtil.getValue(txtParams));
		tblCustomerList.getItems().addAll(list);
	}
	
	public void add() {
		UIUtil.show(CustomerEdit.class, null, this::save);
	}
	
	private void save(Customer c) {
		saleDao.saveCustomer(c);
		search();
	}
	
	@Override
	public void edit() {
		Customer c = tblCustomerList.getSelectionModel().getSelectedItem();
		UIUtil.show(CustomerEdit.class, c, this::save);
		search();
	}
	
	@Override
	public void delete() {
		Customer c = tblCustomerList.getSelectionModel().getSelectedItem();
		saleDao.deleteCustomer(c);
		search();
	}
}