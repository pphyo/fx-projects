module com.jdc.unknown {
	exports com.jdc.app;

	requires javafx.controls;
	requires javafx.fxml;
	requires AnimateFX;
	requires transitive javafx.graphics;

	opens com.jdc.app.views to javafx.fxml;
}