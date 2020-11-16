module com.jdc.unknown {
	exports com.jdc.app;

	requires javafx.controls;
	requires javafx.fxml;
	requires AnimateFX;
	requires java.sql;
	requires transitive javafx.graphics;
	requires lombok;

	opens com.jdc.app.views to javafx.fxml;
}