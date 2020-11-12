package com.jdc.pos.view;

import java.time.LocalDate;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.jdc.pos.service.SaleService;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.DatePicker;

public class PosHome {
	
	@FXML
	private BarChart<String, Integer> chart;
	@FXML
	private DatePicker dateFrom;
	@FXML
	private DatePicker dateTo;
	
	private static LocalDate from = LocalDate.now();
	private static LocalDate to = LocalDate.now();
	
	@FXML
	private void initialize() {
		if(LocalDate.now().compareTo(from) > 0) {
			dateFrom.setValue(from);
		} else {
			dateFrom.setValue(from.minusDays(5));
		}
		dateTo.setValue(to);
		
		load();
		
		dateFrom.valueProperty().addListener(a -> load());
		dateTo.valueProperty().addListener(a -> load());
	}

	public void load() {
		from = dateFrom.getValue();
		to = dateTo.getValue();
		
		chart.getData().clear();
		
		Map<String, Map<String, Integer>> orders = SaleService.getInstance().find(dateFrom.getValue(), dateTo.getValue());
		
		Set<Entry<String, Map<String, Integer>>> dataSet = orders.entrySet();
		
		for(Entry<String, Map<String, Integer>> entry : dataSet) {
			Series<String, Integer> series = new Series<>();
			series.setName(entry.getKey());
			
			for(Entry<String, Integer> data : entry.getValue().entrySet()) {
				series.getData().add(new Data<>(data.getKey(), data.getValue()));
			}
			
			chart.getData().add(series);
			
		}
		
	}

}
