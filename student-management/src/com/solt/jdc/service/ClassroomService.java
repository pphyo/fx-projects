package com.solt.jdc.service;

import static com.solt.jdc.util.DatabaseConnection.getSqlConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.solt.jdc.entity.Classroom;
import com.solt.jdc.entity.Classroom.Grade;

public class ClassroomService {

	private static final String SELECT = "select * from classroom where 1 = 1";
	private static ClassroomService INSTANCE;
	
	private ClassroomService() {}
	
	public static ClassroomService getInstance() {
		if(null == INSTANCE)
			INSTANCE = new ClassroomService();
		
		return INSTANCE;
	}
	
	public void save(Classroom room) {
		
		String sql = "insert into classroom (year, grade) values (?, ?)";
		
		try(Connection conn = getSqlConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			
			stmt.setInt(1, room.getYear());
			stmt.setString(2, room.getGrade().toString());
			
			stmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public List<Classroom> search(int year, Grade grade) {
		
		List<Classroom> result = new ArrayList<>();
		List<Object> params = new LinkedList<>();
		
		StringBuilder sb = new StringBuilder(SELECT);
		
		if(year > 999) {
			params.add(year);
			sb.append(" and year = ?");
		}
		
		if(null != grade) {
			params.add(grade);
			sb.append(" and grade = ?");
		}
		
		try(Connection conn = getSqlConnection();
				PreparedStatement stmt = conn.prepareStatement(sb.toString())) {
			
			for(int i = 0; i < params.size(); i++) {

				Object obj = params.get(i);
				
				if(obj instanceof Grade) {
					Grade g = (Grade) obj;
					
					obj = g.name();
				}
				
				stmt.setObject(i + 1, obj);
			}
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				result.add(getClassroomObject(rs));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
		
	}
	
	public List<Integer> getAllYears() {
		
		String sql = "select distinct(year) from classroom";
		
		List<Integer> result = new ArrayList<>();
		
		try(Connection conn = getSqlConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				result.add(rs.getInt(1));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
		
	}
	
	public List<Grade> getAllGradeByYear(int year) {
		
		String sql = "select grade from classroom where year = ?";
		
		List<Grade> result = new ArrayList<>();
		
		try(Connection conn = getSqlConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			
			stmt.setInt(1, year);
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next())
				result.add(Grade.valueOf(rs.getString(1)));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
		
	}
	
	public List<Classroom> getAll() {
		
		List<Classroom> result = new ArrayList<>();
		
		try(Connection conn = getSqlConnection();
				PreparedStatement stmt = conn.prepareStatement(SELECT)) {
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				result.add(getClassroomObject(rs));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	private Classroom getClassroomObject(ResultSet rs) throws SQLException {
		Classroom room = new Classroom();
		room.setGrade(Grade.valueOf(rs.getString("grade")));
		room.setYear(rs.getInt("year"));
		return room;
	}
	
}
