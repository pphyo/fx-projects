package com.solt.jdc.entity;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Classroom {

	private int year;
	private Grade grade;
	
	public static List<Grade> getGradeList() {
		
		List<Grade> list = new ArrayList<>();
		
		Grade[] grades = Grade.values();
		
		for(Grade g : grades) {
			if(g == Grade.KG) {
				list.add(Grade.KG);
			} else {
				char[] arr = g.toString().toCharArray();
				
				String result = "";
				
				for(int i = 0; i < arr.length; i++) {
					if(i == 1) {
						result += "-";
					}
					result += arr[i];
				}
				
				list.add(Grade.valueOf(result));
				
			}
		}
		
		return list;
	}
	
	public enum Grade {
		KG, G1, G2, G3, G4, G5, G6, G7, G8, G9, G10
	}
	
}
