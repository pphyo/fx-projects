package com.solt.jdc.entity;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Result {

	private boolean result;
	private int marks;
	private int totalMarks;
	private double avg;
	
	private Student student;
}