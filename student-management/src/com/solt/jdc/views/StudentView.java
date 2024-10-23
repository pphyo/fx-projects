package com.solt.jdc.views;

import static com.solt.jdc.util.CommonUtil.isEmpty;

import java.io.File;
import java.util.List;

import com.solt.jdc.entity.Classroom.Grade;
import com.solt.jdc.entity.Student;
import com.solt.jdc.entity.Student.Gender;
import com.solt.jdc.service.ClassroomService;
import com.solt.jdc.service.StudentService;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
public class StudentView {
	
	@FXML
	private ComboBox<Grade> cbxGrade;
	@FXML
	private ComboBox<Integer> cbxYear;
	@FXML
	private TextField txtId;
	@FXML
	private TextField txtName;
	@FXML
	private ComboBox<Gender> cbxGender;
	@FXML
	private TableView<Student> tblStudent;
	
	private ClassroomService clsService;
	private StudentService stuService;
	
	public void initialize() {
		clsService = ClassroomService.getInstance();
		stuService = StudentService.getInstance();
		
		cbxYear.getItems().addAll(clsService.getAllYears());
		cbxYear.valueProperty().addListener((a, b, c) -> {
			cbxGrade.getItems().clear();
			cbxGrade.getItems().addAll(clsService.getAllGradeByYear(null == cbxYear.getValue() ? 0 : cbxYear.getValue()));
		});
		
		search();
		
		createContextMenu();
		tblStudent.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
	}
	
	public void createContextMenu() {
		MenuItem edit = new MenuItem("EDIT");
		edit.setOnAction(e -> edit());
		
		MenuItem delete = new MenuItem("DELETE");
		delete.setOnAction(e -> delete());
		
		ContextMenu menu = new ContextMenu(edit, delete);
		
		tblStudent.setContextMenu(menu);
	}
	
	private void edit() {
		Student stu = tblStudent.getSelectionModel().getSelectedItem();
		StudentEdit.show(stu, this::save);
	}
	
	private void delete() {
		List<Student> list = tblStudent.getSelectionModel().getSelectedItems();
		list.stream().forEach(s -> stuService.delete(s.getId()));
		search();
	}
	
	public void upload() {
		
		try {
			FileChooser fc = new FileChooser();
			fc.setTitle("Choose file");
			fc.setInitialDirectory(new File(System.getProperty("user.home"), "Desktop"));
			fc.setSelectedExtensionFilter(new ExtensionFilter("Text File", ".txt"));
			File file = fc.showOpenDialog(cbxGrade.getScene().getWindow());
			stuService.upload(file);
			
			search();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void add() {
		StudentEdit.show(null, this::save);
	}
	
	private void save(Student s) {
		stuService.save(s);
		search();		
	}
	
	public void search() {
		tblStudent.getItems().clear();
		int year = null == cbxYear.getValue() ? 0 : cbxYear.getValue();
		int id = isEmpty(txtId.getText()) ? 0 : Integer.parseInt(txtId.getText());
		List<Student> list = stuService.search(year, cbxGrade.getValue(), id, txtName.getText(), cbxGender.getValue());
		tblStudent.getItems().addAll(list);
	}
	
	public void reload() {
		cbxGender.setValue(null);
		cbxYear.setValue(null);
		txtId.clear();
		txtName.clear();
		cbxGender.setValue(null);
		search();
	}

}
