module com.jdc.stu {
	
	exports com.solt.jdc;
	exports com.solt.jdc.entity;
	exports com.solt.jdc.views;
	
	requires lombok;
	requires javafx.controls;
	requires javafx.fxml;
	requires java.sql;
	requires transitive javafx.graphics;
	
	opens com.solt.jdc.views to javafx.fxml;
	
}