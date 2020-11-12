package com.jdc.pos.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import com.jdc.pos.entity.Employee;
import com.jdc.pos.entity.Role;
import com.jdc.pos.service.EmployeeService;
import com.jdc.pos.util.PromptBox;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class EmployeeList {
	
	@FXML
	private TextField userName;
	@FXML
	private ComboBox<Role> role;
	@FXML
	private TextField email;
	@FXML
	private TextField address;
	@FXML
	private TableView<Employee> empList;
	
	private EmployeeService empService;
	
	@FXML
	private void initialize() {
		empService = EmployeeService.getInstance();
		role.getItems().addAll(Role.values());
		search();
		
		createMenu();
		userName.textProperty().addListener(a -> search());
		role.valueProperty().addListener(a -> search());
		email.textProperty().addListener(a -> search());
		address.textProperty().addListener(a -> search());
	}
	
	public void add() {
		EmployeeEdit.show(null, this::save);
	}
	
	private void save(Employee emp) {
		empService.create(emp);
		search();
	}
	
	public void search() {
		empList.getItems().clear();
		List<Employee> list = empService.find(userName.getText(), role.getValue(), email.getText(), address.getText());
		empList.getItems().addAll(list);
	}
	
	public void clear() {
		userName.clear();
		role.setValue(null);
		email.clear();
		address.clear();
	}
	
	private void createMenu() {
		MenuItem edit = new MenuItem("Edit");
		edit.setOnAction(e -> edit());
		
		MenuItem delete = new MenuItem("Delete");
		delete.setOnAction(e -> delete(e));
		
		MenuItem image = new MenuItem("Profile Image Upload");
		image.setOnAction(e -> upload());
		
		MenuItem detail = new MenuItem("Show Employee Info");
		detail.setOnAction(e -> showDetail());
		
		MenuItem paySalary = new MenuItem("Pay Salary");
		paySalary.setOnAction(e -> paySalary(e));
		
		ContextMenu menu = new ContextMenu(edit, delete, image, detail, paySalary);
		empList.setContextMenu(menu);
	}
	
	private void edit() {
		Employee p = empList.getSelectionModel().getSelectedItem();
		EmployeeEdit.show(p, this::save);
		search();
	}
	
	private void delete(ActionEvent event) {
		Alert alert = new Alert(AlertType.WARNING);
		try {
			PromptBox.showPrompt(alert, "Delete Product", "Are you sure want to delete this!", new Image(new FileInputStream("resources/image/rm-user.png")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		alert.showAndWait().ifPresent(e -> {
			if(e == ButtonType.CANCEL) {
				event.consume();
			} else {
				Employee p = empList.getSelectionModel().getSelectedItem();
				empService.delete(p.getId());
				search();
			}
		});
	}
	
	private void upload() {
		FileChooser fc = new FileChooser();
		fc.setTitle("Profile image");
		fc.setSelectedExtensionFilter(new ExtensionFilter("png, jpg, jpeg", "*.png", "*.jpg", "*.jpeg"));
		fc.setInitialDirectory(new File(System.getProperty("user.home"), "Pictures"));
		File file = fc.showOpenDialog(userName.getScene().getWindow());
		Employee emp = empList.getSelectionModel().getSelectedItem();
		empService.upload(file.getAbsolutePath(), emp.getId());
		search();
	}
	
	private void showDetail() {
		Employee emp = empList.getSelectionModel().getSelectedItem();
		EmployeeDetail.show(emp);
	}

	private void paySalary(ActionEvent event) {
		
		Employee emp = empList.getSelectionModel().getSelectedItem();
		
		LocalDate now = LocalDate.now();
		Month curPayMonth = now.getMonth();
		Month nextPayMonth = curPayMonth;
		
		try {
			if(emp.isPaid()) {					
				if(curPayMonth == nextPayMonth)
					PromptBox.showPrompt("Already paid for this employee for this month!", "Already Paid", new Image(new FileInputStream("res/image/payment.png")));
			} else {
				PromptBox.showPrompt(String.format("Please pay the salary to employee!%nSalary is %d", emp.getSalary()), "Pay Salary", new Image(new FileInputStream("res/image/salary.png")));
				emp.setPaid(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		empService.paySalary(emp.isPaid(), emp.getId());
	}
	
}