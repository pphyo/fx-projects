module com.jdc.unknown {
	exports com.jdc.app;

	requires javafx.controls;
	requires javafx.fxml;
	requires AnimateFX;
	requires java.sql;
	requires transitive javafx.graphics;
	requires javafx.base;

	opens com.jdc.app.views to javafx.fxml;
	opens com.jdc.app.entity to javafx.base;
}