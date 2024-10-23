package com.solt.jdc.views;

import static com.solt.jdc.util.CommonUtil.isEmpty;

import java.util.function.Consumer;

import com.solt.jdc.entity.Classroom;
import com.solt.jdc.entity.Classroom.Grade;
import com.solt.jdc.entity.Student;
import com.solt.jdc.entity.Student.Gender;
import com.solt.jdc.service.ClassroomService;
import com.solt.jdc.util.StudentException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class StudentEdit {
	
	@FXML
	private Label lblTitle;
	@FXML
	private ComboBox<Integer> cbxYear;
	@FXML
	private ComboBox<Grade> cbxGrade;
	@FXML
	private TextField txtName;
	@FXML
	private ComboBox<Gender> cbxGender;
	@FXML
	private DatePicker dpDob;
	@FXML
	private TextField txtPhone;
	
	private Consumer<Student> handler;
	private Student stu;
	private ClassroomService clsService;
	
	public void initialize() {
		clsService = ClassroomService.getInstance();
		
		cbxYear.getItems().addAll(clsService.getAllYears());
		cbxYear.valueProperty().addListener((a, b, c) -> {
			cbxGrade.getItems().clear();
			cbxGrade.getItems().addAll(clsService.getAllGradeByYear(null == cbxYear.getValue() ? 0 : cbxYear.getValue()));
		});
		cbxGender.getItems().addAll(Gender.values());
		
	}
	
	public static void show(Student stu, Consumer<Student> handler) {
		
		try {
			Stage stage = new Stage(StageStyle.UNDECORATED);
			FXMLLoader loader = new FXMLLoader(StudentEdit.class.getResource("StudentEdit.fxml"));
			Parent root = loader.load();
			stage.setScene(new Scene(root));
			stage.initModality(Modality.APPLICATION_MODAL);
			
			StudentEdit controller = loader.getController();
			controller.stu = stu;
			controller.handler = handler;
			
			if(null != stu) {
				controller.lblTitle.setText("Edit Student");
				controller.setDataToView(stu);
			} else {
				controller.lblTitle.setText("Add New Student");
			}
			
			stage.show();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private void setDataToView(Student stu) {
		cbxYear.setValue(stu.getClassroom().getYear());
		cbxGrade.setValue(stu.getClassroom().getGrade());
		txtName.setText(stu.getName());
		cbxGender.setValue(stu.getGender());
		dpDob.setValue(stu.getDob());
		txtPhone.setText(stu.getPhone());
	}

	public void save() {
		
		try {
			
			if(null == stu) {
				stu = new Student();
			}
			
			if(null == cbxYear.getValue())
				throw new StudentException("Please select academic year!");
			
			if(null == cbxGrade.getValue())
				throw new StudentException("Please select grade!");
			
			if(isEmpty(txtName.getText()))
				throw new StudentException("Please enter student name!");
			
			if(null == cbxGender.getValue())
				throw new StudentException("Please select gender!");
			
			if(null == dpDob.getValue())
				throw new StudentException("Please select date of birth!");
			
			stu.setName(txtName.getText());
			stu.setGender(cbxGender.getValue());
			stu.setDob(dpDob.getValue());
			stu.setPhone(txtPhone.getText());
			
			Classroom room = new Classroom();
			room.setYear(cbxYear.getValue());
			room.setGrade(cbxGrade.getValue());
			
			stu.setClassroom(room);
			
			handler.accept(stu);
			
			close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void close() {
		lblTitle.getScene().getWindow().hide();
	}

}
