package com.jdc.app.views;

import java.util.List;

import com.jdc.app.dao.EmployeeDao;
import com.jdc.app.entity.Employee;
import com.jdc.app.util.ui.TableCellFactory;
import com.jdc.app.util.ui.TextFieldUtil;
import com.jdc.app.util.ui.UIUtil;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class EmployeeManagement extends TableCellFactory<Employee> {
	
	@FXML
	private TextField txtParams;
	@FXML
	private TableView<Employee> tblEmployeeList;
	@FXML
	private VBox searchBox;
	@FXML
	private VBox addBox;
	
	private EmployeeDao empDao;
	
	@FXML
	private void initialize() {
		empDao = EmployeeDao.getInstance();
		
		UIUtil.setTooltip(searchBox, "Search employees");
		UIUtil.setTooltip(addBox, "Add employee");
		
		tblEmployeeList.setPlaceholder(new Label("There is no employee in the table."));
		
		search();
		createUpdateCol();
	}
	
	private void createUpdateCol() {
		
		TableColumn<Employee, Void> updateCol = new TableColumn<>();
		updateCol.setMinWidth(50);
    	updateCol.setPrefWidth(50);
    	updateCol.setMaxWidth(500);
		
    	updateCol.setCellFactory(this);
		
		tblEmployeeList.getColumns().add(updateCol);
		
	}
	
	@Override
	public void edit() {
		Employee emp = tblEmployeeList.getSelectionModel().getSelectedItem();
		UIUtil.show(EmployeeEdit.class, emp, this::save);
		search();
	}
	
	@Override
	public void delete() {
		Employee emp = tblEmployeeList.getSelectionModel().getSelectedItem();
		empDao.delete(emp);
		search();
	}
	
	public void search() {
		tblEmployeeList.getItems().clear();
		List<Employee> list = empDao.find(txtParams.getText(), TextFieldUtil.getValue(txtParams), txtParams.getText());
		tblEmployeeList.getItems().addAll(list);
	}
	
	public void add() {
		UIUtil.show(EmployeeEdit.class, null, this::save);
	}
	
	private void save(Employee emp) {
		empDao.save(emp);
		search();
	}

}
