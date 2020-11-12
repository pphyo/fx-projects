package com.jdc.pos.view;

import java.io.FileInputStream;
import java.time.LocalDateTime;

import com.jdc.pos.entity.Employee;
import com.jdc.pos.util.StringUtil;
import com.jdc.pos.view.page.Page;
import com.jdc.pos.view.page.PageLoader;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class PosFrame implements PageLoader {

	@FXML
	private Label title;
	@FXML
	private VBox menuBar;
	@FXML
	private Label hour;
	@FXML
	private Label minute;
	@FXML
	private Label second;
	@FXML
	private Label userName;
	@FXML
	private StackPane viewHolder;
	@FXML
	private ImageView pic;

	private Employee emp;
	private static PageLoader loader;
	
	@FXML
	private void initialize() {
		try {
			pic.setImage(new Image(new FileInputStream("res/image/ecommerce-b.png")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		loadView(Page.Home);
		loader = this;
		playClock();
	}
	
	private void playClock() {
		Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, e -> {
			LocalDateTime dateTime = LocalDateTime.now();
			hour.setText(StringUtil.concatZero(String.valueOf(dateTime.getHour())));
			minute.setText(StringUtil.concatZero(String.valueOf(dateTime.getMinute())));
			second.setText(StringUtil.concatZero(String.valueOf(dateTime.getSecond())));
		}), new KeyFrame(Duration.seconds(1)));
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();
	}
	
	@Override
	public void loadView(Page page) {
		title.setText(page.getTitle());
		try {
			Parent root = FXMLLoader.load(getClass().getResource(page.getViewName()));
			loadView(root);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void loadView(Parent root) {
		viewHolder.getChildren().clear();
		viewHolder.getChildren().add(root);
	}

	@FXML
	private void loadView(MouseEvent event) {
		Node node = (Node)event.getSource();
		loadView(Page.valueOf(node.getId()));
		menuBar.getChildren().stream().filter(n -> n instanceof HBox)
							 .map(n -> (HBox) n)
							 .filter(n -> n.getStyleClass().contains("active"))
							 .findAny()
							 .ifPresent(a -> a.getStyleClass().remove("active"));
		node.getStyleClass().add("active");
	}
	
	public void showDetail() {
		EmployeeDetail.show(emp);
	}
	
	public void exit() {
		System.exit(0);
	}
	
	public static void show(Employee emp) {
		try {
			FXMLLoader loader = new FXMLLoader(PosFrame.class.getResource("PosFrame.fxml"));
			Parent root = loader.load();
			PosFrame controller = loader.getController();
			controller.emp = emp;
			controller.userName.setText(emp.getUserName());
			Stage stage = new Stage();
			stage.setTitle("Hello Pos");
			stage.getIcons().add(new Image(new FileInputStream("res/image/online-shopping.png")));
			stage.setScene(new Scene(root));
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static PageLoader getLoader() {
		return loader;
	}
	
}
