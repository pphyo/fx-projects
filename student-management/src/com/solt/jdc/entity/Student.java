package com.solt.jdc.entity;

import java.time.LocalDate;

import com.solt.jdc.util.StudentUtil;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Student {
	
	private int id;
	private String name;
	private Gender gender;
	private LocalDate dob;
	private String phone;
	
	private Classroom classroom;
	
	public String getStuId() {
		return StudentUtil.convertStuId(getId());
	}
	
	public int getClassroomYear() {
		return classroom.getYear();
	}
	
	public String getClassroomGrade() {
		return classroom.getGrade().toString();
	}

	public enum Gender { Male, Female }
}
