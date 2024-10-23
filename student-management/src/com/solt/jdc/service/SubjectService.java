package com.solt.jdc.service;

import static com.solt.jdc.util.DatabaseConnection.getSqlConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.solt.jdc.entity.Classroom.Grade;
import com.solt.jdc.entity.Subject;

public class SubjectService {
	
	private static SubjectService INSTANCE;
	
	private SubjectService() {}
	
	public static SubjectService getInstance() {
		return null == INSTANCE ? INSTANCE = new SubjectService() : INSTANCE;
	}
	
	public void save(Subject sub) {
		
		try(Connection conn = getSqlConnection();
				PreparedStatement stmt = conn.prepareStatement("insert into subject (name, classroom_year, classroom_grade) values (?, ?, ?)")) {
			
			stmt.setString(1, sub.getName());
			stmt.setInt(2, sub.getRoom().getYear());
			stmt.setString(3, sub.getRoom().getGrade().toString());
			stmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public List<String> getSubjectByGrade(int year, Grade grade) {
		
		List<String> result = new ArrayList<>();
		
		String sql = "select s.name from subject s join classroom c on"
				+ " s.classroom_year = c.year and s.classroom_grade = c.grade where"
				+ " c.year = ? and c.grade = ?";
		
		try(Connection conn = getSqlConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			
			stmt.setInt(1, year);
			stmt.setString(2, grade.toString());
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				String name = rs.getString(1);
				result.add(name);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

}
