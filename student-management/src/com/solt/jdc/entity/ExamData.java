package com.solt.jdc.entity;

import java.time.LocalDate;

import lombok.Setter;
import lombok.Getter;

@Getter @Setter
public class ExamData {

	private int id;
	private LocalDate examDate;
	
	private Student student;
	private Exam exam;
	
}
