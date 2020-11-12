package com.jdc.pos.view;

import java.io.FileInputStream;
import java.time.LocalDate;
import java.util.function.Consumer;

import com.jdc.pos.entity.Employee;
import com.jdc.pos.entity.Role;
import com.jdc.pos.util.DecimalFormatConverter;
import com.jdc.pos.util.PosException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class EmployeeEdit {

	@FXML
	private Label title;
	@FXML
	private Label info;
	@FXML
	private TextField userName;
	@FXML
	private TextField email;
	@FXML
	private PasswordField password;
	@FXML
	private ComboBox<Role> role;
	@FXML
	private TextField salary;
	@FXML
	private DatePicker dob;
	@FXML
	private TextField phoneNo;
	@FXML
	private TextField address;
	@FXML
	private CheckBox paid;
	@FXML
	private GridPane grid;
	
	private static final int basicSalary = 120000;
	private Employee emp;
	private Consumer<Employee> listener;
	
	@FXML
	private void initialize() {
		role.getItems().addAll(Role.values());
		dob.setValue(LocalDate.now());
		salary.setText(DecimalFormatConverter.format(basicSalary));
		grid.setOnKeyPressed(e -> {
			if(e.getCode() == KeyCode.ENTER)
				save();
			if(e.getCode() == KeyCode.ESCAPE)
				close();
		});
	}
	
	public static void show(Employee emp, Consumer<Employee> listener) {
		try {
			Stage stage = new Stage();
			FXMLLoader loader = new FXMLLoader(EmployeeEdit.class.getResource("EmployeeEdit.fxml"));
			
			Parent root = loader.load();
			EmployeeEdit controller = loader.getController();			
			controller.emp = emp;
			controller.listener = listener;
			
			boolean isNew = emp == null;
			
			controller.title.setText(isNew ? "Add New Employee" : "Edit Employee");
			stage.setTitle(isNew ? "Add New Employee" : "Update Employee");
			
			Image addImg = new Image(new FileInputStream("res/image/add-user.png"));
			Image editImg = new Image(new FileInputStream("res/image/edit-user.png"));
			
			stage.getIcons().add(isNew ? addImg : editImg);
			
			if(!isNew)
				controller.setDataToView(emp);
			
			stage.setScene(new Scene(root));
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.show();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void save() {
		try {
			
			if(null == emp)
				emp = new Employee();
			
			if(userName.getText().isEmpty())
				throw new PosException("Please enter user name!");
			emp.setUserName(userName.getText());
			
			if(email.getText().isEmpty())
				throw new PosException("Please enter email!");
			emp.setEmail(email.getText());
			
			if(password.getText().isEmpty())
				throw new PosException("Please enter password!");
			else {
				if(password.getText().length() < 8)
					throw new PosException("Password length >= 8!");
			}
			emp.setPassword(password.getText());
			
			if(null == role.getValue())
				throw new PosException("Please select role!");
			emp.setRole(role.getValue());
			
			int slry = salary.getText().isEmpty() || Integer.parseInt(salary.getText().replace(",", "")) < basicSalary ? basicSalary : Integer.parseInt(salary.getText().replace(",", ""));
			
			if(salary.getText().isEmpty()) {
				throw new PosException("Salary can't be empty!");
			}
			emp.setSalary(slry);
			
			if(dob.getValue().isAfter(LocalDate.now()))
				throw new PosException("Please select correct birthday!");
			emp.setDob(dob.getValue());
			
			if(phoneNo.getText().isEmpty()) {
				throw new PosException("Please enter phone number!");
			} else {
				if(phoneNo.getText().length() < 9)
					throw new PosException("Please enter correct number!");
			}
			emp.setPhoneNo(phoneNo.getText());
			
			if(address.getText().isEmpty())
				throw new PosException("Please enter address!");
			emp.setAddress(address.getText());
			
			if(paid.isSelected())
				emp.setPaid(true);
			else
				emp.setPaid(false);
			
			listener.accept(emp);
			close();
			
		} catch (Exception e) {
			info.setText(e.getMessage());
		}
	}
	
	public void close() {
		title.getScene().getWindow().hide();
	}
	
	private void setDataToView(Employee emp) {
		userName.setText(emp.getUserName());
		email.setText(emp.getEmail());
		password.setText(emp.getPassword());
		role.setValue(emp.getRole());
		salary.setText(DecimalFormatConverter.format(emp.getSalary()));
		dob.setValue(emp.getDob());
		phoneNo.setText(emp.getPhoneNo());
		address.setText(emp.getAddress());
		if(emp.isPaid())
			paid.setSelected(true);
	}
	
}
