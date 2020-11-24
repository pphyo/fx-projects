package com.jdc.app.views;

import java.time.LocalTime;

import com.jdc.app.util.CommonUtil;
import com.jdc.app.util.Security;
import com.jdc.app.views.page.Page;
import com.jdc.app.views.page.PageLoader;

import animatefx.animation.FadeIn;
import animatefx.animation.FadeInRight;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class RootFrame implements PageLoader {
	
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
		loadView(Page.Dashboard);
		user.setText(Security.getEmployee().getUsername());
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
	

	public void loadLogOut() {
		System.exit(0);
	}
	
	@Override
	public void loadView(Page page) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource(page.getViewName()));
			loadView(root);
			new FadeInRight(root).play();
		} catch (Exception e) {
			
		}
	}
	
	private void loadView(Parent root) {
		viewHolder.getChildren().clear();
		viewHolder.getChildren().add(root);
	}
	
	@FXML
	void loadView(MouseEvent event) {
		Node node = (Node)event.getSource();
		
		title.setText(Page.valueOf(node.getId()).getTitle());
		
		loadView(Page.valueOf(node.getId()));
		btnHolder.getChildren().stream().filter(n -> n instanceof HBox)
							   .map(n -> (HBox)n)
							   .filter(box -> box.getStyleClass().contains("active"))
							   .findAny()
							   .ifPresent(box -> box.getStyleClass().remove("active"));
		node.getStyleClass().add("active");
		new FadeIn(node).play();
	}
	
	private void playClock() {
		Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, e -> {
			LocalTime now = LocalTime.now();
			
			int h = now.getHour();
			int twelveHour = CommonUtil.getTwelveHour(h);
			
			hour.setText(CommonUtil.concatZero(String.valueOf(twelveHour)));
			minute.setText(CommonUtil.concatZero(String.valueOf(now.getMinute())));
			second.setText(CommonUtil.concatZero(String.valueOf(now.getSecond())));
			amPM.setText(CommonUtil.getAmPm(h));
			
		}), new KeyFrame(Duration.seconds(1)));
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();
	}
	
}