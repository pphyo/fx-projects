package com.solt.jdc.service;

import static com.solt.jdc.util.CommonUtil.isEmpty;
import static com.solt.jdc.util.DatabaseConnection.getSqlConnection;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.solt.jdc.entity.Classroom;
import com.solt.jdc.entity.Classroom.Grade;
import com.solt.jdc.entity.Student;
import com.solt.jdc.entity.Student.Gender;

public class StudentService {
	
	private static final String SELECT = "select c.year, c.grade, s.id, s.name, s.gender, s.dob, s.phone from classroom c join student s on c.year = s.classroom_year and c.grade = s.classroom_grade where 1 = 1";
	private static StudentService INSTANCE;
	
	private StudentService() {}
	
	public static StudentService getInstance() {
		return null == INSTANCE ? INSTANCE = new StudentService() : INSTANCE;
	}
	
	public void save(Student stu) {
		
		String insert = "insert into student (name, gender, dob, phone, classroom_year, classroom_grade) values (?, ?, ?, ?, ?, ?)";
		String update = "update student set name = ?, gender = ?, dob = ?, phone = ?, classroom_year = ?, classroom_grade = ? where id = ?";
		
		boolean isNew = stu.getId() == 0;
		
		try(Connection conn = getSqlConnection();
				PreparedStatement stmt = conn.prepareStatement(isNew ? insert : update)) {
			
			stmt.setString(1, stu.getName());
			stmt.setString(2, stu.getGender().toString());
			stmt.setDate(3, Date.valueOf(stu.getDob()));
			stmt.setString(4, stu.getPhone());
			stmt.setInt(5, stu.getClassroom().getYear());
			stmt.setString(6, stu.getClassroom().getGrade().toString());
			
			if(!isNew)
				stmt.setInt(7, stu.getId());
			
			stmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void delete(int id) {
		String sql = "delete from student where id = ?";
		
		try(Connection conn = getSqlConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			
			stmt.setInt(1, id);
			stmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<Student> getAll() {
		
		List<Student> result = new ArrayList<>();
		
		try(Connection conn = getSqlConnection();
				PreparedStatement stmt = conn.prepareStatement(SELECT)) {
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next())
				result.add(getStudentObject(rs));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
		
	}
	
	public List<Student> search(int year, Grade grade, int id, String name, Gender gender) {
		
		List<Student> result = new ArrayList<>();
		List<Object> params = new LinkedList<>();
		
		StringBuilder sb = new StringBuilder(SELECT);
		
		if(year > 999) {
			params.add(year);
			sb.append(" and c.year = ?");
		}
		
		if(null != grade) {
			params.add(grade);
			sb.append(" and c.grade = ?");
		}
		
		if(id > 0) {
			params.add(id);
			sb.append(" and s.id = ?");
		}
		
		if(!isEmpty(name)) {
			sb.append(" and s.name like ?");
			params.add("%".concat(name).concat("%"));
		}
		
		if(null != gender) {
			params.add(gender);
			sb.append(" and s.gender = ?");
		}
		
		try(Connection conn = getSqlConnection();
				PreparedStatement stmt = conn.prepareStatement(sb.toString())) {
			
			for(int i = 0; i < params.size(); i++) {
				
				Object obj = params.get(i);
				
				if(obj instanceof Grade) {
					Grade g = (Grade) obj;
					
					obj = g.name();
				}
				
				if(obj instanceof Gender) {
					Gender g = (Gender) obj;
					
					obj = g.name();
				}
				
				stmt.setObject(i + 1, obj);
				
			}
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next())
				result.add(getStudentObject(rs));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
		
	}

	private Student getStudentObject(ResultSet rs) throws SQLException {
		Student stu = new Student();
		
		stu.setId(rs.getInt("id"));
		stu.setName(rs.getString("name"));
		stu.setGender(Gender.valueOf(rs.getString("gender")));
		stu.setDob(rs.getDate("dob").toLocalDate());
		stu.setPhone(rs.getString("phone"));
		
		Classroom room = new Classroom();
		room.setYear(rs.getInt("year"));
		room.setGrade(Grade.valueOf(rs.getString("grade")));
		
		stu.setClassroom(room);
		
		return stu;
	}
	
	public void upload(File file) throws IOException {
		Files.readAllLines(file.toPath()).stream().skip(1)
			.map(s -> {
				String[] arr = s.split("\t");
				
				Student stu = new Student();
				
				Classroom room = new Classroom();
				room.setYear(Integer.parseInt(arr[0]));
				room.setGrade(Grade.valueOf(arr[1]));
				
				stu.setClassroom(room);
				stu.setName(arr[2]);
				stu.setGender(Gender.valueOf(arr[3]));
				stu.setDob(LocalDate.parse(arr[4]));
				stu.setPhone(arr[5]);
				
				return stu;
				
			}).forEach(this::save);
	}

}
