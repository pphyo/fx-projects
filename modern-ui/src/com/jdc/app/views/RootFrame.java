package com.jdc.app.views;

import java.time.LocalTime;

import com.jdc.app.util.ClockUtil;

import animatefx.animation.BounceIn;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class RootFrame {
	
	@FXML
	private VBox btnHolder;
	@FXML
	private Label title;
	@FXML
	private Label hour;
	@FXML
	private Label minute;
	@FXML
	private Label second;
	@FXML
	private Label amPM;
	@FXML
	private Label user;
	@FXML
	private StackPane viewHolder;
	
	@FXML
	private void initialize() {
		playClock();
	}
	
	public static void show() {
		try {
			Parent root = FXMLLoader.load(RootFrame.class.getResource("RootFrame.fxml"));
			Scene scene = new Scene(root);
			scene.setFill(Color.TRANSPARENT);
			Stage stage = new Stage(StageStyle.TRANSPARENT);
			stage.setScene(scene);
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void loadDashboardView(MouseEvent event) {
		changeActive(event);
		loadView("Dashboard", "POS Dashboard");
		new BounceIn((Node)event.getSource()).play();
	}
	
	public void loadSaleView(MouseEvent event) {
		changeActive(event);
		new BounceIn((Node)event.getSource()).play();
	}
	
	public void loadCategoryView(MouseEvent event) {
		changeActive(event);
		new BounceIn((Node)event.getSource()).play();
	}
	
	public void loadProductView(MouseEvent event) {
		changeActive(event);
		new BounceIn((Node)event.getSource()).play();
	}
	
	public void loadSaleHistoryView(MouseEvent event) {
		changeActive(event);
		new BounceIn((Node)event.getSource()).play();
	}
	
	public void loadMemberView(MouseEvent event) {
		changeActive(event);
		new BounceIn((Node)event.getSource()).play();
	}
	
	public void loadLogOut() {
		System.exit(0);
	}
	
	public void loadView(String viewFile, String viewName) {
		title.setText(viewName);
		try {
			Parent root = FXMLLoader.load(getClass().getResource(viewFile.concat(".fxml")));
			viewHolder.getChildren().clear();
			viewHolder.getChildren().add(root);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void changeActive(MouseEvent event) {
		Node n = (Node) event.getSource();
		btnHolder.getChildren().stream().filter(a -> a.getStyleClass().contains("active")).findAny().ifPresent(a -> a.getStyleClass().remove("active"));
		n.getStyleClass().add("active");
	}
	
	private void playClock() {
		Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, e -> {
			LocalTime now = LocalTime.now();
			
			int h = now.getHour();
			int twelveHour = ClockUtil.getTwelveHour(h);
			
			hour.setText(ClockUtil.concatZero(String.valueOf(twelveHour)));
			minute.setText(ClockUtil.concatZero(String.valueOf(now.getMinute())));
			second.setText(ClockUtil.concatZero(String.valueOf(now.getSecond())));
			amPM.setText(ClockUtil.getAmPm(h));
			
		}), new KeyFrame(Duration.seconds(1)));
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();
	}
	
}