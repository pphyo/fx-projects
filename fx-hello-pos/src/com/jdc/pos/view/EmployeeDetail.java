package com.jdc.pos.view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.LocalDate;

import com.jdc.pos.entity.Employee;
import com.jdc.pos.util.PromptBox;
import com.jdc.pos.util.Security;
import com.jdc.pos.util.StringUtil;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class EmployeeDetail {
	
	@FXML
	private Label name;
	@FXML
	private Label year;
	@FXML
	private Label monthAndDay;
	@FXML
	private Label nextBirthDayOfWeek;
	@FXML
	private Label nextMonthAndDay;
	@FXML
	private ImageView image;
	@FXML
	private Label userName;
	@FXML
	private Label email;
	@FXML
	private Label role;
	@FXML
	private Label phoneNo;
	@FXML
	private Label address;
	@FXML
	private VBox node;
	
	@FXML
	private void initialize() {
		node.setOnKeyPressed(e -> {
			if(e.getCode() == KeyCode.ENTER || e.getCode() == KeyCode.ESCAPE)
				close();
		});
	}
	
	public static void show(Employee emp) {
		try {
			Stage stage = new Stage(StageStyle.UNDECORATED);
			FXMLLoader loader = new FXMLLoader(EmployeeDetail.class.getResource("EmployeeDetail.fxml"));
			Parent root = loader.load();
			EmployeeDetail controller = loader.getController();
			if(null != emp) {
				controller.setDataToView(emp);
			}
			stage.setTitle("Employee Detail");
			stage.getIcons().add(new Image(new FileInputStream("res/image/detail.png")));
			stage.setScene(new Scene(root));
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.show();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void setDataToView(Employee emp) {
		try {
			if(StringUtil.check(emp.getImage()))
				image.setImage(new Image(new FileInputStream(emp.getImage())));
			
			if(Security.getMember().getUserName().equals(emp.getUserName()))
				name.setText(emp.getUserName().concat(" (You)"));
			else
				name.setText(emp.getUserName());
			userName.setText(emp.getUserName());
			email.setText(emp.getEmail());
			role.setText(emp.getRole().toString());
			phoneNo.setText(emp.getPhoneNo());
			address.setText(emp.getAddress());
			
			LocalDate now = LocalDate.now();
			LocalDate birthDay = emp.getDob();
			int curYear = now.getMonthValue() < birthDay.getMonthValue() ? now.compareTo(birthDay) - 1 : now.compareTo(birthDay);
			int month = now.getMonthValue() > birthDay.getMonthValue() ? now.getMonthValue() - birthDay.getMonthValue() : 12 - (birthDay.getMonthValue() - now.getMonthValue()) == 12 ? 0 : 12 - (birthDay.getMonthValue() - now.getMonthValue());
			int day = now.getDayOfMonth() > birthDay.getDayOfMonth() ? now.getDayOfMonth() - birthDay.getDayOfMonth() : now.lengthOfMonth() - (birthDay.getDayOfMonth() - now.getDayOfMonth());
	
			year.setText(String.valueOf(curYear));
			monthAndDay.setText(String.valueOf(month + " " + getMonthText(month) + " | ").concat(String.valueOf(day + " " + getDayText(day))));
			
			LocalDate nextBirthDay = LocalDate.of(now.getYear(), birthDay.getMonth(), birthDay.getDayOfMonth()).plusYears(1);
			
			nextBirthDayOfWeek.setText(nextBirthDay.getDayOfWeek().toString());
			nextMonthAndDay.setText(String.format("%s %d", birthDay.getMonth().toString(), birthDay.getDayOfMonth()));
			
			
		} catch (FileNotFoundException e) {
			try {
				PromptBox.showPrompt("Profile image not found!\nPlease update image location.", "Error", new Image(new FileInputStream("res/image/cc.png")));
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public String getMonthText(int month) {
		return month > 1 ? "months" : "month";
	}
	
	public String getDayText(int day) {
		return day > 1 ? "days" : "day";
	}
	
	public void close() {
		name.getScene().getWindow().hide();
	}

}
