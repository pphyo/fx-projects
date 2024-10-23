package com.solt.jdc.views;

import static com.solt.jdc.util.CommonUtil.isEmpty;

import java.util.function.Consumer;

import com.solt.jdc.entity.Classroom;
import com.solt.jdc.entity.Classroom.Grade;
import com.solt.jdc.util.MessageBox;
import com.solt.jdc.util.StudentException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ClassroomEdit {
	
	@FXML
	private ComboBox<Grade> cbxGrade;
	@FXML
	private TextField txtYear;
	@FXML
	private GridPane grid;
	
	private Consumer<Classroom> handler;
	
	public void initialize() {
		cbxGrade.getItems().addAll(Grade.values());
		
		grid.setOnKeyPressed(e -> {
			if(e.getCode() == KeyCode.ENTER)
				save();
			
			if(e.getCode() == KeyCode.ESCAPE)
				close();
		});
		
	}
	
	public static void show(Consumer<Classroom> handler) {
		try {
			Stage stage = new Stage(StageStyle.UNDECORATED);
			FXMLLoader loader = new FXMLLoader(ClassroomEdit.class.getResource("ClassroomEdit.fxml"));
			Parent root = loader.load();
			
			ClassroomEdit controller = loader.getController();
			controller.handler = handler;
			
			Scene scene = new Scene(root);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setScene(scene);
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void save() {
		try {
			
			if(null == cbxGrade.getValue()) {
				throw new StudentException("Grade can't be null!");
			}
			
			if(isEmpty(txtYear.getText())) {
				throw new StudentException("Year can't be null!");
			}
			
			Classroom room = new Classroom();
			room.setGrade(cbxGrade.getValue());
			room.setYear(Integer.parseInt(txtYear.getText()));
			
			handler.accept(room);
			
			close();
			
		} catch (NumberFormatException e) {
			MessageBox.errorBox(e);
		} catch (Exception e) {
			MessageBox.errorBox(e);
		}
	}
	
	public void close() {
		cbxGrade.getScene().getWindow().hide();
	}

}
