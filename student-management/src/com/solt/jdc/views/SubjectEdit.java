package com.solt.jdc.views;

import static com.solt.jdc.util.CommonUtil.isEmpty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import com.solt.jdc.entity.Classroom;
import com.solt.jdc.entity.Classroom.Grade;
import com.solt.jdc.entity.Subject;
import com.solt.jdc.service.ClassroomService;
import com.solt.jdc.util.MessageBox;
import com.solt.jdc.util.StudentException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class SubjectEdit {
	
	@FXML
	private ComboBox<Integer> cbxYear;
	@FXML
	private ComboBox<Grade> cbxGrade;
	@FXML
	private TextArea txtSubjects;
	
	private ClassroomService clsService;
	private Consumer<List<Subject>> handler;
	
	public void initialize() {
		clsService = ClassroomService.getInstance();
		
		cbxYear.getItems().addAll(clsService.getAllYears());
		cbxYear.valueProperty().addListener((a, b, c) -> {
			cbxGrade.getItems().clear();
			cbxGrade.getItems().addAll(clsService.getAllGradeByYear(null == cbxYear.getValue() ? 0 : cbxYear.getValue()));
		});
		
	}
	
	public static void show(Consumer<List<Subject>> handler) {
		try {
			
			Stage stage = new Stage(StageStyle.UNDECORATED);
			FXMLLoader loader = new FXMLLoader(SubjectEdit.class.getResource("SubjectEdit.fxml"));
			Scene scene = new Scene(loader.load());
			
			SubjectEdit controller = loader.getController();
			controller.handler = handler;
			
			stage.setScene(scene);
			stage.show();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void save() {
		
		try {
			
			if(null == cbxYear.getValue())
				throw new StudentException("Please select year!");
			
			if(null == cbxGrade.getValue())
				throw new StudentException("Please select grade!");
			
			if(isEmpty(txtSubjects.getText()))
				throw new StudentException("Please enter subjects!");
			
			List<Subject> subs = new ArrayList<>();
			
			String raw = txtSubjects.getText();
			String[] arr = raw.split("\n");
			
			Arrays.stream(arr).map(s -> {
				Subject sub = new Subject();
				sub.setName(s);
				
				Classroom room = new Classroom();
				room.setYear(cbxYear.getValue());
				room.setGrade(cbxGrade.getValue());
				
				sub.setRoom(room);
				
				return sub;
				
			}).forEach(sub -> subs.add(sub));
			
			handler.accept(subs);
			close();
			
		} catch (Exception e) {
			MessageBox.errorBox(e);
		}
		
	}
	
	public void close() {
		cbxYear.getScene().getWindow().hide();
	}

}
