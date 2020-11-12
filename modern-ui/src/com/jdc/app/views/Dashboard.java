package com.jdc.app.views;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.DatePicker;

public class Dashboard {
	
	@FXML
	private DatePicker from;
	@FXML
	private DatePicker to;
	@FXML
	private LineChart<String, Integer> lineChart;
	@FXML
	private PieChart pieChartOne;
	@FXML
	private PieChart pieChartTwo;
	
	public void initialize() {
		loadPieChartData();
	}
	
	public void load() {
		lineChart.getData().clear();
		
		Series<String, Integer> series1950 = new Series<>();
		series1950.setName("1950");
        series1950.getData().add(new Data<String, Integer>("China", 555));
        series1950.getData().add(new Data<String, Integer>("India", 358));
        series1950.getData().add(new Data<String, Integer>("Brazil", 54));
        series1950.getData().add(new Data<String, Integer>("UK", 50));
        series1950.getData().add(new Data<String, Integer>("USA", 158));
        
        Series<String, Integer> series2000 = new Series<>();
		series2000.setName("2000");
		series2000.getData().add(new Data<String, Integer>("China", 1275));
        series2000.getData().add(new Data<String, Integer>("India",1017));
        series2000.getData().add(new Data<String, Integer>("Brazil", 172));
        series2000.getData().add(new Data<String, Integer>("UK", 59));
        series2000.getData().add(new Data<String, Integer>("USA", 285));
        
        Series<String, Integer> series2050 = new Series<>();
		series2050.setName("2050");
        series2050.getData().add(new Data<String, Integer>("China", 1395));
        series2050.getData().add(new Data<String, Integer>("India",1531));
        series2050.getData().add(new Data<String, Integer>("Brazil", 233));
        series2050.getData().add(new Data<String, Integer>("UK", 66));
        series2050.getData().add(new Data<String, Integer>("USA", 409));
        
        lineChart.getData().add(series1950);
        lineChart.getData().add(series2000);
        lineChart.getData().add(series2050);
        
	}
	
	public void loadPieChartData() {
		javafx.scene.chart.PieChart.Data c1 = new javafx.scene.chart.PieChart.Data("China", 555);
		javafx.scene.chart.PieChart.Data i1 = new javafx.scene.chart.PieChart.Data("India", 358);
		javafx.scene.chart.PieChart.Data b1 = new javafx.scene.chart.PieChart.Data("Brazil", 54);
		javafx.scene.chart.PieChart.Data uk1 = new javafx.scene.chart.PieChart.Data("UK", 50);
		javafx.scene.chart.PieChart.Data us1 = new javafx.scene.chart.PieChart.Data("USA", 158);
		javafx.scene.chart.PieChart.Data c2 = new javafx.scene.chart.PieChart.Data("China", 1275);
		javafx.scene.chart.PieChart.Data i2 = new javafx.scene.chart.PieChart.Data("India", 358);		
		javafx.scene.chart.PieChart.Data b2 = new javafx.scene.chart.PieChart.Data("Brazil", 172);
		javafx.scene.chart.PieChart.Data uk2 = new javafx.scene.chart.PieChart.Data("UK", 59);
		javafx.scene.chart.PieChart.Data us2 = new javafx.scene.chart.PieChart.Data("USA", 285);
		
		pieChartOne.getData().addAll(c1, i1, b1, uk1, us1);
		pieChartTwo.getData().addAll(c2, i2, b2, uk2, us2);
	}

}
