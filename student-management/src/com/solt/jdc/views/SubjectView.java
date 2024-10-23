package com.solt.jdc.views;

import java.util.List;

import com.solt.jdc.entity.Classroom.Grade;
import com.solt.jdc.service.ClassroomService;
import com.solt.jdc.service.SubjectService;
import com.solt.jdc.util.StudentException;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;

public class SubjectView {
	
	@FXML
	private ListView<Integer> lvYear;
	@FXML
	private ListView<Grade> lvGrade;
	@FXML
	private TilePane tpBoxHolder;
	
	private SubjectService subService;
	private ClassroomService clsService;
	
	public void initialize() {
		clsService = ClassroomService.getInstance();
		subService = SubjectService.getInstance();
		
		
		lvYear.getItems().addAll(clsService.getAllYears());
		lvYear.getSelectionModel().selectedItemProperty().addListener((a, b, c) -> {
			lvGrade.getItems().clear();
			int year = lvYear.getSelectionModel().getSelectedItem();
			List<Grade> grades = clsService.getAllGradeByYear(year);
			lvGrade.getItems().addAll(grades);
		});
		
		lvYear.getSelectionModel().selectedItemProperty().addListener((a, b, c) -> {
			lvGrade.getSelectionModel().selectedIndexProperty().addListener((d, e, f) -> {
				init();
			});
		});
	}
	
	private void init() {		
		
		tpBoxHolder.getChildren().clear();
		
		Integer year = lvYear.getSelectionModel().getSelectedItem();
		Grade grade = lvGrade.getSelectionModel().getSelectedItem();
		
		try {
			if(null == year)
				throw new StudentException("Please select academic year.");
			
			if(null == grade)
				throw new StudentException("Please select grade.");
			
			List<String> list = subService.getSubjectByGrade(year, grade);
			list.stream().map(s -> new SubjectBox(s)).forEach(box -> tpBoxHolder.getChildren().add(box));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void add() {
		SubjectEdit.show(list -> {
			list.stream().forEach(sub -> subService.save(sub));
		});
	}
	
	private class SubjectBox extends HBox {
		public SubjectBox(String text) {
			Label name = new Label(text);
			getChildren().add(name);
			getStyleClass().add("sub-box");
		}
	}

}
