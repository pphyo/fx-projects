package com.jdc.pos.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jdc.pos.entity.SaleOrder;
import com.jdc.pos.service.SaleService;
import com.jdc.pos.util.PromptBox;
import com.jdc.pos.util.Security;

import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.DirectoryChooser;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class SaleHistory {

	@FXML
	private DatePicker from;
	@FXML
	private DatePicker to;
	@FXML
	private TextField total;
	@FXML
	private TableView<SaleOrder> soList;
	
	private SaleService saleService;
	
	@FXML
	private void initialize() {
		saleService = SaleService.getInstance();
		clear();
		search();
		
		from.valueProperty().addListener(a -> search());
		to.valueProperty().addListener(a -> search());
		
		soList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		createMenu();
	}
	
	private void createMenu() {
		MenuItem export = new MenuItem("Export As Pdf");
		export.setOnAction(e -> export());
		
		ContextMenu menu = new ContextMenu(export);
		soList.setContextMenu(menu);
	}
	
	private void export() {
		try {
			List<SaleOrder> list = soList.getSelectionModel().getSelectedItems();
			
			Map<String, Object> params = new HashMap<>();
			
			params.put("reporter", Security.getMember().getUserName());
			params.put("orders", new JRBeanCollectionDataSource(list));
			params.put("totalItems", list.size());
			params.put("totalAmount", list.stream().mapToInt(so -> so.getTotal()).sum() + " MMK");
			
			exportAsPdf("report", params);
			PromptBox.showPrompt("Export Successfully", "Pdf Export", new Image(new FileInputStream("res/image/export.png")));
			
		} catch (Exception e) {
			try {
				PromptBox.showPrompt("Something Wrong when exporting pdf file!", "Export Error", new Image(new FileInputStream("res/image/cc.png")));
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
		}
		
	}
	
	private void exportAsPdf(String reportName, Map<String, Object> params) throws FileNotFoundException, JRException {
		
		DirectoryChooser dc = new DirectoryChooser();
		dc.setTitle("Choose Location");
		dc.setInitialDirectory(new File(System.getProperty("user.home"), "Desktop"));
		File file = dc.showDialog(from.getScene().getWindow());
		
		String exportFileName = LocalDateTime.now().format(DateTimeFormatter.ofPattern("ddMMyyhmmssa"));
		
		InputStream stream = new FileInputStream(new File("report", reportName.concat(".jrxml")));
		JasperReport report = JasperCompileManager.compileReport(stream);
		JasperPrint print = JasperFillManager.fillReport(report, params, new JREmptyDataSource());
		JasperExportManager.exportReportToPdfFile(print, file.getAbsolutePath().concat("\\") + exportFileName.concat(".pdf"));
			
	}
	
	public void search() {
		soList.getItems().clear();
		int price = null == total.getText() || total.getText().isEmpty() ? 0 : Integer.parseInt(total.getText());
		List<SaleOrder> list = saleService.findSaleOrder(from.getValue(), to.getValue(), price);
		soList.getItems().addAll(list);
	}
	
	public void clear() {
		from.setValue(LocalDate.now().minusMonths(1));
		to.setValue(LocalDate.now());
		total.clear();
	}
	
}
